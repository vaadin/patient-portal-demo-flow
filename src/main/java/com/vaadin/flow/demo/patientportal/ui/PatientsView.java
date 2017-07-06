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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Include;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.router.HasChildView;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;
import com.vaadin.ui.AttachEvent;
import com.vaadin.ui.UI;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patients-view")
@HtmlImport("/components/main/patients/patients-view.html")
@Route("patients")
@ParentView(MainView.class)
public class PatientsView
        extends PolymerTemplate<PatientsView.PatientsViewModel>
        implements HasChildView {

    @Autowired
    private PatientService patientService;

    private View patientDetails;

    public PatientsView() {

        getElement().addPropertyChangeListener("currentPatient", event -> {
            // TODO: Make this navigate to /patients/{current-patient-id}.
            System.out.println("current patient changed");
        });

        // TODO: Remove this when proper patient-navigation can be implemented.
        getElement().addEventListener("click", event -> {
            UI.getCurrent().navigateTo("patients/1");
        });
    }

    public interface PatientsViewModel extends TemplateModel {

        @Include({ "firstName", "lastName" })
        void setPatients(List<Patient> patients);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        getModel().setPatients(patientService.getPatients());
    }

    @Override
    public void setChildView(View childView) {
        if (patientDetails != null) {
            getElement().removeChild(this.patientDetails.getElement());
        }
        patientDetails = childView;
        if (patientDetails == null)
            return;

        getElement().appendChild(patientDetails.getElement());
    }

}
