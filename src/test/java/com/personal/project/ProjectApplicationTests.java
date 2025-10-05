package com.personal.project;

import com.personal.project.util.BusinessErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getCode(){
        //System.out.println(BusinessErrorCode.getByCode("SYS_001"));
        System.out.println("SYS_001");
    }

}
