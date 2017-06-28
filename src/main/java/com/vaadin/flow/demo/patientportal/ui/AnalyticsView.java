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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.demo.service.AnalyticsService;
import com.vaadin.demo.service.StringLongPair;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;
import com.vaadin.hummingbird.ext.spring.annotations.Route;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("analytics-view")
@HtmlImport("/components/main/analytics/analytics.html")
@Route(value = "analytics/*")
public class AnalyticsView extends PolymerTemplate<AnalyticsView.AnalyticsModel>
        implements View {

    private static final String AGE_ROUTE = "age";
    private static final String DOCTOR_ROUTE = "doctor";
    private static final String GENDER_ROUTE = "gender";
    
    private final BiConsumer<List<Integer>, List<String>> dataConsumer = (data, categories) -> {
        getModel().setData(data);
        getModel().setCategories(categories);
    };

    @Autowired
    private AnalyticsService analyticsService;

    public static interface AnalyticsModel extends TemplateModel {

        public void setData(List<Integer> data);

        public void setCategories(List<String> categories);

        public void setRoute(String route);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        String path = locationChangeEvent.getPathWildcard();
        if (path.isEmpty() || path.equals(AGE_ROUTE)) {
            setStatsByAge();
            getModel().setRoute(AGE_ROUTE);
            return;
        } else if (path.equals(DOCTOR_ROUTE)) {
            setStatsByDoctor();
        } else if (path.equals(GENDER_ROUTE)) {
            setStatsByGender();
        }
        getModel().setRoute(path);
    }

    private void setStatsByAge() {
        List<Integer> data = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        analyticsService.getStatsByAgeGroup().stream().sorted(this::compare)
                .forEach(pair -> {
                    data.add(pair.getCount().intValue());
                    categories.add(pair.getGroup());
                });
        dataConsumer.accept(data, categories);
    }

    private void setStatsByDoctor() {
        List<Integer> data = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        analyticsService.getStatsByDoctor().entrySet().stream()
                .forEach(entry -> {
                    data.add(entry.getValue().intValue());
                    categories.add(entry.getKey().getLastName());
                });
        dataConsumer.accept(data, categories);
    }

    private void setStatsByGender() {
        List<Integer> data = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        analyticsService.getStatsByGender().stream().forEach(pair -> {
            data.add(pair.getCount().intValue());
            categories.add(pair.getGroup());
        });
        dataConsumer.accept(data, categories);
    }

    private int compare(StringLongPair pair1, StringLongPair pair2) {
        if (pair1.getGroup().charAt(0) == 'U') {
            return -1;
        } else if (pair2.getGroup().charAt(0) == 'U') {
            return 1;
        } else {
            return pair1.getGroup().compareTo(pair2.getGroup());
        }
    }
}
