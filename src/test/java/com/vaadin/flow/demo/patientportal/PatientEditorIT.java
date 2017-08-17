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

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author Vaadin Ltd
 *
 */
public class PatientEditorIT extends AbstractChromeTest {

    public static final String TITLE = "Miss";
    public static final String FIRST_NAME = "Flow";
    public static final String MIDDLE_NAME = "Is";
    public static final String LAST_NAME = "Awesome";
    public static final String GENDER = "female";
    public static final String BIRTH_DATE = "06/13/1993";
    public static final String SSN = "453-87-1829";
    public static final String DOCTOR = "Burns, Gail";

    @Override
    protected String getTestPath() {
        return "/patients/5/edit";
    }

    @Test
    public void testEditingPatient() {
        open();

        waitForElementPresent(By.tagName("patient-editor"));
        layout = findElement(By.tagName("patient-editor"));

        selectFromComboBox("title", TITLE);
        setTextFieldValue("firstName", FIRST_NAME);
        setTextFieldValue("middleName", MIDDLE_NAME);
        setTextFieldValue("lastName", LAST_NAME);
        selectFromComboBox("gender", GENDER);
        setDate("birthDate", BIRTH_DATE);
        setTextFieldValue("ssn", SSN);
        selectFromComboBox("doctor", DOCTOR);

        getInShadowRoot(layout, By.id("save")).click();
        waitForElementPresent(By.tagName("patient-profile"));
        layout = findElement(By.tagName("patient-profile"));

        assertValue("firstName", FIRST_NAME);
        assertValue("middleName", MIDDLE_NAME);
        assertValue("lastName", LAST_NAME);
        assertValue("gender", GENDER);
        assertValue("birthDate", BIRTH_DATE);
        assertValue("ssn", SSN);
        assertValue("doctor", DOCTOR);
    }

    private void assertValue(String elementId, String expectedValue) {
        Assert.assertThat("Edited " + elementId + " should be displayed.",
                getInShadowRoot(layout, By.id(elementId)).getText(),
                is(expectedValue));
    }
}
