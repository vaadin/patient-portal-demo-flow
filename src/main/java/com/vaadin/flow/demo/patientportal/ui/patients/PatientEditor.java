/*
 * Copyright 2000-2023 Vaadin Ltd.
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
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.entities.Doctor;
import com.vaadin.demo.entities.Gender;
import com.vaadin.demo.entities.Patient;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.demo.patientportal.dto.DoctorDTO;
import com.vaadin.flow.demo.patientportal.service.PatientService;
import com.vaadin.flow.demo.patientportal.ui.PatientsView;
import com.vaadin.flow.router.Route;

/**
 * @author Vaadin Ltd
 */
@Tag("patient-editor")
@JsModule("./components/main/patients/patient-editor.js")
@Route(value = "edit", layout = PatientDetails.class)
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

    private transient PatientService patientService;

    @Autowired
    public PatientEditor(PatientService patientService) {
        this.patientService = patientService;

        titleComboBox.setItems("Miss", "Ms", "Mrs", "Mr", "Doc");
        genderComboBox.setItems(Arrays.stream(Gender.values()).map(Enum::name)
                .collect(Collectors.toList()));

        doctorComboBox.setItems(patientService.getAllDoctors().stream()
                .map(DoctorDTO::new).collect(Collectors.toList()));
        doctorComboBox.setItemLabelGenerator(DoctorDTO::getFullName);
        doctorComboBox.setAllowCustomValue(false);

        saveButton.addClickListener(event -> savePatient());
        cancelButton.addClickListener(event -> close());
        deleteButton.addClickListener(event -> deletePatient());
        birthDatePicker.setLocale(Locale.ENGLISH);
    }

    public void savePatient() {
        Patient patient = getPatient();
        patient.setTitle(titleComboBox.getValue());
        patient.setFirstName(firstNameField.getValue());
        patient.setMiddleName(middleNameField.getValue());
        patient.setLastName(lastNameField.getValue());
        patient.setGender(Gender.valueOf(genderComboBox.getValue()));
        patient.setBirthDate(java.sql.Date.valueOf(birthDatePicker.getValue()));
        patient.setSsn(ssnField.getValue());

        DoctorDTO doctorDTO = doctorComboBox.getValue();
        Optional<Doctor> doctor;
        if (doctorDTO == null) {
            doctor = Optional.empty();
        } else {
            doctor = patientService.getDoctor(doctorDTO.getId());
        }
        patient.setDoctor(doctor.orElse(null));
        patientService.savePatient(patient);
        close();
    }

    private void deletePatient() {
        patientService.deletePatient(getPatient());
        Optional<Component> parent = getParent();
        while (parent.isPresent()) {
            if (parent.get() instanceof PatientsView) {
                ((PatientsView) parent.get()).reload();
                break;
            } else {
                parent = parent.get().getParent();
            }
        }
        getUI().ifPresent(ui -> ui.navigate(PatientsView.class));
    }

    @EventHandler
    private void close() {
        getUI().ifPresent(
                ui -> ui.navigate(PatientProfile.class, getPatient().getId()));
    }

    @Override
    protected void loadPatient(Patient aPatient) {
        super.loadPatient(aPatient);
        idComponent.setText(aPatient.getId().toString());
        titleComboBox.setValue(aPatient.getTitle());
        firstNameField.setValue(aPatient.getFirstName());
        middleNameField.setValue(aPatient.getMiddleName());
        lastNameField.setValue(aPatient.getLastName());
        genderComboBox.setValue(aPatient.getGender().name());
        birthDatePicker
                .setValue(new java.sql.Date(aPatient.getBirthDate().getTime())
                        .toLocalDate());
        ssnField.setValue(aPatient.getSsn());
        Doctor doctor = aPatient.getDoctor();
        DoctorDTO doctorDTO = doctor == null ? null : new DoctorDTO(doctor);
        doctorComboBox.setValue(doctorDTO);
    }
}
