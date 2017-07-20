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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class NavigationIT extends AbstractChromeTest {

    @Test
    public void testNavigation() {
        open();

        getInShadowRoot(By.tagName("login-view"), By.id("login-button"))
                .click();
        assertLocation("patients");

        // Click on the first cell that actually contains patient-data:
        getInShadowRoot(By.tagName("patients-view"),
                By.id("vaadin-grid-cell-content-13")).click();
        assertLocation("patients/1");

        getInShadowRoot(By.tagName("patient-details"), By.linkText("JOURNAL"))
                .click();
        assertLocation("patients/1/journal");

        getInShadowRoot(By.tagName("patient-journal"),
                By.partialLinkText("NEW ENTRY")).click();
        assertLocation("patients/1/new-entry");

        getInShadowRoot(By.tagName("patient-details"),
                By.linkText("EDIT PATIENT")).click();
        assertLocation("patients/1/edit");

        getInShadowRoot(By.tagName("patient-details"), By.linkText("PROFILE"))
                .click();
        assertLocation("patients/1");

        getInShadowRoot(By.tagName("patient-details"),
                By.linkText("ALL PATIENTS")).click();
        assertLocation("patients");

        getInShadowRoot(By.tagName("main-view"), By.linkText("ANALYTICS"))
                .click();
        assertLocation("analytics");

        getInShadowRoot(By.tagName("vaadin-license-dialog"),
                By.id("licenseDialogClose")).click();

        getInShadowRoot(By.tagName("analytics-view"), By.linkText("DOCTOR"))
                .click();
        assertLocation("analytics/doctor");

        getInShadowRoot(By.tagName("analytics-view"), By.linkText("GENDER"))
                .click();
        assertLocation("analytics/gender");

        getInShadowRoot(By.tagName("analytics-view"), By.linkText("AGE"))
                .click();
        assertLocation("analytics/age");

        getInShadowRoot(By.tagName("main-view"), By.linkText("PATIENTS"))
                .click();
        assertLocation("patients");
    }

    private void assertLocation(String expectedLocation) {
        Assert.assertThat("Got incorrect page location",
                getDriver().getCurrentUrl(), CoreMatchers
                        .containsString(getRootURL() + '/' + expectedLocation));
    }
}
