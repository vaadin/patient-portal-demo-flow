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

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Include;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-details")
@HtmlImport("/components/main/patients/patient-details.html")
@Route("patients/{id}/*")
@ParentView(PatientsView.class)
public class PatientDetails extends
        PolymerTemplate<PatientDetails.PatientDetailsModel> implements View {

    @Autowired
    private PatientService patientService;

    public PatientDetails() {
    }

    public interface PatientDetailsModel extends TemplateModel {

        @Include({ "firstName", "middleName", "lastName", "doctor.firstName",
                "doctor.lastName", "pictureUrl" })
        void setPatient(Patient p);

        void setPage(String page);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        try {
            long id = Long
                    .parseLong(locationChangeEvent.getPathParameter("id"));
            Patient p = patientService.getPatient(id);
            if (p != null) {
                getModel().setPatient(p);
            } else {
                System.out.println("Patient with id " + id + " not found");
                locationChangeEvent.rerouteTo(PatientsView.class);
            }
        } catch (NumberFormatException e) {
            System.out.println("Couldn't parse id from the path");
            locationChangeEvent.rerouteTo(PatientsView.class);
        }

        getModel().setPage(locationChangeEvent.getPathWildcard());
    }
}
