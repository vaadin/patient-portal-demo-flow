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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Vaadin Ltd
 *
 */
public class PatientsViewIT extends AbstractChromeTest {

    @Override
    protected String getTestPath() {
        return "/patients";
    }

    @Test
    public void testPatientData() {
        open();

        Assert.assertThat(
                "Grid should contain the first name of the first patient.",
                getCellText(13), containsString("Frederick"));
        Assert.assertThat(
                "Grid should contain the last name of the first patient.",
                getCellText(13), containsString("Wilson"));

        Assert.assertThat("Grid should contain the id of the first patient.",
                getCellText(14), is("1"));

        String medicalRecord = getCellText(15);
        Assert.assertTrue(
                "Grid should contain the medical record of the first patient.\n"
                        + "Expected: an integer value\n" + "Actual value: '"
                        + medicalRecord + "'",
                medicalRecord.matches("\\d+"));

        String doctorName = getCellText(16);
        Assert.assertTrue(
                "Grid should contain the name of the first patient's doctor.\n"
                        + "Expected format: Lastname, Firstname\n"
                        + "Actual value: '" + doctorName + "'",
                doctorName.matches("\\w+, \\w+"));

        String lastVisit = getCellText(17);
        Assert.assertTrue(
                "Grid should contain the last visit date of the first patient.\n"
                        + "Expected format: yyyy-mm-dd or an empty string\n"
                        + "Actual value: '" + lastVisit + "'",
                lastVisit.matches("(\\d{4}-\\d{2}-\\d{2})|^$"));

    }

    private WebElement getGridCell(int id) {
        return getInShadowRoot(By.tagName("patients-view"),
                By.id("vaadin-grid-cell-content-" + id));
    }

    private String getCellText(int id) {
        return getGridCell(id).getText();
    }
}
