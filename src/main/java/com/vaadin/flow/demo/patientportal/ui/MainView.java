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
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * @author Vaadin Ltd
 */
@SuppressWarnings("serial")
@UIScope
@Tag("main-view")
@JsModule("./components/main/main-view.ts")
public class MainView extends LitTemplate implements RouterLayout, BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (UI.getCurrent().getSession().getAttribute("login") == null
         && !(event.getLocation().getPath().endsWith("test"))) {
            event.forwardTo(LoginView.class);
            return;
        }
        getElement().setProperty("page", event.getLocation().getFirstSegment());
    }

    @ClientCallable
    private void logout() {
        UI.getCurrent().getSession().setAttribute("login", null);
        UI.getCurrent().close();
        UI.getCurrent().getPage().executeJs("window.location.href=''");
    }
}
