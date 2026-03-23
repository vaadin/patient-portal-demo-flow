/*
 * Copyright 2000-2023 Vaadin Ltd.
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

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Proxy;

import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.testutil.ChromeBrowserTest;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchElement;

/**
 * @author Vaadin Ltd
 *
 */
public abstract class AbstractChromeTest extends ChromeBrowserTest {

    private TestBenchElement layout;

    @Before
    @Override
    public void setup() throws Exception {
        super.setup();
        testBench().resizeViewPortTo(1000, 1000);
        
        // Check if proxy recording is enabled
        String proxyHost = System.getProperty(PROXY_HOST_PROPERTY);
        if (proxyHost != null && !proxyHost.isEmpty()) {
            // Replace the default driver with a proxy-configured one
            WebDriver defaultDriver = getDriver();
            if (defaultDriver != null) {
                defaultDriver.quit();
            }
            setDriver(createProxyDriver(proxyHost));
        }

        getDriver().get("http://" + getDeploymentHostname() + ":" + getDeploymentPort() + "/" + getTestPath());
    
    }

    protected void setLayout(TestBenchElement layout) {
        this.layout = layout;
    }

    protected TestBenchElement getLayout() {
        return layout;
    }

    @Override
    protected String getTestPath() {
        return "/";
    }

    @Override
    protected int getDeploymentPort() {
        return Integer.parseInt(System.getProperty("app.port", "8081"));
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
        DatePickerElement datePicker = layout.$(DatePickerElement.class).id(datePickerId);
        datePicker.clear();
        datePicker.setInputValue(date);
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
        ComboBoxElement comboBox = layout.$(ComboBoxElement.class).id(comboBoxId);
        comboBox.clear();
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
        TestBenchElement field = layout.$(TestBenchElement.class).id(fieldId);
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
        setLayout($("login-view").first());
        setTextFieldValue("username", "user");
        setTextFieldValue("password", "password");
        layout.$("*").id("login-button").click();
        waitUntil(d -> $("main-view").first().$("*").id("logout"));
    }

       private static final String PROXY_HOST_PROPERTY = "k6.proxy.host";

    /**
     * Creates a ChromeDriver configured with proxy settings for k6 recording.
     */
    private WebDriver createProxyDriver(String proxyHost) {
        ChromeOptions options = new ChromeOptions();

        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyHost);
        proxy.setSslProxy(proxyHost);
        options.setProxy(proxy);

        // Required for MITM proxy to work with HTTPS
        options.addArguments("--ignore-certificate-errors");
        // Force localhost traffic through proxy (don't bypass loopback)
        options.addArguments("--proxy-bypass-list=<-loopback>");
        options.setAcceptInsecureCerts(true);

        return TestBench.createDriver(new ChromeDriver(options));
    }

}
