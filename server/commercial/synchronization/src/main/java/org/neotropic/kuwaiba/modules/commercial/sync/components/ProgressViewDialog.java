/*
 * Copyright 2010-2024. Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.neotropic.kuwaiba.modules.commercial.sync.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import lombok.Getter;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.sync.model.SyncResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * A custom dialog for displaying progress and results of a synchronization job.
 *
 * @author Hardy Ryan Chingal Martinez <ryan.chingal@neotropic.co>
 */
public class ProgressViewDialog extends Dialog {
    /**
     * A static SerializableBiConsumer used for updating the status component based on synchronization results.
     * wher espan is the Span component to be updated and syncResult is the synchronization result to determine the status.
     */
    private static final SerializableBiConsumer<Span, SyncResultItem> statusComponentUpdater = (span, syncResult) -> {
        if (syncResult.getTypeAsString().toUpperCase().equals(ESyncResultState.SUCCESS.toString())) {
            span.add(createIcon(VaadinIcon.CHECK), new Span(ESyncResultState.SUCCESS.toString()));
            span.addClassName("sync-result-state-success");
        } else if (syncResult.getTypeAsString().toUpperCase().equals(ESyncResultState.ERROR.toString())) {
            span.add(createIcon(VaadinIcon.EXCLAMATION_CIRCLE_O), new Span(ESyncResultState.ERROR.toString()));
            span.addClassName("sync-result-state-error");
        } else if (syncResult.getTypeAsString().toUpperCase().equals(ESyncResultState.WARNING.toString())) {
            span.add(createIcon(VaadinIcon.WARNING), new Span(ESyncResultState.WARNING.toString()));
            span.addClassName("sync-result-state-warning");
        } else if (syncResult.getTypeAsString().toUpperCase().equals(ESyncResultState.INFORMATION.toString())) {
            span.add(createIcon(VaadinIcon.INFO_CIRCLE_O), new Span(ESyncResultState.INFORMATION.toString()));
            span.addClassName("sync-result-state-information");
        } else {
            span.add(createIcon(VaadinIcon.CIRCLE), new Span(ESyncResultState.INFORMATION.toString()));
            span.addClassName("sync-result-state-information");
        }
        span.addClassName("align-center");
    };
    private final VerticalLayout bodyDialog;
    private final Div fetchProgressBarLabel;
    private final Div analizedProgressBarLabel;
    private final Span fetchMessage;
    private final Span analizeMessage;
    private final ProgressBar fetchProgressBar;
    private final ProgressBar analizedProgressBar;
    @Getter
    private final Button btnConfirm;
    private final Button btnClose;
    private final TranslationService ts;
    private final Grid<SyncResultItem> grdSyncResult;
    private final AtomicInteger currentPage = new AtomicInteger(0);
    private final int pageSize = 10;
    private Button btnNext;
    private Button btnPrevious;
    private List<SyncResultItem> grdSyncResultList;
    private HorizontalLayout buttonsDiv;
    private LoggingService log;

    /**
     * Constructs a `ProgressViewDialog`.
     *
     * @param ts The TranslationService for localization.
     */
    public ProgressViewDialog(TranslationService ts, LoggingService log) {
        setMinWidth("75%");
        this.bodyDialog = new VerticalLayout();
        HorizontalLayout fetchData = new HorizontalLayout();
        HorizontalLayout analizeData = new HorizontalLayout();
        this.btnConfirm = new Button(ts.getTranslatedString("module.sync.data-source.button.accept"));
        this.btnClose = new Button(ts.getTranslatedString("module.sync.data-source.button.close"));
        this.fetchMessage = new Span();
        this.analizeMessage = new Span();
        this.fetchProgressBar = new ProgressBar();
        this.analizedProgressBar = new ProgressBar();
        this.fetchProgressBarLabel = new Div();
        this.analizedProgressBarLabel = new Div();
        this.ts = ts;
        this.setDraggable(true);
        this.setResizable(true);
        this.setModal(false);
        this.grdSyncResult = new Grid<>();
        this.log = log;
        Accordion accordion = new Accordion();
        // Set general attribute properties
        fetchData.setWidthFull();
        fetchData.addAndExpand(fetchMessage);
        fetchProgressBar.setWidthFull();
        VerticalLayout fetchVL = new VerticalLayout(fetchProgressBarLabel, fetchProgressBar);
        fetchVL.setHorizontalComponentAlignment(FlexComponent.Alignment.END, fetchProgressBarLabel);
        fetchData.add(fetchVL);
        fetchData.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        analizeData.setWidthFull();
        analizeData.addAndExpand(analizeMessage);
        analizedProgressBar.setWidthFull();
        VerticalLayout analizedVL = new VerticalLayout(analizedProgressBarLabel, analizedProgressBar);
        analizedVL.setHorizontalComponentAlignment(FlexComponent.Alignment.END, analizedProgressBarLabel);
        analizeData.add(analizedVL);
        analizeData.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        this.bodyDialog.add(fetchData, analizeData);
        createGrdView();

        accordion.add(ts.getTranslatedString("module.sync.dialog.tittle"), bodyDialog);
        this.add(accordion);
        this.btnConfirm.addClickListener(event -> this.close());
        this.btnConfirm.setClassName("confirm-button");
        this.btnConfirm.addThemeVariants(ButtonVariant.LUMO_LARGE);
        this.btnConfirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        this.btnClose.addClickListener(event -> this.close());

        this.bodyDialog.add(btnClose);
        this.bodyDialog.setHorizontalComponentAlignment(FlexComponent.Alignment.START, btnClose);
    }

    /**
     * Creates and returns an Icon with the specified VaadinIcon.
     *
     * @param vaadinIcon The VaadinIcon used to create the Icon.
     * @return An Icon with the provided VaadinIcon.
     */
    private static Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs").set("box-sizing", "border-box");
        return icon;
    }

    /**
     * Creates a ComponentRenderer for rendering a status component for synchronization results.
     *
     * @return A ComponentRenderer for rendering synchronization result status.
     */
    private static ComponentRenderer<Span, SyncResultItem> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    /**
     * Update the values and UI components of the dialog based on the progress and sync results.
     *
     * @param progress    The job progress.
     * @param syncResults The list of synchronization results.
     */
    public void updateValues(JobProgressMessage progress, List<SyncResult> syncResults) {
        UI ui = UI.getCurrent();
        if (ui != null) {
            ui.access(() -> {
                if (progress.getStep() == EAsyncStep.FETCH) {
                    fetchMessage.setText(String.format("%s %s", ts.getTranslatedString(progress.getStep().getValue())
                            , ts.getTranslatedString(progress.getState().getValue())));
                    fetchProgressBarLabel.setText(String.format("Tasks (%s/%s)", progress.getElement(), progress.getTotalElements()));
                    fetchProgressBar.setValue(progress.getProgress());

                } else if (progress.getStep() == EAsyncStep.ANALYZE) {
                    analizeMessage.setText(String.format("%s %s", ts.getTranslatedString(progress.getStep().getValue())
                            , ts.getTranslatedString(progress.getState().getValue())));
                    analizedProgressBarLabel.setText(String.format("Tasks (%s/%s)", progress.getElement(), progress.getTotalElements()));
                    analizedProgressBar.setValue(progress.getProgress());
                }

                if (!syncResults.isEmpty()) {
                    List<SyncResultItem> gridItems = syncResults.stream().map(SyncResultItem::new).collect(Collectors.toList());
                    grdSyncResult.setItems(gridItems);
                    grdSyncResultList = gridItems;
                    grdSyncResult.setVisible(true);
                    grdSyncResult.getDataProvider().refreshAll();
                    updateGridData(currentPage.get(), pageSize);
                    buttonsDiv.setVisible(true);
                }

                if (progress.getStep() == EAsyncStep.ANALYZE && progress.getState() == EJobState.FINISH) {
                    this.bodyDialog.remove(btnClose);
                    this.bodyDialog.add(btnConfirm);
                    this.bodyDialog.setHorizontalComponentAlignment(FlexComponent.Alignment.END, btnConfirm);
                }
                ui.push();
            });
        } else {
            log.writeLogMessage(LoggerType.ERROR, ProgressViewDialog.class, "UI is null. Unable to update values.");
        }
    }

    /**
     * Create and set up the Grid view for displaying synchronization results.
     */
    private void createGrdView() {
        grdSyncResult.setVisible(false);
        grdSyncResult.setPageSize(10);
        grdSyncResult.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
        grdSyncResult.addColumn(createStatusComponentRenderer());
        grdSyncResult.addColumn(SyncResultItem::getResult)
                .setClassNameGenerator(item -> "wrap-cell");
        grdSyncResult.addColumn(SyncResultItem::getActionDescription)
                .setClassNameGenerator(item -> "wrap-cell");
        //this.bodyDialog.add(grdSyncResult);

        // Add pagination controls (e.g., next and previous buttons)
        btnNext = new Button("Next Page");
        btnPrevious = new Button("Previous Page");
        buttonsDiv = new HorizontalLayout(btnPrevious, btnNext);

        buttonsDiv.setVisible(false);
        btnNext.addClickListener(event -> updateGridData(currentPage.incrementAndGet(), pageSize));
        btnNext.setClassName("confirm-button");
        btnNext.addThemeVariants(ButtonVariant.LUMO_LARGE);
        btnNext.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnPrevious.addClickListener(event -> updateGridData(currentPage.decrementAndGet(), pageSize));
        btnPrevious.setClassName("confirm-button");
        btnPrevious.addThemeVariants(ButtonVariant.LUMO_LARGE);
        btnPrevious.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // Create a PagedDataProvider and set it as the Grid's data provider
        updateGridData(currentPage.get(), pageSize);
        this.bodyDialog.add(grdSyncResult, buttonsDiv);
        this.bodyDialog.setHorizontalComponentAlignment(FlexComponent.Alignment.END, buttonsDiv);

    }

    /**
     * Update the data displayed in the Grid for pagination.
     *
     * @param page     The page number.
     * @param pageSize The number of items per page.
     */
    private void updateGridData(int page, int pageSize) {
        List<SyncResultItem> items = getItemsForPage(page, pageSize);
        grdSyncResult.setItems(items);
    }

    /**
     * Get the items to be displayed in the Grid for the specified page and page size.
     *
     * @param page     The page number.
     * @param pageSize The number of items per page.
     * @return A list of synchronization results for the specified page.
     */
    private List<SyncResultItem> getItemsForPage(int page, int pageSize) {
        if (grdSyncResultList != null) {
            int start = page * pageSize;
            int end = Math.min(start + pageSize, grdSyncResultList.size());
            toggleNavigationButton(btnNext, true);
            toggleNavigationButton(btnPrevious, true);

            if (start >= grdSyncResultList.size()) {
                start = Math.max(0, grdSyncResultList.size() - pageSize);
                toggleNavigationButton(btnNext, false);
                currentPage.decrementAndGet();
            } else
                toggleNavigationButton(btnNext, true);

            if (pageSize >= grdSyncResultList.size() || end == grdSyncResultList.size())
                toggleNavigationButton(btnNext, false);

            if (end <= 0) {
                end = Math.min(grdSyncResultList.size(), pageSize);
                toggleNavigationButton(btnPrevious, false);
                currentPage.set(0);
            } else
                toggleNavigationButton(btnPrevious, true);
            if (page == 0)
                toggleNavigationButton(btnPrevious, false);

            return grdSyncResultList.subList(start, end);
        }
        return new ArrayList<>();
    }

    /**
     * Enable or disable a button
     *
     * @param btn    grid navigation button
     * @param toogle boolean with enable value
     */
    private void toggleNavigationButton(Button btn, boolean toogle) {
        btn.setEnabled(toogle);
        if (toogle) {
            btn.setClassName("confirm-button");
            btn.addThemeVariants(ButtonVariant.LUMO_LARGE);
            btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        } else {
            btn.removeClassName("confirm-button");
            btn.removeThemeVariants(ButtonVariant.LUMO_LARGE);
            btn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }
    }
}
