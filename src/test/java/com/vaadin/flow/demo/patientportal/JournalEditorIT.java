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
import org.openqa.selenium.Keys;
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
        return "/patients/1/new-entry";
    }

    @Test
    public void testCreatingJournalEntry() {
        open();

        waitForElementPresent(By.tagName("journal-editor"));
        WebElement layout = findElement(By.tagName("journal-editor"));

        setDate(layout, "date", DATE);

        selectFromComboBox("appointment", APPOINTMENT);

        selectFromComboBox("doctor", DOCTOR);

        WebElement entryField = getInShadowRoot(layout, By.id("entry"));
        entryField.sendKeys(ENTRY);

        getInShadowRoot(layout, By.id("save")).click();

        waitForElementPresent(By.tagName("patient-journal"));
        layout = findElement(By.tagName("patient-journal"));

        WebElement grid = getInShadowRoot(layout, By.id("grid"));
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

    private void selectFromComboBox(String id, String value) {
        WebElement comboBox = getInShadowRoot(By.tagName("journal-editor"),
                By.id(id));
        comboBox.sendKeys(value);
        comboBox.sendKeys(Keys.ENTER);
    }
}
