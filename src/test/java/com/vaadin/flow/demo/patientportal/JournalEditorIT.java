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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class JournalEditorIT extends AbstractChromeTest {

    public static final String DATE = "20/10/2020";
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
        setLayout("journal-editor");

        setDate("date", DATE);

        selectFromComboBox("appointment", APPOINTMENT);

        selectFromComboBox("doctor", DOCTOR);

        getInShadowRoot(getLayout(), By.id("entry")).sendKeys(ENTRY);
        getInShadowRoot(getLayout(), By.id("entry")).sendKeys(Keys.ENTER);

        getInShadowRoot(getLayout(), By.id("save")).click();

        waitForElementPresent(By.xpath("//patient-journal"));
        setLayout("patient-journal");

        WebElement grid = getLayout().findElement(By.xpath("//vaadin-grid"));
        List<WebElement> cells = getChildren(grid);
        int index = cells.size() - 5;
        Assert.assertThat("Date of the new journal-entry should be displayed.",
                cells.get(index).getText(),
                CoreMatchers.allOf(
                        CoreMatchers.anyOf(CoreMatchers.containsString("10"),
                                CoreMatchers.containsString("October")),
                        CoreMatchers.containsString("20")));
        Assert.assertThat(
                "Appointment-type of the new journal-entry should be displayed.",
                cells.get(index + 1).getText(), is(APPOINTMENT));
        Assert.assertThat(
                "Doctor of the new journal-entry should be displayed.",
                cells.get(index + 2).getText(), is(DOCTOR.trim()));
        Assert.assertThat(
                "Entry-notes of the new journal-entry should be displayed.",
                cells.get(index + 4).getText(), is(ENTRY));

    }

}
