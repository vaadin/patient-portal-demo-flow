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

package com.vaadin.flow.demo.patientportal.ui.patients;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-editor")
@HtmlImport("/components/main/patients/patient-editor.html")
@Route("patients/{id}/edit")
@ParentView(PatientDetails.class)
public class PatientEditor extends
        AbstractPatientTemplate<AbstractPatientTemplate.PatientTemplateModel> {

    @EventHandler
    private void close() {

    }

    @EventHandler
    private void save() {

    }
}