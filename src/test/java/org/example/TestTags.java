package org.example;

import io.qameta.allure.junit4.Tag;
import org.junit.Test;

public class TestTags {

    @Test
    @Tag("smoke")
    public void smokeTest() {
        System.out.println("Smoke");
    }

    @Test
    @Tag("regres")
    public void regresTest() {
        System.out.println("Regres");
    }
}
