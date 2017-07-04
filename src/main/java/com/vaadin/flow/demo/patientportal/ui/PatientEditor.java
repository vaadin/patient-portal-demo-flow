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

package com.vaadin.flow.demo.patientportal.ui;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-editor")
@HtmlImport("/components/main/patients/patient-editor.html")
public class PatientEditor extends
        PolymerTemplate<PatientEditor.PatientEditorModel> implements View {

    public PatientEditor() {
    }

    @EventHandler
    private void close() {
        System.out.println("close");
    }

    @EventHandler
    private void save() {
        System.out.println("save");
    }

    public interface PatientEditorModel extends TemplateModel {
    }
}
