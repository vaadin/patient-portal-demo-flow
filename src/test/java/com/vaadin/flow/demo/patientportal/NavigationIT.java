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

import com.vaadin.flow.component.grid.testbench.GridElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.Random;

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

        waitForElementPresent(By.xpath("//patients-view"));

        var patientsGrid = $("patients-view").first().$(GridElement.class).first();
        Random random = new Random(System.nanoTime());
        var rowIndex = random.nextInt(0, patientsGrid.getRowCount() - 1);
        var cell = patientsGrid.getCell(rowIndex, 1);
        cell.click();
        var patientId = cell.getText();

        waitLocation("patients/" + patientId);

        $("*").id("patients-view").$("patient-details").first().$("*")
                .id("journal").click();
        waitLocation("patients/journal/" + patientId);

        $("patients-view").first().$("patient-details").first()
                .$("patient-journal").first().$("*").id("new").click();
        waitLocation("patients/new-entry/" + patientId);

        $("patients-view").first().$("patient-details").first().$("*")
                .id("edit").click();
        waitLocation("patients/edit/" + patientId);

        $("patients-view").first().$("patient-details").first().$("*")
                .id("profile").click();
        waitLocation("patients/" + patientId);

        $("main-view").first().$("patients-view").first().$("patient-details")
                .first().$("*").id("all-patients").click();
        waitLocation("patients");

        $("main-view").first().$("*").id("analytics").click();
        waitLocation("analytics");

        if ($("vaadin-license-dialog").exists()) {
            $("vaadin-license-dialog").first().$("*").id("licenseDialogClose")
                    .click();
        }

        List<WebElement> links = findElement(
                By.xpath("//vaadin-horizontal-layout")).findElements(
                By.xpath("//a"));
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

        $("main-view").first().$("*").id("patients").click();
        waitLocation("patients");

        $("main-view").first().$("*").id("logout").click();
        waitLocation("");

        Assert.assertTrue(
                $("login-view").first().$("*").attribute("id", "login-button").exists());
    }

    private void waitLocation(String expectedLocation) {
        waitUntil(new LocationCondition(expectedLocation), 20);
    }
}
