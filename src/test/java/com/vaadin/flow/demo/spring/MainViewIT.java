package com.vaadin.flow.demo.spring;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.theme.lumo.Lumo;

public class MainViewIT extends AbstractViewTest {

    @Test
    public void buttonIsUsingLumoTheme() {
        WebElement element = findElement(By.tagName("login-view"));
        WebElement button = findInShadowRoot(element,
                By.id("login-button")).get(0);
        assertThemePresentOnElement(button, Lumo.class);
    }
}
