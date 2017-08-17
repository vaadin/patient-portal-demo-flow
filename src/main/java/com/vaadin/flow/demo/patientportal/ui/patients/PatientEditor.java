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

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.entities.Gender;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.demo.patientportal.dto.DoctorDTO;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.html.Span;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DatePicker;
import com.vaadin.ui.TextField;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("patient-editor")
@HtmlImport("/components/main/patients/patient-editor.html")
@Route("patients/{id}/edit")
@ParentView(PatientDetails.class)
public class PatientEditor extends
        AbstractPatientTemplate<AbstractPatientTemplate.PatientTemplateModel> {

    @Id("id")
    private Span idComponent;

    @Id("title")
    private ComboBox<String> titleComboBox;

    @Id("firstName")
    private TextField firstNameField;

    @Id("middleName")
    private TextField middleNameField;

    @Id("lastName")
    private TextField lastNameField;

    @Id("gender")
    private ComboBox<String> genderComboBox;

    @Id("birthDate")
    private DatePicker birthDatePicker;

    @Id("ssn")
    private TextField ssnField;

    @Id("doctor")
    private ComboBox<DoctorDTO> doctorComboBox;

    @Id("save")
    private Button saveButton;

    @Id("cancel")
    private Button cancelButton;

    @Id("delete")
    private Button deleteButton;

    @Autowired
    public PatientEditor(PatientService patientService) {
        titleComboBox.setItems("Miss", "Ms", "Mrs", "Mr");
        genderComboBox.setItems(Arrays.stream(Gender.values())
                .map(gender -> gender.name()).collect(Collectors.toList()));

        doctorComboBox.setItems(patientService.getAllDoctors().stream()
                .map(DoctorDTO::new).collect(Collectors.toList()));
        doctorComboBox.setItemLabelPath("fullName");

        saveButton.addClickListener(event -> savePatient());
        cancelButton.addClickListener(event -> close());
        deleteButton.addClickListener(event -> deletePatient());
    }

    public void savePatient() {
        patient.setTitle(titleComboBox.getValue());
        patient.setFirstName(firstNameField.getValue());
        patient.setMiddleName(middleNameField.getValue());
        patient.setLastName(lastNameField.getValue());
        patient.setGender(Gender.valueOf(genderComboBox.getValue()));
        patient.setBirthDate(java.sql.Date.valueOf(birthDatePicker.getValue()));
        patient.setSsn(ssnField.getValue());
        patient.setDoctor(patientService
                .getDoctor(doctorComboBox.getSelectedItem().getId()).get());
        patientService.savePatient(patient);
        close();
    }

    private void deletePatient() {
        patientService.deletePatient(patient);
        getUI().get().navigateTo("patients");
    }

    @EventHandler
    private void close() {
        getUI().get().navigateTo("patients/" + patient.getId());
    }

    public void fillPatientData(Patient patient) {
        idComponent.setText(patient.getId().toString());
        titleComboBox.setValue(patient.getTitle());
        firstNameField.setValue(patient.getFirstName());
        middleNameField.setValue(patient.getMiddleName());
        lastNameField.setValue(patient.getLastName());
        genderComboBox.setValue(patient.getGender().name());
        birthDatePicker
                .setValue(new java.sql.Date(patient.getBirthDate().getTime())
                        .toLocalDate());
        ssnField.setValue(patient.getSsn());
        doctorComboBox.setSelectedItem(new DoctorDTO(patient.getDoctor()));
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        getPatientFromURL(locationChangeEvent);
        fillPatientData(patient);
    }

}
