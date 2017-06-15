package com.vaadin.flow.demo.patientportal;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.flow.demo.patientportal.Servlet.MyRouterConfigurator;
import com.vaadin.flow.router.RouterConfiguration;
import com.vaadin.flow.router.RouterConfigurator;
import com.vaadin.server.VaadinServlet;

/**
 * The main servlet for the application.
 */
@WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
@VaadinServletConfiguration(routerConfigurator = MyRouterConfigurator.class, productionMode = false)
public class Servlet extends VaadinServlet {
    /**
     * The router configurator defines the how to map URLs to views.
     */
    public static class MyRouterConfigurator implements RouterConfigurator {
        @Override
        public void configure(RouterConfiguration configuration) {
            configuration.setRoute("", PatientPortal.class);
        }
    }
}
