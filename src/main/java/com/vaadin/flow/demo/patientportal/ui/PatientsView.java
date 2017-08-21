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

import com.vaadin.annotations.Convert;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.Include;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.demo.patientportal.converters.DateToStringConverter;
import com.vaadin.flow.demo.patientportal.converters.LongToStringConverter;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.template.model.TemplateModel;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;
import com.vaadin.ui.AttachEvent;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patients-view")
@HtmlImport("frontend://components/main/patients/patients-view.html")
@Route("patients")
@ParentView(MainView.class)
public class PatientsView
extends ParentPolymerTemplate<PatientsView.PatientsViewModel> {

    @Autowired
    private PatientService patientService;

    @Id("patientsGrid")
    private Element grid;

    public PatientsView() {

        // TODO: Remove this when proper patient-navigation can be implemented.
        grid.addEventListener("click",
                event -> getUI().get().navigateTo("patients/1"));
        setId("patients-view");
    }

    public interface PatientsViewModel extends TemplateModel {

        @Include({ "firstName", "lastName", "id", "medicalRecord",
            "doctor.firstName", "doctor.lastName", "lastVisit" })
        @Convert(value = DateToStringConverter.class, path = "lastVisit")
        @Convert(value = LongToStringConverter.class, path = "medicalRecord")
        @Convert(value = LongToStringConverter.class, path = "id")
        void setPatients(List<Patient> patients);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        getModel().setPatients(patientService.getPatients());
    }

}
