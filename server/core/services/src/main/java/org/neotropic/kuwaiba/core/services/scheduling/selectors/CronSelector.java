/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.neotropic.kuwaiba.core.services.scheduling.selectors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import lombok.Getter;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.CronDefinition;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This component allows the consumer to choose and select options to update the cronExpression of desired job
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
public class CronSelector extends FlexLayout {
    /**
     * Reference to the translation service.
     */
    private final TranslationService ts;
    /**
     * Object to show errors while select the cron options
     */
    private final Label lblMessages;
    /**
     * combo to picker frequency execution
     */
    private ComboBox<CronDefinition.CronOptions> executePicker;
    /**
     * combo to select every options
     */
    private ComboBox<CronDefinition.CronOptions> frequencyPicker;
    /**
     * combo to select weekly options
     */
    private ComboBox<CronDefinition.CronOptions> weeklyOptions;
    /**
     * combo to picker every options
     */
    private ComboBox<Integer> intervalPicker;
    /**
     * combo to picker daily options
     */
    private TimePicker hourPicker;
    /**
     * combo to picker days
     */
    private ComboBox<Integer> dayPicker;
    /**
     * combo box layout
     */
    private VerticalLayout lytCronOptions;
    /**
     * main layout
     */
    private VerticalLayout lytMainCron;
    /**
     * flat to validate combo box
     */
    private boolean isValid = true;

    public CronSelector(TranslationService ts, Button accept) {
        this.ts = ts;

        lytMainCron = new VerticalLayout();
        lytMainCron.setWidthFull();
        lytMainCron.addClassName("test");
        lblMessages = new Label();
        lblMessages.setVisible(false);
        lblMessages.addClassName("error");
        lytCronOptions = new VerticalLayout();
        lytCronOptions.addClassName("dialog-padding");
        lytCronOptions.setVisible(false);
        buildCronOptions();

        lytMainCron.add(executePicker, lytCronOptions, lblMessages);

        add(lytMainCron);
        setWidthFull();

        accept.addClickListener(event -> {
            validateComponents();
            if (isValid)
                fireEvent(new CronResultEvent(this, false, buildCronExpression()));
        });
    }

    private void buildCronOptions() {
        executePicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.execute"));
        executePicker.setWidthFull();
        executePicker.setItems(buildExecuteOptions());
        executePicker.setItemLabelGenerator(CronDefinition.CronOptions::getOption);
        executePicker.setAllowCustomValue(false);
        executePicker.setRequiredIndicatorVisible(true);
        executePicker.addValueChangeListener(event -> updateLayoutCron());

        frequencyPicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.frequency"));
        frequencyPicker.setWidthFull();
        frequencyPicker.setItems(buildFrequencyOptions());
        frequencyPicker.setItemLabelGenerator(CronDefinition.CronOptions::getOption);
        frequencyPicker.setAllowCustomValue(false);
        frequencyPicker.setRequiredIndicatorVisible(true);
        frequencyPicker.addValueChangeListener(event -> updateIntervalPickerValues());

        intervalPicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.interval"));
        intervalPicker.setWidthFull();
        intervalPicker.setWidth(5, Unit.EM);
        intervalPicker.setAllowCustomValue(false);
        intervalPicker.setRequiredIndicatorVisible(true);
        intervalPicker.addValueChangeListener(event -> validateIntervalPicker());

        weeklyOptions = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.day-week"));
        weeklyOptions.setWidthFull();
        weeklyOptions.setItems(buildWeekOption());
        weeklyOptions.setItemLabelGenerator(CronDefinition.CronOptions::getOption);
        weeklyOptions.setAllowCustomValue(false);
        weeklyOptions.setRequiredIndicatorVisible(true);
        weeklyOptions.addValueChangeListener(event -> validateWeeklyPicker());

        hourPicker = new TimePicker();
        hourPicker.setWidthFull();
        hourPicker.setLabel(ts.getTranslatedString("module.scheduleJob.ui.label.at"));
        hourPicker.setStep(Duration.ofHours(1));
        hourPicker.setLocale(Locale.US);
        hourPicker.setRequiredIndicatorVisible(true);
        hourPicker.addValueChangeListener(event -> validateHourPicker());

        dayPicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.day"));
        dayPicker.setWidthFull();
        dayPicker.setWidth(5, Unit.EM);
        dayPicker.setAllowCustomValue(false);
        dayPicker.setRequiredIndicatorVisible(true);
        dayPicker.setItems(IntStream.range(1, 31 + 1)
                .boxed()
                .collect(Collectors.toList()));
        dayPicker.addValueChangeListener(event -> validateDayPicker());

    }

    private void updateLayoutCron() {
        if (executePicker.getValue() == null) {
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
            lytCronOptions.setVisible(true);
            refresh();
            return;
        }

        resetCronOptions();

        int value = executePicker.getValue().getIntValue();
        switch (value) {
            case CronDefinition.EXECUTE_TYPE_EVERY:
                lytCronOptions.add(frequencyPicker, intervalPicker);
                weeklyOptions.setValue(new CronDefinition.CronOptions("",0));
                dayPicker.setValue(0);
                hourPicker.setValue(LocalTime.of(0, 0));
                break;
            case CronDefinition.EXECUTE_TYPE_DAILY:
                lytCronOptions.add(hourPicker);
                weeklyOptions.setValue(new CronDefinition.CronOptions("",0));
                dayPicker.setValue(0);
                frequencyPicker.setValue(new CronDefinition.CronOptions("",0));
                intervalPicker.setValue(0);
                break;
            case CronDefinition.EXECUTE_TYPE_WEEKLY:
                lytCronOptions.add(weeklyOptions, hourPicker);
                dayPicker.setValue(0);
                frequencyPicker.setValue(new CronDefinition.CronOptions("",0));
                intervalPicker.setValue(0);
                break;
            case CronDefinition.EXECUTE_TYPE_MONTHLY:
                lytCronOptions.add(dayPicker, hourPicker);
                weeklyOptions.setValue(new CronDefinition.CronOptions("",0));
                frequencyPicker.setValue(new CronDefinition.CronOptions("",0));
                intervalPicker.setValue(0);
                break;
            default:
                lytCronOptions.removeAll();
        }
        lytCronOptions.setVisible(true);
    }

    private void resetCronOptions() {
        lytCronOptions.removeAll();
        frequencyPicker.setValue(null);
        intervalPicker.setValue(null);
        hourPicker.setValue(null);
        weeklyOptions.setValue(null);
        dayPicker.setValue(null);
        lblMessages.setVisible(false);
    }

    private void updateIntervalPickerValues() {
        if (frequencyPicker.getValue() == null) {
            intervalPicker.setValue(null);
            intervalPicker.setEnabled(false);
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
            refresh();
            return;
        }

        lblMessages.setVisible(false);
        refresh();

        int value = frequencyPicker.getValue().getIntValue();
        if (value == 3)
            intervalPicker.setItems(IntStream.range(1, 23 + 1)
                    .boxed()
                    .collect(Collectors.toList()));
        else
            intervalPicker.setItems(IntStream.range(1, 59 + 1)
                    .boxed()
                    .collect(Collectors.toList()));

        intervalPicker.setEnabled(true);
    }

    private void validateIntervalPicker() {
        if (intervalPicker.getValue() == null) {
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
            refresh();
            return;
        }

        lblMessages.setVisible(false);
        refresh();
    }

    private void validateWeeklyPicker() {
        if (weeklyOptions.getValue() == null) {
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
            refresh();
            return;
        }

        lblMessages.setVisible(false);
        refresh();
    }

    private void validateHourPicker() {
        if (hourPicker.getValue() == null) {
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
            refresh();
            return;
        }

        lblMessages.setVisible(false);
        refresh();
    }

    private void validateDayPicker() {
        if (intervalPicker.getValue() == null) {
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
            refresh();
            return;
        }

        lblMessages.setVisible(false);
        refresh();
    }

    private void validateComponents() {
        isValid = true;

        if (executePicker.getValue() == null) {
            isValid = false;
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
        }

        if (executePicker.getValue() != null) {
            int value = executePicker.getValue().getIntValue();
            switch (value) {
                case CronDefinition.EXECUTE_TYPE_EVERY:
                    if (frequencyPicker.getValue() == null || intervalPicker.getValue() == null) {
                        isValid = false;
                    }
                    break;
                case CronDefinition.EXECUTE_TYPE_DAILY:
                    if (hourPicker.getValue() == null) {
                        isValid = false;
                    }
                    break;
                case CronDefinition.EXECUTE_TYPE_WEEKLY:
                    if (weeklyOptions.getValue() == null || hourPicker.getValue() == null) {
                        isValid = false;
                    }
                    break;
                case CronDefinition.EXECUTE_TYPE_MONTHLY:
                    if (dayPicker.getValue() == null || hourPicker.getValue() == null) {
                        isValid = false;
                    }
                    break;
                default:
                    break;
            }
        }

        if (!isValid) {
            lblMessages.setVisible(true);
            lblMessages.setText(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
        } else
            lblMessages.setVisible(false);

        refresh();
    }

    private Collection<CronDefinition.CronOptions> buildExecuteOptions() {
        Collection<CronDefinition.CronOptions> options = new ArrayList<>();
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.every"), 1));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.daily"),2));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.weekly"),3));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.monthly"), 4));

        return options;
    }

    private Collection<CronDefinition.CronOptions> buildFrequencyOptions() {
        Collection<CronDefinition.CronOptions> options = new ArrayList<>();
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.frequency.option.seg"),1));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.frequency.option.min"),2));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.frequency.option.hr"),3));

        return options;
    }

    private Collection<CronDefinition.CronOptions> buildWeekOption() {
        Collection<CronDefinition.CronOptions> options = new ArrayList<>();
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.sun"),1));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.mon"),2));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.tue"),3));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.wed"),4));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.thu"),5));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.fri"),6));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.sat"),7));

        return options;
    }

    private void refresh() {
        lytMainCron.remove(lblMessages);
        lytMainCron.add(executePicker, lytCronOptions, lblMessages);
    }

    private String buildCronExpression() {
        return CronDefinition.getCronExpression(executePicker.getValue().getIntValue(),
                frequencyPicker.getValue().getIntValue(),
                intervalPicker.getValue(),
                dayPicker.getValue(),
                weeklyOptions.getValue().getIntValue(),
                hourPicker.getValue().getHour(), ts);
    }

    public void addResultCron(ComponentEventListener<CronResultEvent> listener) {
        addListener(CronResultEvent.class, listener);
    }

    @Getter
    public static class CronResultEvent extends ComponentEvent<CronSelector> {
        private final String cronExpression;

        public CronResultEvent(CronSelector source, boolean fromClient, String cron) {
            super(source, fromClient);
            this.cronExpression = cron;
        }

    }

}
