package com.orange.article;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TimeTest {

    @Test
    public void timeTest() {
        System.out.println("date = " + new Date());
        System.out.println("localDate.now = " + LocalDate.now());
    }

}
