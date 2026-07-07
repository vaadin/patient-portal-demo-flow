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

package com.vaadin.flow.demo.patientportal.ui.patients;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.demo.patientportal.converters.DateToStringEncoder;
import com.vaadin.flow.demo.patientportal.converters.GenderToStringEncoder;
import com.vaadin.flow.demo.patientportal.converters.LongToStringEncoder;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.demo.patientportal.ui.LoginView;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;

import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

/**
 * Superclass for all of the patient-specific {@link LitTemplate}-views,
 * that have a {@link Patient} bound as a JSON property and have the
 * patient's id in their URL.
 *
 * @author Vaadin Ltd
 *
 */
public abstract class AbstractPatientTemplate extends LitTemplate implements HasUrlParameter<Long> {

    private Patient patient;

    @Autowired
    private transient PatientService patientService;

    @Override
    public void setParameter(BeforeEvent event, Long patientId) {
        if (UI.getCurrent().getSession().getAttribute("login") == null) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigate(LoginView.class);
            return;
        }
        Optional<Patient> optionalPatient = patientService.getPatient(patientId);
        if (optionalPatient.isPresent()) {
            loadPatient(optionalPatient.get());
        } else {
            String msg = "Patient with id " + patientId + " was not found.";
            Logger.getLogger(AbstractPatientTemplate.class.getName())
                    .info(msg);
            event.rerouteToError(IllegalArgumentException.class, msg);
        }
    }

    protected void loadPatient(Patient aPatient) {
        patient = aPatient;

        ObjectNode json = JsonNodeFactory.instance.objectNode();
        String idStr = new LongToStringEncoder().encode(patient.getId());
        json.put("id", idStr != null ? idStr : "");
        json.put("firstName", patient.getFirstName() != null ? patient.getFirstName() : "");
        json.put("middleName", patient.getMiddleName() != null ? patient.getMiddleName() : "");
        json.put("lastName", patient.getLastName() != null ? patient.getLastName() : "");
        String genderStr = patient.getGender() != null ? new GenderToStringEncoder().encode(patient.getGender()) : "";
        json.put("gender", genderStr != null ? genderStr : "");
        String birthDateStr = new DateToStringEncoder().encode(patient.getBirthDate());
        json.put("birthDate", birthDateStr != null ? birthDateStr : "");
        json.put("ssn", patient.getSsn() != null ? patient.getSsn() : "");
        String medicalRecordStr = new LongToStringEncoder().encode(patient.getMedicalRecord());
        json.put("medicalRecord", medicalRecordStr != null ? medicalRecordStr : "");
        String lastVisitStr = patient.getLastVisit() != null ? new DateToStringEncoder().encode(patient.getLastVisit()) : "";
        json.put("lastVisit", lastVisitStr != null ? lastVisitStr : "");
        json.put("pictureUrl", patient.getPictureUrl() != null ? patient.getPictureUrl() : "");

        ObjectNode doctor = JsonNodeFactory.instance.objectNode();
        if (patient.getDoctor() != null) {
            doctor.put("firstName", patient.getDoctor().getFirstName() != null ? patient.getDoctor().getFirstName() : "");
            doctor.put("lastName", patient.getDoctor().getLastName() != null ? patient.getDoctor().getLastName() : "");
        }
        json.set("doctor", doctor);

        getElement().setPropertyJson("patient", json);
    }

    protected Patient getPatient() {
        return patient;
    }
}
