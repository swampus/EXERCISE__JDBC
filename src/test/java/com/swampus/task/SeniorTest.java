package com.swampus.task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeniorTest {

    private Senior senior = new Senior();

    @Test
    public void getPassword() {
        assertEquals("122323", senior.getPassword());
    }
}