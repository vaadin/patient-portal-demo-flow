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
import java.util.LongSummaryStatistics;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.demo.testcategory.Measurement;

@Category(Measurement.class)
public abstract class AbstractMemoryMeasurementIT extends AbstractChromeTest {

    private static final int UIS_NUMBER = 5;
    private static final int MAX_UIS_OPENED = 300;

    private static final double FAILURE_THRESHOLD = 5 * 0.01; // 5%

    private static final Logger logger = LoggerFactory.getLogger("Measurement");

    @Test
    public void memoryConsumption() {
        long previous = 0;
        long uiSize = 0;
        List<Long> sizes = new ArrayList<>();
        int i = 1;
        logger.info("Starting generation of ui's to get size");
        // open ui:s until size stable enough or a limit reached.
        while (true) {
            // If UI size has stabilized break loop
            if (hasUiSizeStabilized(uiSize, sizes)) {
                break;
            }

            sizes.add(uiSize);
            // If we have opened over MAX_UIS_OPENED ui:s, break loop and use an average
            if (shouldBreakWithAverage(i, sizes)) {
                logger.info(
                        "No stability over {} ui:s returning average ui size",
                        i);
                uiSize = getAverageUiSize(sizes);
                break;
            }

            doOpen();
            long current = getMemory(i);
            // See how much session size increased after adding one UI
            uiSize = current - previous;
            previous = current;
            i++;
        }
        logger.info("Generated {} uis for size comparison.", i);

        printTeamcityStats(uiSize);

        double gold = getGoldenAmount() * (1 + FAILURE_THRESHOLD);
        Assert.assertTrue(
                "The UI instance size is '" + uiSize + "' which is bigger than "
                        + "expected allowed amount :'" + gold + "'.",
                gold > uiSize);
    }

    private boolean shouldBreakWithAverage(int i, List<Long> sizes) {
        return i >= MAX_UIS_OPENED && sizes.size() >= UIS_NUMBER;
    }

    private long getAverageUiSize(List<Long> sizes) {
        LongSummaryStatistics statistics = sizes.stream()
                .mapToLong(value -> value).summaryStatistics();
        long uiSize = (long) statistics.getAverage();

        return uiSize;
    }

    protected abstract long getGoldenAmount();

    protected abstract String getStatKey();

    private boolean hasUiSizeStabilized(long size, List<Long> sizes) {
        if (sizes.size() < UIS_NUMBER) {
            return false;
        }
        for (int i = sizes.size() - 1; i >= sizes.size() - UIS_NUMBER; i--) {
            long previous = sizes.get(i);
            if (Math.abs(size - previous) >= previous * FAILURE_THRESHOLD) {
                logger.info("UI size still unstable: {} >= {} against ui {}",
                        Math.abs(size - previous), previous * FAILURE_THRESHOLD,
                        i);
                // Clear sizes and only add last so we run up to 5 uis
                // before retesting as the probability of hitting Threshold
                // for all 5 old ones is minimal
                sizes.clear();
                sizes.add(size);
                return false;
            }
        }
        logger.info("Got stable ui size with {} uis", sizes.size());
        return true;
    }

    private long getMemory(int uisAmount) {
        waitForElementPresent(By.id("show-memory"));
        findElement(By.id("show-memory")).click();
        WebElement uis = findElement(By.id("uis"));

        Assert.assertEquals(String.valueOf(uisAmount), uis.getText());

        waitForElementPresent(By.id("memory"));
        WebElement sessionMemory = findElement(By.id("memory"));
        return Long.parseLong(sessionMemory.getText());
    }

    private void printTeamcityStats(long value) {
        System.out.println(String.format(
                "##teamcity[buildStatisticValue key='%s' value='%s']",
                getStatKey(), Long.toString(value)));
    }
}
