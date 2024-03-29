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
package com.vaadin.flow.demo.patientportal.converters;

import com.vaadin.demo.entities.AppointmentType;
import com.vaadin.flow.templatemodel.ModelEncoder;

/**
 * @author Vaadin Ltd
 *
 */
public class AppointmentTypeToStringEncoder
        implements ModelEncoder<AppointmentType, String> {

    @Override
    public String encode(AppointmentType modelValue) {
        return modelValue.name();
    }

    @Override
    public AppointmentType decode(String presentationValue) {
        return AppointmentType.valueOf(presentationValue);
    }

}
