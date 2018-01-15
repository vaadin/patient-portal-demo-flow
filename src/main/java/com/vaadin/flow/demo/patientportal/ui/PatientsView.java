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

import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.patientportal.converters.DateToStringConverter;
import com.vaadin.flow.demo.patientportal.converters.LongToStringConverter;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.Convert;
import com.vaadin.flow.templatemodel.Include;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Vaadin Ltd
 */
@Tag("patients-view")
@HtmlImport("frontend://components/main/patients/patients-view.html")
@Route(value = "patients", layout = MainView.class)
//todo fix navigation NPE on back - PR submitted to GH
public class PatientsView
        extends PolymerTemplate<PatientsView.PatientsViewModel> implements RouterLayout, BeforeEnterObserver {

    @Autowired
    private PatientService patientService;

    @Id("patientsGrid")
    private Element grid;

    public PatientsView() {
        grid.addEventListener("click", event -> {
            String currentPatientId = getModel().getCurrentPatientId();
            if (currentPatientId != null && !currentPatientId.isEmpty()) {
                getUI().get().navigateTo("patient/" + currentPatientId);
                setId("patients-view");
            }
        });
    }

    public interface PatientsViewModel extends TemplateModel {

        @Include({"firstName", "lastName", "id", "medicalRecord",
                "doctor.firstName", "doctor.lastName", "lastVisit"})
        @Convert(value = DateToStringConverter.class, path = "lastVisit")
        @Convert(value = LongToStringConverter.class, path = "medicalRecord")
        @Convert(value = LongToStringConverter.class, path = "id")
        void setPatients(List<Patient> patients);

        List<Patient> getPatients();

        String getCurrentPatientId();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (UI.getCurrent().getSession().getAttribute("login") == null) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigateTo("");
            return;
        }

        if ((noPatientsInTheModel() || locationChangedToSameView(event)) && outOfSyncWithPatientService()) {
            getModel().setPatients(patientService.getPatients());
        }
    }

    private boolean locationChangedToSameView(BeforeEnterEvent locationChangeEvent) {
        return locationChangeEvent.getLocation().getSegments().size() == 1 && locationChangeEvent.getLocation().getFirstSegment().equals("patients");
    }

    private boolean outOfSyncWithPatientService() {
        return patientService.getPatientsCount() != getModel().getPatients().size();
    }

    private boolean noPatientsInTheModel() {
        return getModel().getPatients() == null || getModel().getPatients()
                .isEmpty();
    }
}
