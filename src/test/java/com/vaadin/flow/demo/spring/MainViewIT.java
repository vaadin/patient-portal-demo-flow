package com.vaadin.flow.demo.spring;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.theme.lumo.Lumo;

public class MainViewIT extends AbstractViewTest {

    @Test
    public void buttonIsUsingLumoTheme() {
        WebElement button = $("login-view").first().$("*").id("login-button");
        assertThemePresentOnElement(button, Lumo.class);
    }
}
