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
package com.vaadin.flow.demo.patientportal.converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.vaadin.flow.templatemodel.ModelEncoder;

/**
 * Encodes between Date-objects and their String-representations in format
 * 'yyyy/MM/dd'.
 *
 * @author Vaadin Ltd
 */
public class DateToStringEncoder implements ModelEncoder<Date, String> {

    public static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern(DATE_FORMAT);

    @Override
    public String encode(Date modelValue) {
        if (modelValue instanceof java.sql.Date) {
            return ((java.sql.Date) modelValue).toLocalDate().format(formatter);
        }
        return modelValue == null ? null
                : modelValue.toInstant().atZone(ZoneId.systemDefault())
                        .format(formatter);
    }

    @Override
    public Date decode(String presentationValue) {
        try {
            return Date.from(LocalDate.parse(presentationValue, formatter)
                    .atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException dateTimeParseException) {
            return null;
        }
    }

}
