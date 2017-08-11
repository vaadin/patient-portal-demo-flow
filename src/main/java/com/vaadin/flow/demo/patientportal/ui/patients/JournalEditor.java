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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.entities.AppointmentType;
import com.vaadin.demo.entities.JournalEntry;
import com.vaadin.flow.demo.patientportal.dto.DoctorDTO;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DatePicker;
import com.vaadin.ui.TextField;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("journal-editor")
@HtmlImport("/components/main/patients/journal-editor.html")
@Route("patients/{id}/new-entry")
@ParentView(PatientDetails.class)
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

    @Autowired
    public JournalEditor(PatientService patientService) {
        datePicker.setValue(LocalDate.now());

        appointmentTypeComboBox.setItems(Arrays.stream(AppointmentType.values())
                .map(type -> type.name()).collect(Collectors.toList()));

        doctorComboBox.setItems(patientService.getAllDoctors().stream()
                .map(doc -> new DoctorDTO(doc)).collect(Collectors.toList()));
        doctorComboBox.setItemLabelPath("fullName");
    }

    @EventHandler
    private void save() {
        Date date = java.sql.Date.valueOf(datePicker.getValue());
        AppointmentType appointmentType = AppointmentType
                .valueOf(appointmentTypeComboBox.getValue());
        String entry = entryField.getValue();

        JournalEntry journalEntry = new JournalEntry(date, entry,
                appointmentType);

        Long docId = doctorComboBox.getSelectedItem().getId();
        journalEntry.setDoctor(patientService.getDoctor(docId).get());

        journalEntries.add(journalEntry);
        patientService.savePatient(getPatient());

        getUI().get()
                .navigateTo("patients/" + getPatient().getId() + "/journal");
    }

    @Override
    @Transactional
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        super.onLocationChange(locationChangeEvent);
        journalEntries = getPatient().getJournalEntries();
        journalEntries.size(); // to initialize the list
    }
}
