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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author Vaadin Ltd
 *
 */
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
        WebElement main = findElement(By.tagName("main-view"));
        waitUntil(driver -> isPresentInShadowRoot(main, By.id("analytics")));
        WebElement analytics = getInShadowRoot(main, By.id("analytics"));
        new Actions(getDriver()).moveToElement(analytics).click().build()
                .perform();
    }

    @Override
    protected long getGoldenAmount() {
        return 35000;
    }

}
