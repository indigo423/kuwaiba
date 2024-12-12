/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        https://apache.org/licenses/LICENSE-2.0.txt
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.neotropic.kuwaiba.web.ui;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.icon.ActionIcon;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;

/**
 * The default error page.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@StyleSheet("css/main.css")
@Route(value = "error")
public class ErrorUI extends VerticalLayout implements HasErrorParameter<NotFoundException> {

    /**
     * Reference to the internationalization service.
     */
    @Autowired
    private TranslationService ts;

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
        ActionIcon iconError = new ActionIcon(VaadinIcon.CLOSE_CIRCLE);
        iconError.setClassName("error-icon");

        Html infoError = new Html(ts.getTranslatedString("module.error.labels.page-not-found"));
        Anchor linkError = new Anchor("/kuwaiba/home", ts.getTranslatedString("module.error.labels.back-to-home-page"));

        VerticalLayout lytError = new VerticalLayout(iconError, infoError, linkError);
        lytError.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(lytError);

        return HttpServletResponse.SC_NOT_FOUND;
    }
}