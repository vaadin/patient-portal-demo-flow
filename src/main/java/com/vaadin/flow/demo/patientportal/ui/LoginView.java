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

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("login-view")
@JsModule("./components/login-view.js")
@CssImport("./shared-styles.css")
@Route("")
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel> implements BeforeEnterObserver {

    @Id("login-button")
    private Button loginButton;

    public LoginView() {

        getModel().setUsername("user");
        getModel().setPassword("password");

        loginButton.addClickListener(event -> login());
    }

    @EventHandler
    private void login() {
        if ("user".equals(getModel().getUsername())
                && "password".equals(getModel().getPassword())) {

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

    public interface LoginViewModel extends TemplateModel {

        String getUsername();

        void setUsername(String username);

        String getPassword();

        void setPassword(String password);
    }

}
