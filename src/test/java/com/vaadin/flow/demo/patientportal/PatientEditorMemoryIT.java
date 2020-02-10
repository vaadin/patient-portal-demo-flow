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

import org.openqa.selenium.By;

public class PatientEditorMemoryIT extends AbstractMemoryMeasurementIT {

    @Override
    protected String getTestPath() {
        return "/patients/edit/test/21";
    }

    @Override
    protected long getGoldenAmount() {
        return 267040;
    }

    @Override
    protected String getStatKey() {
        return "editor";
    }

    @Override
    protected void doOpen() {
        super.doOpen();
        if(getInShadowRoot(getLayout(), By.id("login-button")) != null) {
            getInShadowRoot(getLayout(), By.id("login-button")).click();
            super.doOpen();
        }
    }
}
