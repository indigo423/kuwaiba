/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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
package com.neotropic.flow.component.paper.dialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

/**
 * <paper-dialog>
 *  <h2></h2>
 *  <!--Content-->
 *  <div class="buttons"></div>
 * </paper-dialog>
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
@Tag("paper-dialog")
@JsModule("@polymer/paper-dialog/paper-dialog.js")
@NpmPackage(value = "@polymer/paper-dialog", version = "^3.0.1")
public class PaperDialog extends Component implements HasComponents, HasSize, HasStyle {
    public PaperDialog() {
    }
    public void dialogConfirm(Component component) {
        component.getElement().setAttribute(Attribute.DIALOG_CONFIRM.toString(), true);
    }
    public void dialogDismiss(Component component) {
        component.getElement().setAttribute(Attribute.DIALOG_DISMISS.toString(), true);
    }
    public void open() {
        getElement().executeJs("this.open()"); //NOI18N
    }
    public void close() {
        getElement().executeJs("this.close()"); //NOI18N
    }
    /**
     * Opens the dialog at the left of the given component
     * @param in order to open the dialog in place we need it id to to set its top and right
     * @param positionTarget the target component to open the dialog
     * @param relative if we are open a relative or in place
     */
    public void open(String paperDialogId, Component positionTarget, boolean relative){
        if(relative)
            getElement().executeJs("document.getElementById($0).style.position='absolute';"
                + "console.log('>>> offsetTop' + $1.offsetTop);" 
                + "console.log('>>> offsetWidth' + $1.offsetWidth);" 
                + "document.getElementById($0).style.top= $1.offsetTop + 'px';"
                + "var w = $1.offsetLeft + $1.offsetWidth;"
                + "document.getElementById($0).style.left= w + 'px'", paperDialogId, positionTarget); //NOI18N
        else
            getElement().executeJs("document.getElementById($0).style.position='absolute';"
                + "console.log('>>> getBoundingClientRect top' + $1.getBoundingClientRect.top);" 
                + "console.log('>>> getBoundingClientRect right' + $1.getBoundingClientRect().left);" 
                + "document.getElementById($0).style.top= ($1.getBoundingClientRect().top + 5) + 'px';"
                + "document.getElementById($0).style.left= ($1.getBoundingClientRect().left + 5) + 'px'", paperDialogId, positionTarget); //NOI18N
        getElement().executeJs("this.open()"); //NOI18N
    }
    
    public void positionTarget(Component positionTarget) {
        getElement().executeJs("this.positionTarget = $0", positionTarget); //NOI18N
    }
    public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
        getElement().setProperty(Property.HORIZONTAL_ALIGN, horizontalAlign.align());
    }
    public void setModal(boolean modal) {
        getElement().setProperty(Property.MODAL, modal);
    }
    public void setNoOverlap(boolean noOverlap) {
        getElement().setProperty(Property.NO_OVERLAP, noOverlap);
    }
    public void setVerticalAlign(VerticalAlign verticalAlign) {
        getElement().setProperty(Property.VERTICAL_ALIGN, verticalAlign.align());
    }
    public void setMargin(boolean margin) {
        if (!margin)
            getElement().getStyle().set(Attribute.MARGIN.toString(), "0");
    }
    public void setAllowClickThrough(boolean allowClickThrough) {
        getElement().setProperty(Property.ALLOW_CLICK_THROUGH, allowClickThrough);
    }
    public void setNoCancelOnEscKey(boolean noCancelOnEscKey) {
        getElement().setProperty(Property.NO_CANCEL_ON_ESC_KEY, noCancelOnEscKey);
    }
    public void setNoCancelOnOutsideClick(boolean noCancelOnOutsideClick) {
        getElement().setProperty(Property.NO_CANCEL_ON_OUTSIDE_CLICK, noCancelOnOutsideClick);
    }

    public class Property {
        public static final String HORIZONTAL_ALIGN = "horizontalAlign"; //NOI18N
        public static final String MODAL = "modal"; //NOI18N
        public static final String NO_OVERLAP = "noOverlap"; //NOI18N
        public static final String VERTICAL_ALIGN = "verticalAlign"; //NOI18N
        public static final String ALLOW_CLICK_THROUGH = "allowClickThrough"; //NOI18N
        public static final String NO_CANCEL_ON_ESC_KEY = "noCancelOnEscKey"; //NOI18N
        public static final String NO_CANCEL_ON_OUTSIDE_CLICK = "noCancelOnOutsideClick"; //NOI18N
    }
    public enum HorizontalAlign {
        LEFT("left"), //NOI18N
        RIGHT("right"); //NOI18N
        private final String align;
        private HorizontalAlign(String align) {
            this.align = align;
        }
        public String align() {
            return align;
        }
    }
    public enum VerticalAlign {
        TOP("top"), //NOI18N
        BOTTOM("bottom"); //NOI18N
        private final String align;
        private VerticalAlign(String align) {
            this.align = align;
        }
        public String align() {
            return align;
        }
    }
    private enum Attribute {
        DIALOG_DISMISS {
            @Override
            public String toString() {
                return "dialog-dismiss"; //NOI18N
            }
        },
        DIALOG_CONFIRM {
            @Override
            public String toString() {
                return "dialog-confirm"; //NOI18N
            }
        },
        MARGIN {
            @Override
            public String toString() {
                return "margin"; //NOI18N
            }
        };
    }
    public enum Clazz {
        BUTTONS("buttons"); //NOI18N
        private final String clazz;
        private Clazz(String clazz) {
            this.clazz = clazz;
        }
        public String clazz() {
            return clazz;
        }
    }
}
