package com.vaadin.flow.demo.patientportal;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

@Tag("patient-portal")
@HtmlImport("context://components/patient-portal.html")
public class PatientPortal
        extends PolymerTemplate<PatientPortal.PatientPortalModel> implements View {

    public PatientPortal() {

    }

    public interface PatientPortalModel extends TemplateModel {

    }

}
