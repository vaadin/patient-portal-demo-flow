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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.testbench.TestBenchElement;

public class AnalyticsMemoryIT extends AbstractMemoryMeasurementIT {

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
        TestBenchElement main = $("main-view").first();
        waitUntil(driver -> main.$("*").attribute("id", "analytics").exists());
        WebElement analytics = main.$("*").id("analytics");
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
