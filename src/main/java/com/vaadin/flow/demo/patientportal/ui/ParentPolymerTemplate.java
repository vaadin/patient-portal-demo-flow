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

package com.vaadin.flow.demo.patientportal.ui;


import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * {@link PolymerTemplate} that acts as a {@link HasChildView}. The HTML markup
 * should be loaded using the {@link HtmlImport @HtmlImport} annotation and the
 * components should be associated with the web component element using the
 * {@link Tag @Tag} annotation. The HTML markup should contain slot-tags to show
 * where the child-view will be placed.
 *
 * @param <M>
 *            a model class that will be used for template data propagation
 *
 * @see PolymerTemplate
 * @see HtmlImport
 * @see Tag
 *
 * @author Vaadin Ltd
 *
 */
public abstract class ParentPolymerTemplate<M extends TemplateModel>
        extends PolymerTemplate<M> /*implements HasChildView */{

    private HasElement childView;

/* todo what is was?
    @Override
    public void setChildView(com.vaadin.flow.router.legacy.View childView) {

        if (this.childView != null) {
            getElement().removeChild(this.childView.getElement());
        }
        this.childView = childView;

        if (this.childView != null) {
            getElement().appendChild(this.childView.getElement());
        }
    }
*/
}
