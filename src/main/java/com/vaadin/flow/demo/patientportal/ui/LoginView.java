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

package com.vaadin.flow.demo.patientportal.ui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("login-view")
@JsModule("./components/login-view.js")
@Route("")
public class LoginView extends LitTemplate implements BeforeEnterObserver {

    @ClientCallable
    private void login(String username, String password) {
        if ("user".equals(username) && "password".equals(password)) {
            UI ui = UI.getCurrent();
            ui.getSession().setAttribute("login", true);
            ui.navigate(PatientsView.class);
        } else {
            NativeLabel error = new NativeLabel("Faulty login credentials!");
            error.setClassName("alert error");
            error.getStyle().set("color", "red");
            error.getStyle().set("fontSize", "18px");
            getElement().appendChild(error.getElement());
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        VaadinSession session = beforeEnterEvent.getUI().getSession();
        Object loggedIn = session.getAttribute("login");
        if (loggedIn != null && Boolean.valueOf((boolean) loggedIn)) {
            beforeEnterEvent.rerouteTo(PatientsView.class);
        }
    }

}
