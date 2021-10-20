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
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.testutil.AbstractTestBenchTest;

import static org.hamcrest.CoreMatchers.is;

public class PatientEditorIT extends AbstractChromeTest {

    public static final String TITLE = "Miss";
    public static final String FIRST_NAME = "Flow";
    public static final String MIDDLE_NAME = "Is";
    public static final String LAST_NAME = "Awesome";
    public static final String GENDER = "female";
    public static final String BIRTH_DATE = "06/13/1993";
    public static final String SSN = "453-87-1829";
    public static final String DOCTOR = "Number 1, Doc ";

    @Override
    protected String getTestPath() {
        return "/test-ids";
    }

    @Test
    public void editPatient() {
        doOpen();

        List<WebElement> ids = findElements(By.className("patient-id"));

        // self check
        Assert.assertTrue(ids.size() > 1);
        String id = ids.get(1).getText();

        getDriver().get(AbstractTestBenchTest.getTestURL(getRootURL(), "/"));
        login();
        getDriver().get(AbstractTestBenchTest
                .getTestURL(getRootURL(), "/patients/edit/" + id));

        waitForElementPresent(By.xpath("//patient-editor"));
        setLayout("patient-editor");

        selectFromComboBox("title", TITLE);
        setTextField("firstName", FIRST_NAME);
        setTextField("middleName", MIDDLE_NAME);
        setTextField("lastName", LAST_NAME);
        selectFromComboBox("gender", GENDER);
        setDate("birthDate", BIRTH_DATE);
        setTextField("ssn", SSN);
        selectFromComboBox("doctor", DOCTOR);

        getInShadowRoot(getLayout(), By.id("save")).click();
        waitForElementPresent(By.xpath("//patient-profile"));
        setLayout("patient-profile");

        assertValue("firstName", FIRST_NAME);
        assertValue("middleName", MIDDLE_NAME);
        assertValue("lastName", LAST_NAME);
        assertValue("gender", GENDER);
        assertValue("birthDate", BIRTH_DATE);
        assertValue("ssn", SSN);
        assertValue("doctor", DOCTOR.trim());
    }

    @Test
    public void deletePatient() {
        //        doOpen();
        getDriver().get(AbstractTestBenchTest.getTestURL(getRootURL(), "/"));
        login();

        WebElement nameCell = getGridCellByContent("Last 5, First5").get();

        nameCell.click();

        waitForElementPresent(By.xpath("//patient-details"));
        String currentUrl = driver.getCurrentUrl();
        String id = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
        getDriver().get(AbstractTestBenchTest
                .getTestURL(getRootURL(), "/patients/edit/" + id));

        waitForElementPresent(By.xpath("//patient-editor"));
        setLayout("patient-editor");

        getInShadowRoot(getLayout(), By.id("delete")).click();

        waitForElementPresent(By.xpath("//patients-view"));
        setLayout("patients-view");

        Assert.assertFalse("Id " + id
                        + " of the deleted patient was still found in the patients-grid.",
                getGridCellByContent("Last 5, First5").isPresent());
    }

    private Optional<WebElement> getGridCellByContent(String content) {
        List<WebElement> cells = findInShadowRoot(
                findElement(By.xpath("//patients-view")),
                By.cssSelector("vaadin-grid-cell-content"));
        return cells.stream().filter(cell -> cell.getText().equals(content))
                .findFirst();
    }

    protected void setTextField(String fieldId, String value) {
        WebElement field = getInShadowRoot(getLayout(), By.id(fieldId));
        WebElement inputElement = field.findElement(By.tagName("input"));
        inputElement.clear();
        field.sendKeys(value);
        field.sendKeys(Keys.ENTER);
    }

    private void assertValue(String elementId, String expectedValue) {
        Assert.assertThat("Edited " + elementId + " should be displayed.",
                getInShadowRoot(getLayout(), By.id(elementId)).getText(),
                is(expectedValue));
    }
}
