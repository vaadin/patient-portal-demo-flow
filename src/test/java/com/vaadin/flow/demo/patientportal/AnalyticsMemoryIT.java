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

import java.net.URL;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.TestBench;

@Ignore
public class AnalyticsMemoryIT extends AbstractMemoryMeasurementIT {

    @Override
    public void setup() throws Exception {
        if (getRunOnHub(getClass()) != null
                || Parameters.getHubHostname() != null) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                    new String[] { "--headless", "--disable-gpu" });
            options.setExperimentalOption("w3c", false);

            options.merge(getDesiredCapabilities());
            setDesiredCapabilities(getDesiredCapabilities());

            WebDriver driver = TestBench.createDriver(
                    new RemoteWebDriver(new URL(getHubURL()), options));
            setDriver(driver);
        } else {
            super.setup();
        }
    }

    @Override
    protected String getTestPath() {
        // Use patients view as a starting point, navigate on open to analytics
        return "/patients/test";
    }

    @Override
    protected void doOpen() {
        super.doOpen();

        // navigate to analytics view from patients view instead of open
        // analytics view directly
        WebElement main = findElement(By.xpath("//main-view"));
        waitUntil(driver -> isPresentInShadowRoot(main, By.id("analytics")));
        WebElement analytics = getInShadowRoot(main, By.id("analytics"));
        new Actions(getDriver()).moveToElement(analytics).click().build()
                .perform();
    }

    @Override
    protected long getGoldenAmount() {
        return 35000;
    }

    @Override
    protected String getStatKey() {
        return "analytics";
    }

}
