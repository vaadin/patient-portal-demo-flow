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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.renderer.LocalDateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * @author Vaadin Ltd
 */
@Tag("patients-view")
@HtmlImport("frontend://components/main/patients/patients-view.html")
@Route(value = "patients", layout = MainView.class)
// todo fix navigation NPE on back - PR submitted to GH
public class PatientsView
        extends PolymerTemplate<PatientsView.PatientsViewModel>
        implements RouterLayout, BeforeEnterObserver {

    @Autowired
    private PatientService patientService;

    @Id("patientsGrid")
    private Grid<Patient> grid;

    public PatientsView() {
        grid.addSelectionListener(event -> {
            Optional<Patient> patient = event.getFirstSelectedItem();
            if (patient.isPresent()) {
                getUI().get().navigateTo("patients/" + patient.get().getId());
                setId("patients-view");
            }
        });
        setupGridColumns();
    }

    private void setupGridColumns() {
        grid.addColumn(patient -> patient.getLastName() +", " + patient.getFirstName())
                .setHeader("Name")
                .setSortable(true)
                .addClassName("strong");

        grid.addColumn(Patient::getId)
                .setHeader("Id")
                .setSortable(true)
                .setWidth("40px")
                .setFlexGrow(0);
        grid.addColumn(Patient::getMedicalRecord)
                .setSortable(true)
                .setHeader("Medical Record");

        grid.addColumn(patient->
                patient.getDoctor().getLastName() +", " + patient.getDoctor().getFirstName())
                .setSortable(true)
                .setHeader("Doctor");
//todo flex grow
        grid.addColumn(
                new LocalDateRenderer<>(patient -> {
                    Date lastVisit = patient.getLastVisit();
                    if(lastVisit!=null)
                    {
                        return LocalDateTime.ofInstant(lastVisit.toInstant(), ZoneId.systemDefault()).toLocalDate();
                    } else {
                        return null;
                    }
                }))
                .setSortable(true)
                .setHeader("Last Visit");

    }

    public interface PatientsViewModel extends TemplateModel {

        String getCurrentPatientId();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        //todo improve the app security
        if (UI.getCurrent().getSession().getAttribute("login") == null) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigateTo("");
        }
        grid.setItems(patientService.getPatients());

    }

}
