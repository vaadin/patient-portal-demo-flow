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

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class NavigationIT extends AbstractChromeTest {

    private class LocationCondition implements ExpectedCondition<Boolean> {

        private final String location;

        LocationCondition(String location) {
            this.location = location;
        }

        @Override
        public Boolean apply(WebDriver driver) {
            return driver.getCurrentUrl()
                    .contains(getRootURL() + '/' + location);
        }

        @Override
        public String toString() {
            return "Page location '" + getRootURL() + '/' + location + "'";
        }
    }

    @Test
    public void testNavigation() {
        open();

        getInShadowRoot(By.tagName("login-view"), By.id("login-button"))
        .click();
        waitLocation("patients");

        // Click on the first cell that actually contains patient-data:
        getInShadowRoot(By.tagName("patients-view"),
                By.id("vaadin-grid-cell-content-13")).click();
        waitLocation("patients/1");

        getInShadowRoot(By.tagName("patient-details"), By.linkText("JOURNAL"))
        .click();
        waitLocation("patients/1/journal");

        getInShadowRoot(By.tagName("patient-journal"),
                By.partialLinkText("NEW ENTRY")).click();
        waitLocation("patients/1/new-entry");

        getInShadowRoot(By.tagName("patient-details"),
                By.linkText("EDIT PATIENT")).click();
        waitLocation("patients/1/edit");

        getInShadowRoot(By.tagName("patient-details"), By.linkText("PROFILE"))
        .click();
        waitLocation("patients/1");

        getInShadowRoot(By.tagName("patient-details"),
                By.linkText("ALL PATIENTS")).click();
        waitLocation("patients");

        getInShadowRoot(By.tagName("main-view"), By.linkText("ANALYTICS"))
        .click();
        waitLocation("analytics");

        getInShadowRoot(By.tagName("vaadin-license-dialog"),
                By.id("licenseDialogClose")).click();

        getInShadowRoot(By.tagName("analytics-view"), By.linkText("DOCTOR"))
        .click();
        waitLocation("analytics/doctor");

        getInShadowRoot(By.tagName("analytics-view"), By.linkText("GENDER"))
        .click();
        waitLocation("analytics/gender");

        getInShadowRoot(By.tagName("analytics-view"), By.linkText("AGE"))
        .click();
        waitLocation("analytics/age");

        getInShadowRoot(By.tagName("main-view"), By.linkText("PATIENTS"))
        .click();
        waitLocation("patients");

        getInShadowRoot(By.tagName("patients-view"), By.id("patientsGrid"));
    }

    private void waitLocation(String expectedLocation) {
        waitUntil(new LocationCondition(expectedLocation));
    }
}
