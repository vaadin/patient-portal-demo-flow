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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.testbench.TestBenchElement;

import static org.hamcrest.CoreMatchers.is;

public class JournalEditorIT extends AbstractChromeTest {

    public static final String DATE = "10/20/2020";
    public static final String APPOINTMENT = "X_RAY";
    public static final String DOCTOR = "Number 0, Doc ";
    public static final String ENTRY = "some notes";

    @Override
    protected String getTestPath() {
        return "/patients/new-entry/21";
    }

    @Test
    public void createJournalEntry() {
        open();

        waitForElementPresent(By.xpath("//journal-editor"));
        setLayout($("patients-view").first().$("patient-details").first().$("journal-editor").first());

        setDate("date", DATE);

        selectFromComboBox("appointment", APPOINTMENT);

        selectFromComboBox("doctor", DOCTOR);

        TestBenchElement entryField = getLayout().$("*").id("entry");

        new Actions(getDriver()).sendKeys(entryField, ENTRY)
                .sendKeys(Keys.ENTER)
                .pause(500)
                .click(getLayout().$("*").id("save"))
                .perform();

        waitForElementPresent(By.xpath("//patient-journal"));
        setLayout($("patients-view").first().$("patient-details").first().$("patient-journal").first());

        GridElement grid = $(GridElement.class).first();

        Assert.assertThat("Date of the new journal-entry should be displayed.",
                grid.getCell(0,0).getText(),
                CoreMatchers.allOf(
                        CoreMatchers.anyOf(CoreMatchers.containsString("10"),
                                CoreMatchers.containsString("October")),
                        CoreMatchers.containsString("20")));
        Assert.assertThat(
                "Appointment-type of the new journal-entry should be displayed.",
                grid.getCell(0,1).getText(), is(APPOINTMENT));
        Assert.assertThat(
                "Doctor of the new journal-entry should be displayed.",
                grid.getCell(0,2).getText().trim(), is(DOCTOR.trim()));
        Assert.assertThat(
                "Entry-notes of the new journal-entry should be displayed.",
                grid.getCell(0,3).getText(), is(ENTRY));

    }

}
