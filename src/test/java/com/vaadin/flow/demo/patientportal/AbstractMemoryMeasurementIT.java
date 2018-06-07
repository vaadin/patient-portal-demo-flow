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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testcategory.Measurement;

@Category(Measurement.class)
public abstract class AbstractMemoryMeasurementIT extends AbstractChromeTest {

    private static final int UIS_NUMBER = 5;

    private static final double FAILURE_THRESHOLD = 5 * 0.01; // 5%

    @Test
    public void memoryConsumption() {
        long previous = 0;
        long uiSize = 0;
        List<Long> sizes = new ArrayList<>();
        int i = 1;
        while (!isStable(uiSize, sizes)) {
            sizes.add(uiSize);

            doOpen();
            long current = getMemory(i);
            uiSize = current - previous;
            previous = current;
            i++;
        }

        printTeamcityStats(uiSize);

        double gold = getGoldenAmount() * (1 + FAILURE_THRESHOLD);
        Assert.assertTrue(
                "The UI instance size is '" + uiSize + "' which is bigger than "
                        + "expected allowed amount :'" + gold + "'.",
                gold > uiSize);
    }

    protected abstract long getGoldenAmount();

    protected abstract String getStatKey();

    private boolean isStable(long size, List<Long> sizes) {
        if (sizes.size() < UIS_NUMBER) {
            return false;
        }
        for (int i = sizes.size() - 1; i >= sizes.size() - UIS_NUMBER; i--) {
            long previous = sizes.get(i);
            if (Math.abs(size - previous) >= previous * FAILURE_THRESHOLD) {
                return false;
            }
        }
        return true;
    }

    private long getMemory(int uisAmount) {
        findElement(By.id("show-memory")).click();
        WebElement uis = findElement(By.id("uis"));

        Assert.assertEquals(String.valueOf(uisAmount), uis.getText());

        WebElement sessionMemory = findElement(By.id("memory"));
        return Long.parseLong(sessionMemory.getText());
    }

    private void printTeamcityStats(long value) {
        System.out.println(String.format(
                "##teamcity[buildStatisticValue key='%s' value='%s']",
                getStatKey(), Long.toString(value)));
    }
}
