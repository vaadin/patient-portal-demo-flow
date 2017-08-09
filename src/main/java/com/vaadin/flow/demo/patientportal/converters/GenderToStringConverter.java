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

import com.vaadin.demo.entities.Gender;
import com.vaadin.flow.template.model.ModelConverter;

/**
 * @author Vaadin Ltd
 *
 */
public class GenderToStringConverter implements ModelConverter<Gender, String> {

    @Override
    public String toPresentation(Gender modelValue) {
        return modelValue.name().toLowerCase();
    }

    @Override
    public Gender toModel(String presentationValue) {
        return Gender.valueOf(presentationValue.toUpperCase());
    }

}
