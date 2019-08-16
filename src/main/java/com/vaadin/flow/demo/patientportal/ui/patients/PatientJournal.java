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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.entities.Doctor;
import com.vaadin.demo.entities.JournalEntry;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-journal")
@JsModule("./components/main/patients/patient-journal.js")
@Route(value = "journal", layout = PatientDetails.class)
public class PatientJournal extends
        AbstractPatientTemplate<AbstractPatientTemplate.PatientTemplateModel> {

    @Autowired
    private transient PatientService patientService;

//    @Id("journalGrid") todo restore when details generator is fixed
    private Grid<JournalEntry> grid;

    public PatientJournal() {
        grid = new Grid<>();
        ValueProvider<JournalEntry, LocalDate> dateValueProvider = journalEntry -> Optional
                .ofNullable(journalEntry.getDate())
                .map(localDate -> LocalDateTime.ofInstant(localDate.toInstant(),
                        ZoneId.systemDefault()).toLocalDate())
                .orElse(null);
        grid.addColumn(new LocalDateRenderer<>(dateValueProvider))
            .setComparator(dateValueProvider)
            .setHeader("Date");
        grid.addColumn(JournalEntry::getAppointmentType)
            .setComparator(JournalEntry::getAppointmentType)
            .setHeader("Appointment");
        ValueProvider<JournalEntry, String> doctorNameProvider = entry -> Optional.ofNullable(entry.getDoctor())
                                                                                               .map((Doctor d) -> d.getLastName() + ", " + d.getFirstName())
                                                                                               .orElse("");
        grid.addColumn(doctorNameProvider).setHeader("Doctor").setComparator(doctorNameProvider);
        grid.addColumn(JournalEntry::getEntry).setComparator(JournalEntry::getEntry).setHeader("Notes");
        grid.setItemDetailsRenderer(TemplateRenderer.<JournalEntry>of(
                "<section class=\"details\"><h3>Notes</h3><article>[[item.entry]]</article></section>")
                                                    .withProperty("entry",JournalEntry::getEntry));
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        getElement().appendChild(grid.getElement());
    }

    @Override
    protected void loadPatient(Patient aPatient) {
        Patient patient = patientService.findAttached(aPatient);
        super.loadPatient(patient);
        grid.setItems(patient.getJournalEntries());
    }

}
