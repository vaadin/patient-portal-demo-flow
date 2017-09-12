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
package com.vaadin.flow.demo.patientportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

/**
 * Spring boot web appplication initializer.
 * <p>
 * The initializer registers the {@link PatientServlet} Vaadin servlet.
 *
 * @author Vaadin Ltd
 */
@SpringBootApplication
/*
 * To disable we security:
 *
 * @SpringBootApplication(exclude = {
 * org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.
 * class })
 */
public class PatientPortalInitializer extends SpringBootServletInitializer
        implements WebApplicationInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PatientPortalInitializer.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new PatientServlet(), "/*");
    }
}
