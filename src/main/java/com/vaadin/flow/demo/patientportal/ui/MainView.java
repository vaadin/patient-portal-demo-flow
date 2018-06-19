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
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * @author Vaadin Ltd
 */
@SuppressWarnings("serial")
@UIScope
@Tag("main-view")
@HtmlImport("components/main/main-view.html")
public class MainView extends PolymerTemplate<MainView.MainViewModel>
        implements RouterLayout, BeforeEnterObserver {
    public interface MainViewModel extends TemplateModel {
        void setPage(String page);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (UI.getCurrent().getSession().getAttribute("login") == null) {
            event.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("");
            return;
        }
        getModel().setPage(event.getLocation().getFirstSegment());
    }

    @EventHandler
    private void logout() {
        UI.getCurrent().getSession().setAttribute("login", null);
        UI.getCurrent().close();
        UI.getCurrent().getPage().executeJavaScript("window.location.href=''");
    }
}
