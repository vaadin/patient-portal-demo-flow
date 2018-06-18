/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.patientportal;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.testutil.ChromeBrowserTest;

/**
 * @author Vaadin Ltd
 *
 */
public abstract class AbstractChromeTest extends ChromeBrowserTest {

    private WebElement layout;

    protected void setLayout(String tagName) {
        layout = findElement(By.tagName(tagName));
    }

    protected WebElement getLayout() {
        return layout;
    }

    protected WebElement getInShadowRoot(By shadowHost, By by) {
        return getInShadowRoot(findElement(shadowHost), by);
    }

    @Override
    protected String getTestPath() {
        return "/";
    }

    @Override
    protected int getDeploymentPort() {
        return 8080;
    }

    protected List<WebElement> getChildren(WebElement parent) {
        return (List<WebElement>) getCommandExecutor()
                .executeScript("return arguments[0].children", parent);
    }

    /**
     * Inputs a given date-string to a vaadin-date-picker that is in the
     * shadow-dom of the {@link #layout}.
     *
     * @param datePickerId
     *            id-property of the vaadin-date-picker
     * @param date
     *            date that will be picked, in format MM/dd/yyyy
     */
    protected void setDate(String datePickerId, String date) {
        WebElement datePicker = getInShadowRoot(layout, By.id(datePickerId));
        WebElement dateField = getInShadowRoot(datePicker, By.id("input"));
        dateField = getInShadowRoot(dateField, By.cssSelector("input"));
        dateField.clear();
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ENTER);
    }

    /**
     * Selects a given value from a vaadin-combo-box that is in the shadow-dom
     * of the {@link #layout}.
     *
     * @param comboBoxId
     *            id-property of the vaadin-combo-box
     * @param value
     *            item to be selected
     */
    protected void selectFromComboBox(String comboBoxId, String value) {
        WebElement comboBox = getInShadowRoot(layout, By.id(comboBoxId));
        WebElement textField = getInShadowRoot(comboBox, By.id("input"));
        getInShadowRoot(textField, By.cssSelector("input")).clear();
        comboBox.sendKeys(value);
        comboBox.sendKeys(Keys.ENTER);
    }

    /**
     * Inputs a given value to a text-field that is in the shadow-dom of the
     * {@link #layout}.
     *
     * @param fieldId
     *            id-property of the text-field
     * @param value
     *            text to be written into the field
     */
    protected void setTextFieldValue(String fieldId, String value) {
        WebElement field = getInShadowRoot(layout, By.id(fieldId));
        field.clear();
        field.sendKeys(value);
    }

    @Override
    protected void open() {
        doOpen();
        login();
        doOpen();
    }

    protected void doOpen() {
        super.open();
    }

    protected void login() {
        waitForElementPresent(By.xpath("//login-view"));
        setLayout("login-view");
        setTextFieldValue("username", "user");
        setTextFieldValue("password", "password");
        getInShadowRoot(getLayout(), By.id("login-button")).click();
    }
}
