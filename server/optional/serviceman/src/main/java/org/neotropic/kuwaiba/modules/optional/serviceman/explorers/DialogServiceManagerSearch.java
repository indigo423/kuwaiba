/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.neotropic.kuwaiba.modules.optional.serviceman.explorers;

import com.neotropic.flow.component.paper.dialog.PaperDialog;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResourceRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.icons.IconGenerator;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

/**
 * Dynamic dialog that shows the search results while the user is typing in the
 * search box.
 *
 * @author Adrian Martinez Molina {@literal <adrian.martinez@kuwaiba.org>}
 */
public class DialogServiceManagerSearch extends Div {
    private PaperDialog paperDialog;
    private TextField txtSearch;

    public DialogServiceManagerSearch(TranslationService ts,
            BusinessEntityManager bem, MetadataEntityManager mem,
            IconGenerator iconGenerator, String classToApply,
            String placeHolder, Consumer<Object> consumerSearch) {

        ActionIcon icnSearch = new ActionIcon(VaadinIcon.SEARCH);
        txtSearch = new TextField();
        txtSearch.setAutofocus(true);
        txtSearch.setClassName("serviceman-search-box-large");
        txtSearch.setPlaceholder(placeHolder);
        txtSearch.setSuffixComponent(icnSearch);
        txtSearch.setClearButtonVisible(true);
        txtSearch.setValueChangeMode(ValueChangeMode.EAGER);
        txtSearch.setWidthFull();

        paperDialog = new PaperDialog();
        paperDialog.setNoOverlap(true);
        paperDialog.setHorizontalAlign(PaperDialog.HorizontalAlign.LEFT);
        paperDialog.setVerticalAlign(PaperDialog.VerticalAlign.TOP);
        paperDialog.setMargin(false);
        paperDialog.positionTarget(txtSearch);
        paperDialog.setWidth(txtSearch.getWidth());
        paperDialog.setClassName("serviceman-paper-dialog-style");

        add(paperDialog);
        add(txtSearch);

        txtSearch.addKeyPressListener(e -> {
            //Weirdly enough, event.getKey().equals(Key.Enter) ALWAYS returns false
            if (e.getKey().getKeys().get(0).equals(Key.ENTER.getKeys().get(0))) 
                consumerSearch.accept(txtSearch.getValue());
        });

        icnSearch.addClickListener(e -> {
            if (!txtSearch.getValue().isEmpty())
                consumerSearch.accept(txtSearch.getValue());
        });

        txtSearch.addValueChangeListener(e -> {
            paperDialog.removeAll();
            paperDialog.setVisible(true);

            if (e.isFromClient() && e.getValue().length() > 2) {
                try {
                    VerticalLayout lytContent = new VerticalLayout();
                    lytContent.setPadding(false);
                    lytContent.setMargin(false);
                    lytContent.setSpacing(false);
                    lytContent.getElement().setProperty("margin-top", "10px");
                    lytContent.setClassName("search-results-content");
                    Grid<BusinessObjectLight> tblElements = new Grid<>();
                    List<BusinessObjectLight> results = new ArrayList<>();

                    HashMap<String, List<BusinessObjectLight>> searchResults
                            = bem.getSuggestedObjectsWithFilterGroupedByClassName(
                                    null, e.getValue(),
                                    0, 5, 0, 10);

                    if (!searchResults.isEmpty()) {
                        for (Map.Entry<String, List<BusinessObjectLight>> entry : searchResults.entrySet()) {
                            entry.getValue().forEach(object -> {
                                try {
                                    if (mem.isSubclassOf(classToApply, object.getClassName()))
                                        results.add(object);
                                } catch (MetadataObjectNotFoundException ex) {
                                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                                }
                            });
                        }
                    }

                    if (results.isEmpty()) {
                        lytContent.add(new Label(ts.getTranslatedString("module.general.messages.no-search-results")));
                        paperDialog.setHeight("60px");
                    } else {
                        ComponentRenderer<Component, BusinessObjectLight> resultRenderer = new ComponentRenderer<>(
                                item -> {
                                    HorizontalLayout lineItem = new HorizontalLayout();
                                    lineItem.setPadding(false);
                                    lineItem.setMargin(false);
                                    lineItem.setSpacing(true);

                                    lineItem.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
                                    if (item instanceof BusinessObjectLight) {
                                        Image objIcon = new Image(iconGenerator.apply(((BusinessObjectLight) item).getClassName()), "-");
                                        objIcon.setClassName("serviceman-search-icons");
                                        lineItem.add(objIcon);
                                        lineItem.add(new Label(((BusinessObjectLight) item).toString()));
                                    }
                                    return lineItem;
                                });
                        tblElements.addColumn(resultRenderer);
                        tblElements.setItems(results);
                        tblElements.addItemClickListener(t -> consumerSearch.accept(t.getItem()));
                        tblElements.setAllRowsVisible(true);
                        lytContent.add(tblElements);
                        paperDialog.setHeight("auto");
                    }
                    paperDialog.add(lytContent);
                    paperDialog.open();
                } catch (InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            } else if (e.getValue().isEmpty()) {
                paperDialog.close();
            }

            txtSearch.focus();
        });
    }

    public void close() {
        paperDialog.removeAll();
        paperDialog.setVisible(false);
    }

    public void clearSearch() {
        txtSearch.setValue("");
    }
}