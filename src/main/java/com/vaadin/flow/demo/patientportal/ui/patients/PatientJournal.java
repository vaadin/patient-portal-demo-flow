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

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.demo.patientportal.ui.LoginView;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;
import com.vaadin.ui.UI;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-journal")
@HtmlImport("frontend://components/main/patients/patient-journal.html")
@Route("patients/{id}/journal")
@ParentView(PatientDetails.class)
public class PatientJournal extends
        AbstractPatientTemplate<AbstractPatientTemplate.PatientTemplateModel> {

    @Autowired
    private PatientService patientService;

    @Override
    @Transactional
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        if (UI.getCurrent().getSession().getAttribute("login") == null) {
            locationChangeEvent.rerouteTo(LoginView.class);
            UI.getCurrent().navigateTo("");
            return;
        }
        super.onLocationChange(locationChangeEvent);
        Patient patient = patientService.findAttached(getPatient());
        getModel().setEntries(patient.getJournalEntries());
    }

}
