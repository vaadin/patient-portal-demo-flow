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

import com.vaadin.demo.service.AnalyticsService;
import com.vaadin.demo.service.StringLongPair;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Vaadin Ltd
 *
 */
@Tag("analytics-view")
@HtmlImport("frontend://components/main/analytics/analytics.html")
@Route(value = "analytics/*", layout = MainView.class)
public class AnalyticsView extends PolymerTemplate<AnalyticsView.AnalyticsModel>
        implements HasUrlParameter<String> {

    private static final String AGE_ROUTE = "age";
    private static final String DOCTOR_ROUTE = "doctor";
    private static final String GENDER_ROUTE = "gender";

    @Autowired
    private AnalyticsService analyticsService;

    public interface AnalyticsModel extends TemplateModel {

        void setData(List<Integer> data);

        void setCategories(List<String> categories);

        void setRoute(String route);
    }

    @Override
    public void setParameter(BeforeEvent event,
            @WildcardParameter String path) {
        if (path.isEmpty() || path.equals(AGE_ROUTE)) {
            setChartData(this::getDataByAge);
            getModel().setRoute(AGE_ROUTE);
            return;
        } else if (path.equals(DOCTOR_ROUTE)) {
            setChartData(this::getDataByDoctor);
        } else if (path.equals(GENDER_ROUTE)) {
            setChartData(this::getDataByGender);
        }
        getModel().setRoute(path);
    }

    private List<StringLongPair> getDataByAge() {
        return analyticsService.getStatsByAgeGroup().stream()
                .sorted(this::compare).collect(Collectors.toList());
    }

    private List<StringLongPair> getDataByDoctor() {
        return analyticsService.getStatsByDoctor().entrySet().stream()
                .map(entry -> new StringLongPair(
                        "Dr. " + entry.getKey().getLastName(),
                        entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<StringLongPair> getDataByGender() {
        return new ArrayList<>(analyticsService.getStatsByGender());
    }

    private void setChartData(Supplier<List<StringLongPair>> dataSupplier) {
        List<Integer> data = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        dataSupplier.get().forEach(pair -> {
            data.add(pair.getCount().intValue());
            categories.add(pair.getGroup());
        });
        getModel().setData(data);
        getModel().setCategories(categories);
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
