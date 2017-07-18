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

import com.vaadin.flow.template.model.ModelConverter;

/**
 * @author Vaadin Ltd
 *
 */

public class LongToStringConverter implements ModelConverter<Long, String> {

    @Override
    public Class<Long> getApplicationType() {
        return Long.class;
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public String toModel(Long applicationValue) {
        if (applicationValue == null)
            return "";
        return applicationValue.toString();
    }

    @Override
    public Long toApplication(String modelValue) {
        return Long.parseLong(modelValue);
    }

}