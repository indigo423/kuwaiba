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
package org.neotropic.kuwaiba.core.notifications.exceptions;

/**
 * Exception thrown when a notification process cannot be performed in the notification module.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public class NotificationException extends Exception {
    /**
     * Used as exception key and I18N key prefix. The exception key and I18N key has a prefix and code. Example [prefix].[code] = api.bem.error.1234
     */
    private String prefix;
    /**
     * Used as exception key and I18N key code. The exception key and I18N key has a prefix and code. Example [prefix].[code] = api.bem.error.1234
     */
    private int code;
    /**
     * Set of I18N key arguments. Example api.bem.error.1234=Error %s then i18nKeyArgs=["message"]
     */
    private Object[] messageArgs = new Object[0];

    public NotificationException() {    }
    
    public NotificationException(String msg) {
        super (msg);
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public Object[] getMessageArgs() {
        return messageArgs;
    }
    
    public void setMessageArgs(Object... messageArgs) {
        this.messageArgs = messageArgs;
    }
    
    public String getMessageKey() {
        return String.format("%s.%s", prefix, code);
    }
}
