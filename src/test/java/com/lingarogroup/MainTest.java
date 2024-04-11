package com.lingarogroup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    public void testWilmaFirstNameToSalary() {
        Main main = new Main();
        main.main(new String[0]);
        int salary = main.getSalary("Wilma");
        assertEquals(2506, salary, "salary should be 2506");
    }

    @Test
    public void testWilma2FirstNameToSalary() {
        Main main = new Main();
        main.main(new String[0]);
        int salary = main.getSalary("Wilma2");
        assertEquals(2508, salary,"salary should be 2508");
    }
}