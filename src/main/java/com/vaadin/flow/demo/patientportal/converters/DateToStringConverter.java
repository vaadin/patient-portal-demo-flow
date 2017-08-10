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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.flow.template.model.ModelConverter;

/**
 * Converts between Date-objects and their String-representations in format
 * 'yyyy/MM/dd'.
 * 
 * @author Vaadin Ltd
 *
 */

public class DateToStringConverter implements ModelConverter<Date, String> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd");

    @Override
    public String toPresentation(Date modelValue) {
        return modelValue == null ? null : dateFormat.format(modelValue);
    }

    @Override
    public Date toModel(String presentationValue) {
        try {
            return dateFormat.parse(presentationValue);
        } catch (ParseException e) {
            return null;
        }
    }

}
