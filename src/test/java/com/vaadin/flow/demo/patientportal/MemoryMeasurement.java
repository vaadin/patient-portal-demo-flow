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
package com.vaadin.flow.demo.patientportal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.server.VaadinSession;

public class MemoryMeasurement extends Div {

    private final Span uis = new Span();
    private final Span memory = new Span();

    public MemoryMeasurement() {
        UI.getCurrent().getSession().setAttribute("login", true);

        UI.getCurrent().setId(UUID.randomUUID().toString());
        setId("session-size");

        uis.setId("uis");
        memory.setId("memory");

        removeExtraUIs();

        NativeButton showMemory = new NativeButton("Show session memory",
                event -> showMemory());
        showMemory.setId("show-memory");
        NativeButton clearMemory = new NativeButton("Clear session memory info",
                event -> clearMemoryInfo());
        clearMemory.setId("clear-memory");
        add(showMemory, clearMemory, uis, memory);
    }

    private void showMemory() {
        removeExtraUIs();
        getUI().ifPresent(ui -> {
            uis.setText(String.valueOf(ui.getSession().getUIs().size()));
            uis.setVisible(true);
            memory.setText(String.valueOf(
                    MemoryAmountCalculator.getObjectSize(ui.getSession())));
            memory.setVisible(true);
        });
    }

    private void clearMemoryInfo() {
        uis.setText("");
        uis.setVisible(false);
        memory.setText("");
        memory.setVisible(false);
    }

    private void removeExtraUIs() {
        VaadinSession session = VaadinSession.getCurrent();
        List<UI> toRemove = new ArrayList<>();
        for (UI ui : session.getUIs()) {
            if (!ui.getId().isPresent()) {
                ui.close();
                toRemove.add(ui);
            }
        }
        toRemove.forEach(session::removeUI);
    }
}
