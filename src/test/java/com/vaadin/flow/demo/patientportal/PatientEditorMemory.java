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

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.demo.patientportal.ui.patients.PatientDetails;
import com.vaadin.flow.demo.patientportal.ui.patients.PatientEditor;
import com.vaadin.flow.router.Route;

@Route(value = "edit/test", layout = PatientDetails.class)
public class PatientEditorMemory extends PatientEditor
        implements HasComponents {

    @Autowired
    public PatientEditorMemory(PatientService patientService) {
        super(patientService);

        add(new MemoryMeasurement());
    }

}
