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
import com.vaadin.flow.html.Label;
import com.vaadin.flow.html.NativeButton;
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
@HtmlImport("frontend://components/login-view.html")
@Route()
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel>
        implements View {

    @Id("login-button")
    private NativeButton loginButton;

    public LoginView() {

        getModel().setUsername("user");
        getModel().setPassword("password");

        loginButton.addClickListener(event -> login());
    }

    @EventHandler
    private void login() {
        if("user".equals(getModel().getUsername()) && "password".equals(getModel().getPassword())) {
            UI.getCurrent().getSession().setAttribute("login", true);
            getUI().get().navigateTo("patients");
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
