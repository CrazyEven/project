package com.personal.project.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RedisDistributedLock {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ThreadLocal<String> lockValueThreadLocal = new ThreadLocal<>();

    private static final String LOCK_PREFIX = "lock:";
    private static final long DEFAULT_EXPIRE_TIME = 30000; // 30秒
    private static final long DEFAULT_WAIT_TIME = 5000; // 5秒
    private static final long DEFAULT_RETRY_INTERVAL = 100; // 100毫秒

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey 锁的key
     * @param expireTime 锁的过期时间(毫秒)
     * @param waitTime 获取锁的最大等待时间(毫秒)
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, long expireTime, long waitTime) {
        String fullLockKey = LOCK_PREFIX + lockKey;
        String lockValue = UUID.randomUUID().toString();
        long endTime = System.currentTimeMillis() + waitTime;

        try {
            while (System.currentTimeMillis() < endTime) {
                Boolean result = redisTemplate.opsForValue().setIfAbsent(
                        fullLockKey,
                        lockValue,
                        Duration.ofMillis(expireTime)
                );

                if (Boolean.TRUE.equals(result)) {
                    lockValueThreadLocal.set(lockValue);
                    return true;
                }

                // 短暂休眠后重试
                try {
                    Thread.sleep(DEFAULT_RETRY_INTERVAL);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        } catch (Exception e) {
            // 记录日志
            System.err.println("获取分布式锁异常: " + e.getMessage());
        }

        return false;
    }

    /**
     * 尝试获取分布式锁（使用默认等待时间）
     */
    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, DEFAULT_EXPIRE_TIME, DEFAULT_WAIT_TIME);
    }

    /**
     * 释放分布式锁
     */
    public void unlock(String lockKey) {
        String fullLockKey = LOCK_PREFIX + lockKey;
        String lockValue = lockValueThreadLocal.get();

        if (lockValue != null) {
            try {
                // 使用 Lua 脚本保证原子性操作
                String luaScript =
                        "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                                "   return redis.call('del', KEYS[1]) " +
                                "else " +
                                "   return 0 " +
                                "end";

                RedisScript<Long> script = RedisScript.of(luaScript, Long.class);
                Long result = redisTemplate.execute(script, Collections.singletonList(fullLockKey), lockValue);

                if (result != null && result == 1) {
                    lockValueThreadLocal.remove();
                }
            } catch (Exception e) {
                // 记录日志
                System.err.println("释放分布式锁异常: " + e.getMessage());
            }
        }
    }

    /**
     * 安全执行带有分布式锁的业务逻辑
     */
    public <T> T executeWithLock(String lockKey, long expireTime, long waitTime, Supplier<T> supplier) {
        if (tryLock(lockKey, expireTime, waitTime)) {
            try {
                return supplier.get();
            } finally {
                unlock(lockKey);
            }
        } else {
            throw new RuntimeException("获取分布式锁失败，请稍后重试");
        }
    }

    /**
     * 安全执行带有分布式锁的业务逻辑（使用默认参数）
     */
    public <T> T executeWithLock(String lockKey, Supplier<T> supplier) {
        return executeWithLock(lockKey, DEFAULT_EXPIRE_TIME, DEFAULT_WAIT_TIME, supplier);
    }
}
