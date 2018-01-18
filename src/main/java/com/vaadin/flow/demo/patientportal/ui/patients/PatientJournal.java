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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import com.vaadin.demo.entities.Doctor;
import com.vaadin.demo.entities.JournalEntry;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.renderer.LocalDateRenderer;
import com.vaadin.flow.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-journal")
@HtmlImport("frontend://components/main/patients/patient-journal.html")
@Route(value = "journal", layout = PatientDetails.class)
public class PatientJournal extends
        AbstractPatientTemplate<AbstractPatientTemplate.PatientTemplateModel> {

    @Autowired
    private transient PatientService patientService;

    @Id("journalGrid")
    private Grid<JournalEntry> grid;

    public PatientJournal() {
        grid.addColumn(new LocalDateRenderer<>(journalEntry -> Optional
                .ofNullable(journalEntry.getDate())
                .map(localDate -> LocalDateTime.ofInstant(localDate.toInstant(),
                        ZoneId.systemDefault()).toLocalDate())
                .orElse(null))).setSortable(true).setHeader("Date");
        grid.addColumn(JournalEntry::getAppointmentType).setSortable(true).setHeader("Appointment");
        grid.addColumn(entry -> Optional.ofNullable(entry.getDoctor())
                .map((Doctor d) -> d.getLastName() + ", " + d.getFirstName())
                .orElse("")).setSortable(true).setHeader("Doctor");
        grid.addColumn(JournalEntry::getEntry).setSortable(true).setHeader("Notes");
        grid.setItemDetailsRenderer(TemplateRenderer.of(
                "<section class=\"details\"><h3>Notes</h3><article>[[entry]]</article></section>"));
    }

    @Override
    protected void loadPatient(Patient aPatient) {
        Patient patient = patientService.findAttached(aPatient);
        super.loadPatient(patient);
        grid.setItems(patient.getJournalEntries());
    }

}
