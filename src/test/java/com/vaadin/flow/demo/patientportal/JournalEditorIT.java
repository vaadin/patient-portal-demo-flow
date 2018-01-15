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

import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Vaadin Ltd
 *
 */
public class JournalEditorIT extends AbstractChromeTest {

    public static final String DATE = "10/20/2020";
    public static final String APPOINTMENT = "X_RAY";
    public static final String DOCTOR = "Perry, Rose";
    public static final String ENTRY = "some notes";

    @Override
    protected String getTestPath() {
        return "/patient/new-entry/21";
    }

    @Test
    public void createJournalEntry() {
        open();

        waitForElementPresent(By.tagName("journal-editor"));
        setLayout("journal-editor");

        setDate("date", DATE);

        selectFromComboBox("appointment", APPOINTMENT);

        selectFromComboBox("doctor", DOCTOR);

        setTextFieldValue("entry", ENTRY);

        getInShadowRoot(getLayout(), By.id("save")).click();

        waitForElementPresent(By.tagName("patient-journal"));
        setLayout("patient-journal");

        WebElement grid = getInShadowRoot(getLayout(), By.id("grid"));
        List<WebElement> cells = getChildren(grid);
        int index = cells.size() - 5;
        Assert.assertThat("Date of the new journal-entry should be displayed.",
                cells.get(index++).getText(), is(DATE));
        Assert.assertThat(
                "Appointment-type of the new journal-entry should be displayed.",
                cells.get(index++).getText(), is(APPOINTMENT));
        Assert.assertThat(
                "Doctor of the new journal-entry should be displayed.",
                cells.get(index++).getText(), is(DOCTOR));
        Assert.assertThat(
                "Entry-notes of the new journal-entry should be displayed.",
                cells.get(index++).getText(), is(ENTRY));

    }

}
