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

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.demo.patientportal.ui.ParentPolymerTemplate;
import com.vaadin.flow.demo.patientportal.ui.PatientsView;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.template.model.TemplateModel;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-details")
@HtmlImport("/components/main/patients/patient-details.html")
@ParentView(PatientsView.class)
public class PatientDetails
        extends ParentPolymerTemplate<PatientDetails.PatientDetailsModel> {

    public interface PatientDetailsModel extends TemplateModel {
        void setPatientId(String patientId);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        getModel().setPatientId(locationChangeEvent.getPathParameter("id"));
    }

}
