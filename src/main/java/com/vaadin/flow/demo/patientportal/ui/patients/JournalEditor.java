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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.entities.AppointmentType;
import com.vaadin.demo.entities.JournalEntry;
import com.vaadin.flow.demo.patientportal.dto.DoctorDTO;
import com.vaadin.flow.demo.patientportal.service.PatientService;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("journal-editor")
@HtmlImport("frontend://components/main/patients/journal-editor.html")
@Route(value = "new-entry", layout = PatientDetails.class)
public class JournalEditor extends
        AbstractPatientTemplate<AbstractPatientTemplate.PatientTemplateModel> {

    private List<JournalEntry> journalEntries;

    @Id("date")
    private DatePicker datePicker;

    @Id("appointment")
    private ComboBox<String> appointmentTypeComboBox;

    @Id("doctor")
    private ComboBox<DoctorDTO> doctorComboBox;

    @Id("entry")
    private TextField entryField;

    private transient PatientService patientService;

    @Autowired
    public JournalEditor(PatientService patientService) {
        this.patientService = patientService;

        datePicker.setValue(LocalDate.now());

        appointmentTypeComboBox.setItems(Arrays.stream(AppointmentType.values())
                .map(AppointmentType::name).collect(Collectors.toList()));

        doctorComboBox.setItems(patientService.getAllDoctors().stream()
                .map(DoctorDTO::new).collect(Collectors.toList()));
        doctorComboBox.setItemLabelGenerator(DoctorDTO::getFullName);
    }

    @EventHandler
    private void save() {
        Date date = java.sql.Date.valueOf(datePicker.getValue());
        AppointmentType appointmentType = AppointmentType
                .valueOf(appointmentTypeComboBox.getValue());
        String entry = entryField.getValue();

        JournalEntry journalEntry = new JournalEntry(date, entry,
                appointmentType);

        Long docId = doctorComboBox.getValue().getId();
        journalEntry.setDoctor(patientService.getDoctor(docId).get());

        journalEntries.add(journalEntry);
        patientService.savePatient(getPatient());

        close();
    }

    @EventHandler
    private void close() {
        getUI().get()
                .navigateTo("patient/journal/" + getPatient().getId());
    }

    @Override
    protected void loadPatient(Patient aPatient) {
        Patient patient = patientService.findAttached(aPatient);
        super.loadPatient(patient);
        journalEntries = getPatient().getJournalEntries();
        journalEntries.size(); // to initialize the list
    }
}
