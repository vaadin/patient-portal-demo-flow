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

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.patientportal.converters.DateToStringConverter;
import com.vaadin.flow.demo.patientportal.converters.GenderToStringConverter;
import com.vaadin.flow.demo.patientportal.converters.LongToStringConverter;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.demo.patientportal.ui.LoginView;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.templatemodel.Convert;
import com.vaadin.flow.templatemodel.Include;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Superclass for all of the patient-specific {@link PolymerTemplate}-views,
 * that have a {@link Patient} bound in their {@link TemplateModel} and have the
 * patient's id in their URL.
 * 
 * @author Vaadin Ltd
 *
 */
public abstract class AbstractPatientTemplate<M extends AbstractPatientTemplate.PatientTemplateModel>
        extends PolymerTemplate<M> implements HasUrlParameter<Long> {

    private Patient patient;

    @Autowired
    private transient PatientService patientService;

    public interface PatientTemplateModel extends TemplateModel {

        @Include({ "firstName", "middleName", "lastName", "gender", "birthDate",
                "ssn", "id", "doctor.firstName", "doctor.lastName",
                "medicalRecord", "lastVisit", "pictureUrl" })
        @Convert(value = LongToStringConverter.class, path = "id")
        @Convert(value = LongToStringConverter.class, path = "medicalRecord")
        @Convert(value = DateToStringConverter.class, path = "birthDate")
        @Convert(value = DateToStringConverter.class, path = "lastVisit")
        @Convert(value = GenderToStringConverter.class, path = "gender")
        void setPatient(Patient patient);

    }


    @Override
    public void setParameter(BeforeEvent event, Long patientId) {
        if (UI.getCurrent().getSession().getAttribute("login") == null) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("");
            return;
        }
        Optional<Patient> optionalPatient = patientService.getPatient(patientId);
        if (optionalPatient.isPresent()) {
            loadPatient(optionalPatient.get());
        } else {
            String msg = "Patient with id " + patientId + " was not found.";
            Logger.getLogger(AbstractPatientTemplate.class.getName())
                    .info(msg);
            event.rerouteToError(IllegalArgumentException.class,msg);
        }
    }

    protected void loadPatient(Patient aPatient) {
        patient = aPatient;
        getModel().setPatient(aPatient);
    }

    protected Patient getPatient() {
        return patient;
    }
}
