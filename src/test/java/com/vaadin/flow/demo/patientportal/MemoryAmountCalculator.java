/*
 * Copyright 2000-2023 Vaadin Ltd.
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

import java.lang.reflect.InaccessibleObjectException;

import org.github.jamm.MemoryMeter;
import org.slf4j.LoggerFactory;

public class MemoryAmountCalculator {

    private static final MemoryMeter memoryMeter = MemoryMeter.builder().build();

    static long getObjectSize(Object o) {
        try {
            return memoryMeter.measureDeep(o);
        } catch (RuntimeException ex) {
            // Unwrap original exception
            Throwable cause = ex;
            while (cause != null && RuntimeException.class.equals(cause.getClass())) {
                cause = cause.getCause();
            }
            if (cause instanceof InaccessibleObjectException) {
                // Print error message to the console so that it will be immediately visible
                // and not hidden by application error handlers
                System.err.println("[ERROR] " + cause.getMessage());
                System.err.println("[ERROR] Usually, to fix the problem a '--add-opens' flag must be added to JVM arguments");
            }
            throw ex;
        }
    }
}
