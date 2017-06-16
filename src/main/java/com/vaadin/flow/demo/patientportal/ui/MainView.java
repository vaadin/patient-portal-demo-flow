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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.flow.html.Div;
import com.vaadin.flow.router.HasChildView;
import com.vaadin.flow.router.View;
import com.vaadin.hummingbird.ext.spring.annotations.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;

/**
 * @author Vaadin Ltd
 *
 */
@SuppressWarnings("serial")
@UIScope
public class MainView extends Composite<Div> implements HasChildView {

    @Autowired
    private ApplicationContext context;

    @Override
    public void setChildView(View childView) {
        getContent().removeAll();
        getContent().add((Component) childView);

        context.getBean(MainView.class);
    }

}
