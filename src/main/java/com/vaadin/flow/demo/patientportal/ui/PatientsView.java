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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.demo.patientportal.converters.DateToStringEncoder;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.demo.patientportal.ui.patients.PatientProfile;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * @author Vaadin Ltd
 */
@Tag("patients-view")
@JsModule("./components/main/patients/patients-view.js")
@Route(value = "patients", layout = MainView.class)
@ParentLayout(MainView.class)
// todo fix navigation NPE on back - PR submitted to GH
public class PatientsView extends PolymerTemplate<TemplateModel>
        implements RouterLayout, BeforeEnterObserver {

    @Id("patientsGrid")
    private Grid<Patient> grid;

    public PatientsView(@Autowired PatientService patientService) {
        grid.addSelectionListener(event -> {
            Optional<Patient> patient = event.getFirstSelectedItem();
            if (patient.isPresent()) {
                getUI().get().navigate(PatientProfile.class, patient.get().getId());
                setId("patients-view");
            }
        });
        setupGridColumns();

        grid.setDataProvider(DataProvider.fromCallbacks(
                query -> patientService
                        .getPatients(query.getOffset() / query.getLimit(),
                                query.getLimit())
                        .stream(),
                query -> (int) patientService.getPatientsCount()));
    }

    private void setupGridColumns() {
        ValueProvider<Patient, String> fullNameProvider = patient -> patient
                .getLastName() + ", " + patient.getFirstName();
        grid.addColumn(TemplateRenderer
                .<Patient> of("<strong>[[item.fullName]]</strong>")
                .withProperty("fullName", fullNameProvider)).setHeader("Name")
                .setSortable(true).setFlexGrow(1);

        grid.addColumn(Patient::getId).setHeader("Id")
                .setComparator(Patient::getId).setWidth("40px").setFlexGrow(0);
        grid.addColumn(Patient::getMedicalRecord)
                .setComparator(Patient::getMedicalRecord)
                .setHeader("Medical Record");

        ValueProvider<Patient, String> doctorNameProvider = patient -> patient
                .getDoctor().getLastName() + ", "
                + patient.getDoctor().getFirstName();
        grid.addColumn(doctorNameProvider).setComparator(doctorNameProvider)
                .setFlexGrow(1).setHeader("Doctor");
        grid.addColumn(new LocalDateRenderer<>(PatientsView::findLastVisit, DateToStringEncoder.DATE_FORMAT))
                .setComparator(entry -> {
                    LocalDate lastVisit = findLastVisit(entry);
                    return lastVisit == null ? LocalDate.MIN : lastVisit;
                }).setFlexGrow(1).setHeader("Last Visit");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // todo improve the app security
        if (UI.getCurrent().getSession().getAttribute("login") == null) {
            UI.getCurrent().navigate(LoginView.class);
        }
    }

    public void reload() {
        grid.getDataProvider().refreshAll();
    }

    private static LocalDate findLastVisit(Patient patient) {
        Date lastVisit = patient.getLastVisit();
        if (lastVisit != null) {
            return LocalDateTime
                    .ofInstant(lastVisit.toInstant(), ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            return null;
        }
    }
}
