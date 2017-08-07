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

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Convert;
import com.vaadin.annotations.Include;
import com.vaadin.demo.entities.JournalEntry;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.demo.patientportal.converters.DateToStringConverter;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

/**
 * Superclass for all of the patient-specific {@link PolymerTemplate}-views,
 * that have a {@link Patient} bound in their {@link TemplateModel} and have the
 * patient's id in their URL.
 * 
 * @author Vaadin Ltd
 *
 */
public abstract class AbstractPatientTemplate<M extends AbstractPatientTemplate.PatientTemplateModel>
        extends PolymerTemplate<M> implements View {

    @Autowired
    private PatientService patientService;

    private Patient patient;

    public interface PatientTemplateModel extends TemplateModel {

        @Include({ "firstName", "middleName", "lastName", "doctor.firstName",
                "doctor.lastName", "pictureUrl" })
        void setPatient(Patient p);

        @Convert(value = DateToStringConverter.class, path = "date")
        @Include({ "entry", "doctor.firstName", "doctor.lastName", "date" })
        void setEntries(List<JournalEntry> entries);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        try {
            long id = Long
                    .parseLong(locationChangeEvent.getPathParameter("id"));
            Optional<Patient> patient = patientService.getPatient(id);
            if (patient.isPresent()) {
                this.patient = patient.get();
                getModel().setPatient(this.patient);
            } else {
                Logger.getLogger(AbstractPatientTemplate.class.getName())
                        .info("Patient with id " + id + " was not found.");
                locationChangeEvent.rerouteToErrorView();
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(AbstractPatientTemplate.class.getName())
                    .info("Failed to parse patient's id from the url.");
            locationChangeEvent.rerouteToErrorView();
        }
    }

    public Patient getPatient() {
        return patient;
    }
}
