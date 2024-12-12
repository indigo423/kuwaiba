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
package org.neotropic.kuwaiba.core.services.scheduling.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingModule;

/**
 * This class represent a cron expression structure in module {@link SchedulingModule}
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Data
@AllArgsConstructor
public class CronDefinition {
    public static final int EXECUTE_TYPE_EVERY = 1;//Every...
    public static final int EXECUTE_TYPE_DAILY = 2;//Daily
    public static final int EXECUTE_TYPE_WEEKLY = 3;//Weekly
    public static final int EXECUTE_TYPE_MONTHLY = 4;//Monthly
    public static final int FREQUENCY_SECONDS = 1;//Seconds
    public static final int FREQUENCY_MINUTES = 2;//Minutes
    public static final int FREQUENCY_HOURS = 3;//Hours

    /**
     * returns a cron expressions from the values supplied by the UI selectors
     * @param executeType type of execute (every, daily, weekly, monthly)
     * @param frequency type of frequency (seconds, minutes, hours )
     * @param interval interval to execute
     * @param dayOfMonth day of execution
     * @param dayOfWeek ady of week to execution
     * @param hour hour of execution
     * @param ts reference to translation service
     * @return A cron expression with the following format 0 0 0 ? * *
     */
    public static String getCronExpression(int executeType, int frequency, int interval, int dayOfMonth, int dayOfWeek, int hour, TranslationService ts) {
        StringBuilder cronExpressionBuilder = new StringBuilder();

        switch (executeType) {
            case EXECUTE_TYPE_EVERY:
                switch (frequency) {
                    case FREQUENCY_SECONDS:
                        cronExpressionBuilder.append("0/").append(interval).append(" * * ? * *");
                        break;
                    case FREQUENCY_MINUTES:
                        cronExpressionBuilder.append("0 0/").append(interval).append(" * ? * *");
                        break;
                    case FREQUENCY_HOURS:
                        cronExpressionBuilder.append("0 0 0/").append(interval).append(" ? * *");
                        break;
                    default:
                        throw new IllegalArgumentException(ts.getTranslatedString("module.scheduleJob.error.actions.update.cron.execute"));
                }
                break;
            case EXECUTE_TYPE_DAILY:
                cronExpressionBuilder.append("0 0 ").append(hour).append(" ? * *");
                break;
            case EXECUTE_TYPE_WEEKLY:
                cronExpressionBuilder.append("0 0 ").append(hour).append(" ? * ").append(getValueOfDay(dayOfWeek));
                break;
            case EXECUTE_TYPE_MONTHLY:
                cronExpressionBuilder.append("0 0 ").append(hour).append(" ").append(dayOfMonth).append(" * ?");
                break;
            default:
                throw new IllegalArgumentException(ts.getTranslatedString("module.scheduleJob.error.actions.update-cron"));
        }

        return cronExpressionBuilder.toString();
    }

    private static String getValueOfDay(int daySelected) {
        switch (daySelected) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THU";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                throw new IllegalArgumentException("wrong day of week: " + daySelected);
        }
    }
    /**
     * returns the Convert of cron expression to readable text format
     * @param cronExpression cron expression to convert
     * @param ts  reference to translation service
     * @return readable text format of cron expression
     */
    public static String getCronSummary(String cronExpression, TranslationService ts) {
        String[] cronParts = cronExpression.split(" ");
        int hour = 0;
        String formatHour = "";
        if (!cronParts[2].equals("*") && !cronParts[2].contains("/")) {
            hour = Integer.parseInt(cronParts[2]);
            formatHour = (hour < 12) ? ":00:00 AM" : ":00:00 PM";
        }
        // Analyze dayOfMonth and dayOfWeek to determine frequency
        if (!cronParts[3].equals("?"))
            // Monthly execution
            return String.format(ts.getTranslatedString("module.scheduleJob.ui.label.cron-summary-every"), cronParts[3], hour + formatHour);
        else if (!cronParts[5].equals("*"))
            // Weekly execution
            return String.format(ts.getTranslatedString("module.scheduleJob.ui.label.cron-summary-every"), getNameOfWeekDay(cronParts[5], ts), hour + formatHour);
        else {
            // Analyze hour and minute for frequency within a day
            if (!cronParts[2].equals("*")) {
                if (cronParts[2].contains("/")) {
                    // Every hour execution
                    String[] hourPart = cronParts[2].split("/");
                    return String.format(ts.getTranslatedString("module.scheduleJob.ui.label.cron-summary-hours"), hourPart[1]);
                } else
                    // Daily execution
                    return String.format(ts.getTranslatedString("module.scheduleJob.ui.label.cron-summary-every-day"), hour + formatHour);
            } else if (cronParts[1].contains("/")) {
                // Every minute execution
                String[] minutePart = cronParts[1].split("/");
                return String.format(ts.getTranslatedString("module.scheduleJob.ui.label.cron-summary-min"), minutePart[1]);
            } else {
                // Every second execution
                String[] secondPart = cronParts[0].split("/");
                return String.format(ts.getTranslatedString("module.scheduleJob.ui.label.cron-summary-seg"), secondPart[1]);
            }
        }
    }

    private static String getNameOfWeekDay(String dayOfWeek, TranslationService ts) {
        switch (dayOfWeek) {
            case "SUN":
                return ts.getTranslatedString("module.scheduleJob.ui.label.week.option.sun");// "Sunday";
            case "MON":
                return ts.getTranslatedString("module.scheduleJob.ui.label.week.option.mon");//"Monday";
            case "TUE":
                return ts.getTranslatedString("module.scheduleJob.ui.label.week.option.tue");//"Tuesday";
            case "WED":
                return ts.getTranslatedString("module.scheduleJob.ui.label.week.option.wed");//"Wednesday";
            case "THU":
                return ts.getTranslatedString("module.scheduleJob.ui.label.week.option.thu");//"Thursday";
            case "FRI":
                return ts.getTranslatedString("module.scheduleJob.ui.label.week.option.fri");//"Friday";
            case "SAT":
                return ts.getTranslatedString("module.scheduleJob.ui.label.week.option.sat");//"Saturday";
            default:
                throw new IllegalArgumentException("wrong day of week ");
        }
    }

    /**
     * Class to build cron options
     */
    @Data
    public static class CronOptions {

        private String option;

        private int intValue;

        public CronOptions(String option, int intValue) {
            this.option = option;
            this.intValue = intValue;
        }

    }
}
