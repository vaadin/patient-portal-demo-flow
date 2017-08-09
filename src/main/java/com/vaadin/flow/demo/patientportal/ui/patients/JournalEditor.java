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

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.entities.AppointmentType;
import com.vaadin.demo.entities.JournalEntry;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.hummingbird.ext.spring.annotations.ParentView;
import com.vaadin.hummingbird.ext.spring.annotations.Route;

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

    @EventHandler
    private void save() {
        // TODO: get real data from the template, this is just to test that new
        // entries are saved to the database.
        JournalEntry e = new JournalEntry(new Date(), "test entry",
                AppointmentType.FOLLOW_UP);
        journalEntries.add(e);
        patientService.savePatient(getPatient());
        getUI().get()
                .navigateTo("patients/" + getPatient().getId() + "/journal");
    }

    @Override
    @Transactional
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        super.onLocationChange(locationChangeEvent);
        this.journalEntries = getPatient().getJournalEntries();
        journalEntries.size(); // to initialize the list
    }
}
