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

import javax.annotation.PostConstruct;

import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.vaadin.demo.service.DBInitService;

/**
 * Spring boot web appplication initializer.
 * <p>
 *
 * @author Vaadin Ltd
 */
@SpringBootApplication(exclude = { WebMvcAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class, },scanBasePackages = { "com.vaadin.flow.demo.patientportal", "com.vaadin.demo" })
@EnableJpaRepositories("com.vaadin.demo.repositories")
@EntityScan("com.vaadin.demo.entities")
public class PatientPortalInitializer extends WebSecurityConfigurerAdapter {

    @Autowired
    private DBInitService initService;

    public static void main(String[] args) {
        SpringApplication.run(PatientPortalInitializer.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/*").permitAll();
    }

    @PostConstruct
    private void init() {
        initService.initDatabase();
    }

    @Bean
    public TomcatConnectorCustomizer http2ProtocolCustomizer() {
        return (connector) -> {
            for (UpgradeProtocol upgradeProtocol: connector.findUpgradeProtocols()) {
                if (upgradeProtocol instanceof Http2Protocol) {
                    Http2Protocol http2Protocol = (Http2Protocol)upgradeProtocol;
                    http2Protocol.setOverheadContinuationThreshold(-1);
                    http2Protocol.setOverheadDataThreshold(-1);
                    http2Protocol.setOverheadWindowUpdateThreshold(-1);
                }
            }
        };
    }
}
