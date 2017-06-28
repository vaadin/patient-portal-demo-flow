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

package com.vaadin.flow.demo.patientportal.ui;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.html.Button;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;
import com.vaadin.hummingbird.ext.spring.annotations.Route;
import com.vaadin.ui.UI;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("login-view")
@HtmlImport("/components/login-view.html")
@Route("")
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel>
        implements View {

    @Id("login-button")
    private Button loginButton;

    public LoginView() {

        getModel().setUsername("user");
        getModel().setPassword("password");

        // Login using REST-API in the client-side until backend implemented.
        // For some reason this doesn't work inside clicklistener so temporarily
        // just login immediately.
        UI.getCurrent().getPage().executeJavaScript(
                "PatientPortal.http.login({username: $0, password: $1 });",
                getModel().getUsername(), getModel().getPassword());

        loginButton.addClickListener(event -> {
            login();
        });
    }

    @EventHandler
    private void login() {
        loginButton.getUI().ifPresent(ui -> ui.navigateTo("patients"));
    }

    public interface LoginViewModel extends TemplateModel {

        String getUsername();

        void setUsername(String username);

        String getPassword();

        void setPassword(String password);
    }

}
