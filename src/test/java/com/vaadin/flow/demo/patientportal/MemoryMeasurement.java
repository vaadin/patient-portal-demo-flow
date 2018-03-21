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
package com.vaadin.flow.demo.patientportal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.server.VaadinSession;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class MemoryMeasurement extends Div {

    public MemoryMeasurement() {
        UI.getCurrent().getSession().setAttribute("login", "foo");

        UI.getCurrent().setId(UUID.randomUUID().toString());
        setId("session-size");

        removeExtraUIs();

        NativeButton button = new NativeButton("Show session memory",
                event -> showMemory());
        button.setId("show-memory");
        add(button);
    }

    private void showMemory() {
        removeExtraUIs();
        Label uis = new Label(
                String.valueOf(getUI().get().getSession().getUIs().size()));
        uis.setId("uis");
        Label memory = new Label(String.valueOf(ObjectSizeCalculator
                .getObjectSize(getUI().get().getSession())));
        memory.setId("memory");
        add(uis, memory);
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
