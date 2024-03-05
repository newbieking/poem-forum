package com.example;


import org.junit.jupiter.api.Test;

import java.util.UUID;

public class NormalTests {

    @Test
    void test() {
        UUID uuid = UUID.randomUUID();
        String string = uuid.toString();
        System.out.println(string);
    }

    @Test
    void testUTF8() {
        System.out.println("你好");
    }
}
