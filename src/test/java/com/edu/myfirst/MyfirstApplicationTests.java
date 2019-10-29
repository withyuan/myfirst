package com.edu.myfirst;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyfirstApplicationTests {
    @Test
    void contextLoads() {
    }
    @Test
    void a(){
        Integer a=new Integer(0);
        int v = a.intValue();
        System.out.println(v);

    }

}
