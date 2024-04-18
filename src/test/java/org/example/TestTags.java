package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TestTags {

    @Test
    @Tag("smoke")
    public void smokeTest() {
        System.out.println("Smoke");
    }

    @Test
    @Tag("regress")
    public void regresTest() {
        System.out.println("Regress");
    }
}
