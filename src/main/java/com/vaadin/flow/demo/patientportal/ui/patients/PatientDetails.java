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

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.demo.patientportal.ui.ParentPolymerTemplate;
import com.vaadin.flow.demo.patientportal.ui.PatientsView;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Objects;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-details")
@HtmlImport("frontend://components/main/patients/patient-details.html")
@RoutePrefix("patient")
@ParentLayout(PatientsView.class)
public class PatientDetails
        extends ParentPolymerTemplate<PatientDetails.PatientDetailsModel> implements
        RouterLayout {

    public interface PatientDetailsModel extends TemplateModel {
        void setPatientId(String patientId);
        String getPatientId();
    }

    @Override
    public void setRouterLayoutContent(HasElement content) {
        Long patientId = ((AbstractPatientTemplate) content).getPatient().getId();
        getModel().setPatientId(patientId+"");
        RouterLayout.super.setRouterLayoutContent(content);
    }
}
