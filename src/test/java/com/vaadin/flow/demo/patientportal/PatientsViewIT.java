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
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.patientportal.converters.DateToStringEncoder;

public class PatientsViewIT extends AbstractChromeTest {

    @Override
    protected String getTestPath() {
        return "/patients";
    }

    @Test
    public void testPatientData() {
        open();

        waitForElementPresent(By.tagName("patients-view"));

        WebElement nameCell = getGridCellByContent("Wilson, Frederick");

        WebElement idCell = getGridCellByContent("21");

        // check that the name cell is on the same Y coordinate
        Assert.assertEquals("Unexpected patient on the row with id=21",
                idCell.getLocation().getY(), nameCell.getLocation().getY());

        String slotName = idCell.getAttribute("slot");
        int indx = slotName.lastIndexOf("-");
        int slotIndex = Integer
                .parseInt(slotName.substring(indx + 1, slotName.length()));

        String medicalRecord = getCellText(slotIndex + 1);
        Assert.assertTrue(
                "Grid should contain the medical record of the first patient.\n"
                        + "Expected: an integer value\n" + "Actual value: '"
                        + medicalRecord + "'",
                medicalRecord.matches("\\d+"));

        String doctorName = getCellText(slotIndex + 2);
        Assert.assertTrue(
                "Grid should contain the name of the first patient's doctor.\n"
                        + "Expected format: Lastname, Firstname\n"
                        + "Actual value: '" + doctorName + "'",
                doctorName.matches("\\w+, \\w+"));

        String lastVisit = getCellText(17);
        Assert.assertTrue(
                "Grid should contain the last visit date of the first patient.\n"
                        + "Expected format: " + DateToStringEncoder.DATE_FORMAT
                        + " or an empty string\n" + "Actual value: '"
                        + lastVisit + "'",
                lastVisit.matches("(\\d{2}/\\d{2}/\\d{4})|^$"));

    }

    private WebElement getGridCellByContent(String content) {
        List<WebElement> cells = findInShadowRoot(
                findElement(By.tagName("patients-view")),
                By.cssSelector("vaadin-grid-cell-content"));
        return cells.stream().filter(cell -> cell.getText().equals(content))
                .findFirst().get();
    }

    private WebElement getGridCell(int id) {
        List<WebElement> cells = findInShadowRoot(
                findElement(By.tagName("patients-view")),
                By.cssSelector("vaadin-grid-cell-content"));
        String slotId = "vaadin-grid-cell-content-" + id;
        return cells.stream()
                .filter(cell -> slotId.equals(cell.getAttribute("slot")))
                .findFirst().get();
    }

    private String getCellText(int id) {
        return getGridCell(id).getText();
    }

}
