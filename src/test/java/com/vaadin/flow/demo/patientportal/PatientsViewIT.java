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

import com.vaadin.flow.component.grid.testbench.GridColumnElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTHTDElement;
import com.vaadin.flow.demo.patientportal.converters.DateToStringEncoder;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchElement;

public class PatientsViewIT extends AbstractChromeTest {

    @Override
    protected String getTestPath() {
        return "/patients";
    }

    @Test
    public void testPatientData() {
        open();

        waitForElementPresent(By.xpath("//patients-view"));
        TestBenchElement testBenchElement = $(TestBenchElement.class).all()
                .stream()
                .filter(tbe -> tbe.getTagName().equals("patients-view"))
                .findFirst().get();
        GridElement grid = testBenchElement.$(GridElement.class).id("patientsGrid");

        GridColumnElement medicalRecordColumn = grid.getColumn("Medical Record");
        GridColumnElement doctorColumn = grid.getColumn("Doctor");
        GridColumnElement lastVisitColumn = grid.getColumn("Last Visit");

        GridTHTDElement cell = grid.getCell("Last 10, First10");

        String medicalRecord = grid.getCell(cell.getRow(), medicalRecordColumn).getText();
        Assert.assertTrue(
                "Grid should contain the medical record of the first patient.\n"
                        + "Expected: an integer value\n" + "Actual value: '"
                        + medicalRecord + "'",
                medicalRecord.matches("\\d+"));

        String doctorName = grid.getCell(cell.getRow(), doctorColumn).getText().trim();
        Assert.assertTrue(
                "Grid should contain the name of the first patient's doctor.\n"
                        + "Expected format: Lastname, Firstname\n"
                        + "Actual value: '" + doctorName + "'",
                doctorName.matches("\\w+(\\s\\w+)?, \\w+"));


        String lastVisit = grid.getCell(cell.getRow(), lastVisitColumn).getText();
        Assert.assertTrue(
                "Grid should contain the last visit date of the first patient.\n"
                        + "Expected format: " + DateToStringEncoder.DATE_FORMAT
                        + " or an empty string\n" + "Actual value: '"
                        + lastVisit + "'",
                lastVisit.matches("(\\d{2}/\\d{2}/\\d{4})|^$"));
    }

}
