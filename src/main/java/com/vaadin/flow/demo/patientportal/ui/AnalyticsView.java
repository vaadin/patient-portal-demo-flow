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
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.demo.service.AnalyticsService;
import com.vaadin.demo.service.StringLongPair;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
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

    private Chart chart = new Chart(ChartType.COLUMN);

    public AnalyticsView() {
        RouterLink age = new RouterLink("Age", AnalyticsView.class, AGE_ROUTE);
        RouterLink doctor = new RouterLink("Doctor", AnalyticsView.class,
                DOCTOR_ROUTE);
        RouterLink gender = new RouterLink("Gender", AnalyticsView.class,
                GENDER_ROUTE);

        HorizontalLayout navigation = new HorizontalLayout();
        navigation.add(age, doctor, gender);

        Configuration configuration = chart.getConfiguration();
        configuration.getLegend().setEnabled(false);
        configuration.setExporting(false);
        configuration.getyAxis().setTitle("Patients");
        add(navigation, chart);
    }

    @Override
    public void setParameter(BeforeEvent event,
            @WildcardParameter String path) {
        switch (path) {
        case DOCTOR_ROUTE:
            setChartData(getDataByDoctor());
            break;
        case GENDER_ROUTE:
            setChartData(getDataByGender());
            break;
        default:
            setChartData(getDataByAge());
        }
    }

    private Stream<StringLongPair> getDataByAge() {
        return analyticsService.getStatsByAgeGroup().stream()
                .sorted(this::compare);
    }

    private Stream<StringLongPair> getDataByDoctor() {
        return analyticsService.getStatsByDoctor().entrySet().stream()
                .map(entry -> new StringLongPair(
                        "Dr. " + entry.getKey().getLastName(),
                        entry.getValue()));
    }

    private Stream<StringLongPair> getDataByGender() {
        return analyticsService.getStatsByGender().stream();
    }

    private void setChartData(Stream<StringLongPair> pairs) {
        Configuration configuration = chart.getConfiguration();
        ListSeries data = new ListSeries();
        List<String> categories = new ArrayList<>();
        pairs.forEach(pair -> {
            data.addData(pair.getCount().intValue());
            categories.add(pair.getGroup());
        });
        XAxis xAxis = configuration.getxAxis();
        xAxis.setCategories(categories.toArray(new String[categories.size()]));
        configuration.setSeries(data);

        chart.drawChart();
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
