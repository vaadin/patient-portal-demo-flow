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
package com.vaadin.flow.demo.patientportal;

import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import org.burningwave.core.assembler.StaticComponentContainer;
import org.burningwave.core.classes.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vaadin.demo.service.DBInitService;

import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Spring boot web application initializer.
 * <p>
 *
 * @author Vaadin Ltd
 */
@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,}, scanBasePackages = {"com.vaadin.flow.demo", "com.vaadin.demo"})
@EntityScan("com.vaadin.demo.entities")
@EnableJpaRepositories("com.vaadin.demo.repositories")
public class PatientPortalInitializer {

    @Autowired
    private DBInitService initService;

    public static void main(String[] args) {
        // Opens all modules to each other to allow memory calculator to use
        // reflection to compute objects size
        // This is a workaround to avoid a huge number of '--add-opens' flags
        // to JVM arguments list
        Modules.create().exportAllToAll();
        SpringApplication.run(PatientPortalInitializer.class, args);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
        return http.build();
    }

    @PostConstruct
    private void init() {
        initService.initDatabase();
    }

}
