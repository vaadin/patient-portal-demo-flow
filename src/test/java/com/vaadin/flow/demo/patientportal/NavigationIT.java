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

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class NavigationIT extends AbstractChromeTest {

    private final int patientId = 21;

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
    public void testNavigation() throws InterruptedException {
        open();

        getInShadowRoot(By.tagName("login-view"), By.id("login-button"))
                .click();
        waitLocation("patients");

        // Click on the first cell that actually contains patient-data:
        List<WebElement> cells = findInShadowRoot(
                findElement(By.tagName("patients-view")),
                By.cssSelector("vaadin-grid-cell-content"));
        WebElement firstPatient = cells.stream().filter(
                cell -> cell.getText().equals(String.valueOf(patientId)))
                .findFirst().get();
        firstPatient.click();

        waitLocation("patients/" + patientId);

        getInShadowRoot(By.tagName("patient-details"), By.linkText("JOURNAL"))
                .click();
        waitLocation("patients/journal/" + patientId);

        getInShadowRoot(By.tagName("patient-journal"),
                By.partialLinkText("NEW ENTRY")).click();
        waitLocation("patients/new-entry/" + patientId);

        getInShadowRoot(By.tagName("patient-details"),
                By.linkText("EDIT PATIENT")).click();
        waitLocation("patients/edit/" + patientId);

        getInShadowRoot(By.tagName("patient-details"), By.linkText("PROFILE"))
                .click();
        waitLocation("patients/" + patientId);

        getInShadowRoot(By.tagName("patient-details"),
                By.linkText("ALL PATIENTS")).click();
        waitLocation("patients");

        getInShadowRoot(By.tagName("main-view"), By.linkText("ANALYTICS"))
                .click();
        waitLocation("analytics");

        List<WebElement> dialogs = findElements(
                By.tagName("vaadin-license-dialog"));
        if (dialogs.size() > 0) {
            getInShadowRoot(dialogs.get(0), By.id("licenseDialogClose"))
                    .click();
        }

        List<WebElement> links = findElement(
                By.tagName("vaadin-horizontal-layout"))
                        .findElements(By.tagName("a"));
        WebElement doctor = links.get(1);
        Assert.assertEquals("Doctor", doctor.getText());
        doctor.click();
        waitLocation("analytics/doctor");

        WebElement gender = links.get(2);
        Assert.assertEquals("Gender", gender.getText());
        gender.click();
        waitLocation("analytics/gender");

        WebElement age = links.get(0);
        Assert.assertEquals("Age", age.getText());
        age.click();
        waitLocation("analytics/age");

        getInShadowRoot(By.tagName("main-view"), By.linkText("PATIENTS"))
                .click();
        waitLocation("patients");

        getInShadowRoot(By.tagName("patients-view"), By.id("patientsGrid"));

        getInShadowRoot(By.tagName("main-view"), By.id("logout")).click();
        waitLocation("");

        getInShadowRoot(By.tagName("login-view"), By.id("login-button"));
    }

    private void waitLocation(String expectedLocation) {
        waitUntil(new LocationCondition(expectedLocation), 20);
    }
}
