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
package org.neotropic.kuwaiba.modules.core.logging.slf4j;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import java.util.HashMap;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

/**
 * Implements the methods that allow logging messages.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
@Service
public class LoggingServiceImpl implements LoggingService {
    
    private static final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    
    private final HashMap<String, Logger> loggers;
    
    private final String filePattern;

    public LoggingServiceImpl() {
        this.filePattern = "%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n";
        this.loggers = new HashMap<>();
    }
    
    @Override
    public void writeLogMessage(LoggerType type, Class source, String message) {
        Logger currentLogger = getLogger(source);
        sendMessage(type, currentLogger, message);
    }

    @Override
    public void writeLogMessage(LoggerType type, Class source, String message, Throwable ex) {
        Logger currentLogger = getLogger(source);
        sendMessageThrowable(type, currentLogger, message, ex);
    }
    
    @Override
    public void registerLog(String loggerName, String filePath, String fileName, int maxIndex) {
        
        // Configure encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(filePattern);
        encoder.start();

        // Configure appender
        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
        rollingFileAppender.setContext(context);
        rollingFileAppender.setFile(filePath + fileName);
        rollingFileAppender.setEncoder(encoder);
        
        String fileRollingName = (fileName != null && fileName.lastIndexOf('.') != -1) ? 
                fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
        
        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(rollingFileAppender);  // Set the parent of the rolling policy
        rollingPolicy.setFileNamePattern(filePath + fileRollingName + ".%d{yyyy-MM-dd}.log.gz");  // Name files rolling
        rollingPolicy.setMaxHistory(maxIndex);  // Maximum number of files to keep
        rollingPolicy.start();
        
        rollingFileAppender.setRollingPolicy(rollingPolicy);
        rollingFileAppender.start();

        // Configure the specific logger
        ch.qos.logback.classic.Logger specificLogger = context.getLogger(loggerName);
        specificLogger.detachAndStopAllAppenders();  // Delete existing appender
        specificLogger.addAppender(rollingFileAppender);
        specificLogger.setLevel(ch.qos.logback.classic.Level.TRACE);
        specificLogger.setAdditive(false);
    }
    
    @Override
    public void registerLog(String loggerName, String filePath, String fileName, String maxFileSize, int maxIndex) {
        if(isValidFileSize(maxFileSize)) {
            // Configure encoder
            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
            encoder.setContext(context);
            encoder.setPattern(filePattern);
            encoder.start();

            // Configure appender
            RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
            rollingFileAppender.setContext(context);
            rollingFileAppender.setFile(filePath + fileName);
            rollingFileAppender.setEncoder(encoder);

            String fileRollingName = (fileName != null && fileName.lastIndexOf('.') != -1) ? 
                    fileName.substring(0, fileName.lastIndexOf('.')) : fileName;

            SizeBasedTriggeringPolicy<ILoggingEvent> sizeBasedTriggeringPolicy = new SizeBasedTriggeringPolicy<>();
            sizeBasedTriggeringPolicy.setMaxFileSize(FileSize.valueOf(maxFileSize));
            sizeBasedTriggeringPolicy.start();

            // Configure size and time-based rolling policy
            FixedWindowRollingPolicy fixedWindowRollingPolicy = new FixedWindowRollingPolicy();
            fixedWindowRollingPolicy.setContext(context);
            fixedWindowRollingPolicy.setParent(rollingFileAppender);
            fixedWindowRollingPolicy.setFileNamePattern(filePath + fileRollingName + ".%i.log.gz");
            fixedWindowRollingPolicy.setMinIndex(1);
            fixedWindowRollingPolicy.setMaxIndex(maxIndex);
            fixedWindowRollingPolicy.start();

            // Add the rotation policy to the appender
            rollingFileAppender.setRollingPolicy(fixedWindowRollingPolicy);
            rollingFileAppender.setTriggeringPolicy(sizeBasedTriggeringPolicy);
            rollingFileAppender.start();

            // Configure the specific logger
            ch.qos.logback.classic.Logger specificLogger = context.getLogger(loggerName);
            specificLogger.detachAndStopAllAppenders(); 
            specificLogger.addAppender(rollingFileAppender);
            specificLogger.setLevel(ch.qos.logback.classic.Level.TRACE);
            specificLogger.setAdditive(false);
        } else
            registerLog(loggerName, filePath, fileName, maxIndex);
    }
    
    private boolean isValidFileSize(String maxFileSize) {
        String regex = "^[0-9]+[kKmMgG][bB]$";
        
        if (!maxFileSize.matches(regex)) 
            return false;
        
        
        try {
            FileSize.valueOf(maxFileSize);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
    
    /**
    * According to the log level type sends messages to the log.
    */
    private void sendMessage(LoggerType type, Logger logger, String message) {
        switch (type) {
            case WARN:
                logger.warn(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case TRACE:
                logger.trace(message);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    /**
    * According to the log level type sends  error messages to the log.
    */
    private void sendMessageThrowable(LoggerType type, Logger logger, String message, Throwable ex) {
        switch (type) {
            case WARN:
                logger.warn(message, ex);
                break;
            case DEBUG:
                logger.debug(message, ex);
                break;
            case ERROR:
                logger.error(message, ex);
                break;
            case INFO:
                logger.info(message, ex);
                break;
            case TRACE:
                logger.trace(message, ex);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    /**
     * Sets the Logger to use.
     * If not found, create a new one.
    */
    private Logger getLogger(Class source) {
        if(loggers.containsKey(source.getName()))
            return loggers.get(source.getName());
        org.slf4j.Logger newLogger = LoggerFactory.getLogger(source.getName());
        loggers.put(source.getName(), newLogger);
        return newLogger;
    }
    
}


