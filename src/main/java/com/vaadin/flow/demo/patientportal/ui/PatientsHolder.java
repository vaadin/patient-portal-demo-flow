package com.vaadin.flow.demo.patientportal.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vaadin.demo.entities.Patient;

/**
 * Created by Mikael on 01/09/2017.
 */
public class PatientsHolder {

    private final Map<Long, Patient> patients = new HashMap<>();

    private static final PatientsHolder INSTANCE = new PatientsHolder();

    public static PatientsHolder getInstance() {
        return INSTANCE;
    }

    public Optional<Patient> getPatient(Long id) {
        return Optional.ofNullable(patients.get(id));
    }

    public void setPatients(List<Patient> patientList) {
        patients.clear();
        patientList.forEach(patient -> patients.put(patient.getId(), patient));
    }
}
