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
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.demo.service.AnalyticsService;
import com.vaadin.demo.service.StringLongPair;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.WildcardParameter;

/**
 * @author Vaadin Ltd
 */
@Tag("analytics-view")
@Route(value = "analytics", layout = MainView.class)
public class AnalyticsView extends Div implements HasUrlParameter<String> {

    private static final String AGE_ROUTE = "age";
    private static final String DOCTOR_ROUTE = "doctor";
    private static final String GENDER_ROUTE = "gender";

    @Autowired
    private AnalyticsService analyticsService;

    private Chart chart = new Chart();

    public AnalyticsView() {
        RouterLink age = new RouterLink("Age", AnalyticsView.class, AGE_ROUTE);
        RouterLink doctor = new RouterLink("Doctor", AnalyticsView.class,
                DOCTOR_ROUTE);
        RouterLink gender = new RouterLink("Gender", AnalyticsView.class,
                GENDER_ROUTE);

        HorizontalLayout navigation = new HorizontalLayout();
        navigation.add(age, doctor, gender);

        add(navigation, chart);
    }

    @Override
    public void setParameter(BeforeEvent event,
            @WildcardParameter
                    String path) {
        if (path.isEmpty() || path.equals(AGE_ROUTE)) {
            setChartData(this::getDataByAge);
        } else if (path.equals(DOCTOR_ROUTE)) {
            setChartData(this::getDataByDoctor);
        } else if (path.equals(GENDER_ROUTE)) {
            setChartData(this::getDataByGender);
        }
    }

    private List<StringLongPair> getDataByAge() {
        return analyticsService.getStatsByAgeGroup().stream()
                .sorted(this::compare).collect(Collectors.toList());
    }

    private List<StringLongPair> getDataByDoctor() {
        return analyticsService.getStatsByDoctor().entrySet().stream()
                .map(entry -> new StringLongPair(
                        "Dr. " + entry.getKey().getLastName(),
                        entry.getValue())).collect(Collectors.toList());
    }

    private List<StringLongPair> getDataByGender() {
        return new ArrayList<>(analyticsService.getStatsByGender());
    }

    private void setChartData(Supplier<List<StringLongPair>> dataSupplier) {
        remove(chart);
        chart = new Chart();
        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.COLUMN);

        ListSeries data = new ListSeries();
        List<String> categories = new ArrayList<>();
        dataSupplier.get().forEach(pair -> {
            data.addData(pair.getCount().intValue());
            categories.add(pair.getGroup());
        });
        XAxis xAxis = configuration.getxAxis(0);
        if (xAxis == null) {
            xAxis = configuration.getxAxis();
        }
        xAxis.setCategories(categories.toArray(new String[categories.size()]));
        configuration.addSeries(data);

        add(chart);
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
