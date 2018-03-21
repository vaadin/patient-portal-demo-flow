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

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("login-view")
@HtmlImport("frontend://components/login-view.html")
@Route("")
@Theme(Lumo.class)
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel> {

    @Id("login-button")
    private NativeButton loginButton;

    public LoginView() {

        getModel().setUsername("user");
        getModel().setPassword("password");

        loginButton.addClickListener(event -> login());
    }

    @EventHandler
    private void login() {
        if ("user".equals(getModel().getUsername())
                && "password".equals(getModel().getPassword())) {
            UI.getCurrent().getSession().setAttribute("login", true);
            getUI().get().navigate("patients");
        } else {
            Label error = new Label("Faulty login credentials!");
            error.setClassName("alert error");
            error.getStyle().set("color", "red");
            error.getStyle().set("fontSize", "18px");
            getElement().appendChild(error.getElement());
        }
    }

    public interface LoginViewModel extends TemplateModel {

        String getUsername();

        void setUsername(String username);

        String getPassword();

        void setPassword(String password);
    }

}
