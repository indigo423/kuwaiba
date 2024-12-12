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
package org.neotropic.kuwaiba.modules.core.logging;

/**
 * Contains the methods used for logging.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public interface LoggingService {
    
    /**
     * Write entries in the log
     * @param type Type of message (e.g. info, debug)
     * @param source Log message source class
     * @param message Message to be written in the log
     */
    public void writeLogMessage(LoggerType type, Class source, String message);
    
    /**
     * Writes error entries in the log
     * @param type Type of message (e.g. info, debug)
     * @param source Log message source class
     * @param message Message to be written in the log
     * @param ex Exception to be displayed in the log
     */
    public void writeLogMessage(LoggerType type, Class source, String message, Throwable ex);
    
    /**
     * Register a log channel with a log rotation by file size.
     * @param loggerName Log name to be taken into account. 
     * In the case of logback, this parameter defines which elements are included in a specific log according to the following packages
     * @param path Path where the log is saved
     * @param fileName 
     * @param maxFileSize Maximum file size
     * @param maxIndex Number of files saved
     */
    public void registerLog(String loggerName, String path, String fileName, String maxFileSize, int maxIndex);
    
    /**
     * Register a log channel with a rotation of logs per time period.
     * @param loggerName Log name to be taken into account. 
     * @param path In the case of logback, this parameter defines which elements are included in a specific log according to the following packages
     * @param fileName  
     * @param maxIndex
     */
    public void registerLog(String loggerName, String path, String fileName, int maxIndex);
    
}
