/*
 *  Copyright 2010-2021 Neotropic SAS <contact@neotropic.co>.
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
 
import java.util.List;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Pool;
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.AttributeMetadata;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadata;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;

/**
 * description: Updates the data model for the Outside Plant Module to move kuwaiba from 2.0.x to 2.1.
 * 
 * Updates General configuration variables pool.
 * Updates Widgets configuration variables pool.
 * Updates Outside Plant configuration variables pool.
 * Adds attribute leafover in OptocalLink class.
 * Adds ColorType class.
 * Adds attribute color of type ColorType to class OpticalLink.
 * Updates configuration variable general.maps.provider value to com.neotropic.kuwaiba.modules.commercial.ospman.providers.google.GoogleMapsMapProvider.
 * commitOnExecute: true
 * parameters: none
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */

TaskResult taskResult = new TaskResult();
// Constants
final String configVarsPoolNameGeneral = "General"; //NOI18N
final String configVarNameGeneralMapsApiKey = "general.maps.apiKey"; //NOI18N
final String configVarNameGeneralMapsLenguage = "general.maps.language"; //NOI18N
final String configVarNameGeneralMapsProvider = "general.maps.provider"; //NOI18N

final String configVarsPoolNameWidgets = "Widgets"; //NOI18N
final String configVarNameWidgetsSimplemapCenterLatitude = "widgets.simplemap.centerLatitude"; //NOI18N
final String configVarNameWidgetsSimplemapCenterLongitude = "widgets.simplemap.centerLongitude"; //NOI18N
final String configVarNameWidgetsSimplemapZoom = "widgets.simplemap.zoom"; //NOI18N

final String configVarsPoolNameOutsidePlant = "Outside Plant"; //NOI18N
final String configVarNameMinZoomForLabels = "module.ospman.minZoomForLabels"; //NOI18N
final String configVarNameColorForLabels = "module.ospman.colorForLabels"; //NOI18N
final String configVarNameFontSizeForLabels = "module.ospman.fontSizeForLabels"; //NOI18N
final String configVarNameFillColorForNodeLabels = "module.ospman.fillColorForNodeLabels"; //NOI18N
final String configVarNameFillColorForEdgeLabels = "module.ospman.fillColorForEdgeLabels"; //NOI18N
final String configVarNameFillColorForSelectedNodeLabels = "module.ospman.fillColorForSelectedNodeLabels"; //NOI18N
final String configVarNameFillColorForSelectedEdgeLabels = "module.ospman.fillColorForSelectedEdgeLabels"; //NOI18N

final String classNameOpticalLink = "OpticalLink"; //NOI18N
final String attrValue = "value"; //NOI18N
final String attrColor = "color"; //NOI18N
final String classNameColor = "Color"; //NOI18N

List<Pool> configVarsPools = aem.getConfigurationVariablesPools();
String configVarsPoolIdGeneral = null;
String configVarsPoolIdWidgets = null;
String configVarsPoolIdOutsidePlant = null;

for (Pool configVarsPool : configVarsPools) {
    if (configVarsPoolNameGeneral.equals(configVarsPool.getName()))
        configVarsPoolIdGeneral = configVarsPool.getId();
    if (configVarsPoolNameWidgets.equals(configVarsPool.getName()))
        configVarsPoolIdWidgets = configVarsPool.getId();
    if (configVarsPoolNameOutsidePlant.equals(configVarsPool.getName()))
        configVarsPoolIdOutsidePlant = configVarsPool.getId();
}
// Updating General configuration variables pool.
if (configVarsPoolIdGeneral == null) {
    try {
        configVarsPoolIdGeneral = aem.createConfigurationVariablesPool(configVarsPoolNameGeneral, null);
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variables pool %s", configVarsPoolNameGeneral)));
    } catch (InvalidArgumentException ex) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
else
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variables pool %s already exist", configVarsPoolNameGeneral)));

try {
    aem.getConfigurationVariable(configVarNameGeneralMapsApiKey);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exist", configVarNameGeneralMapsApiKey)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdGeneral, configVarNameGeneralMapsApiKey,
            "The developer key to use the Google Maps API", 
            2, false, "not set");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameGeneralMapsApiKey)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}

try {
    aem.getConfigurationVariable(configVarNameGeneralMapsLenguage);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exist", configVarNameGeneralMapsLenguage)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdGeneral, configVarNameGeneralMapsLenguage,
            "The default language used in maps", 
            2, false, "spanish");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameGeneralMapsLenguage)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}

try {
    aem.getConfigurationVariable(configVarNameGeneralMapsProvider);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exist", configVarNameGeneralMapsProvider)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdGeneral, configVarNameGeneralMapsProvider,
            "Map provider", 
            2, false, "com.neotropic.kuwaiba.modules.commercial.ospman.providers.google.GoogleMapsMapProvider");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameGeneralMapsProvider)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Updating Widgets configuration variables pool.
if (configVarsPoolIdWidgets == null) {
    try {
        configVarsPoolIdWidgets = aem.createConfigurationVariablesPool(configVarsPoolNameWidgets, null);
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variables pool %s", configVarsPoolNameWidgets)));
    } catch (InvalidArgumentException ex) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
else
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variables pool %s already exist", configVarsPoolNameWidgets)));

try {
    aem.getConfigurationVariable(configVarNameWidgetsSimplemapCenterLatitude);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exist", configVarNameWidgetsSimplemapCenterLatitude)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdWidgets, configVarNameWidgetsSimplemapCenterLatitude, 
            "The default center latitude used in the", 
            1, false, "11.8399727");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameWidgetsSimplemapCenterLatitude)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}

try {
    aem.getConfigurationVariable(configVarNameWidgetsSimplemapCenterLongitude);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameWidgetsSimplemapCenterLongitude)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdWidgets, configVarNameWidgetsSimplemapCenterLongitude, 
            "The default center longitude used in the", 
            1, false, "12.8260721");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameWidgetsSimplemapCenterLongitude)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}

try {
    aem.getConfigurationVariable(configVarNameWidgetsSimplemapZoom);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameWidgetsSimplemapZoom)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameWidgetsSimplemapZoom, 
            "The default map zoom used in the", 
            0, false, "3");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameWidgetsSimplemapZoom)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Updating Outside Plant configuration variables pool.
if (configVarsPoolIdOutsidePlant == null) {
    try {
        configVarsPoolIdOutsidePlant = aem.createConfigurationVariablesPool(configVarsPoolNameOutsidePlant, null);
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variables pool %s", configVarsPoolNameOutsidePlant)));
    } catch (InvalidArgumentException ex) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
else
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variables pool %s already exists", configVarsPoolNameOutsidePlant)));

try {
    aem.getConfigurationVariable(configVarNameMinZoomForLabels);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameMinZoomForLabels)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameMinZoomForLabels, 
            "The minimum zoom level for the map when displaying", 
            0, false, "12");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameMinZoomForLabels)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}

try {
    aem.getConfigurationVariable(configVarNameColorForLabels);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameColorForLabels)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameColorForLabels, 
            "The color for the map labels", 
            2, false, "black");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameColorForLabels)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Adding the configuration variable module.ospman.fontSizeForLabels
try {
    aem.getConfigurationVariable(configVarNameFontSizeForLabels);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameFontSizeForLabels)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameFontSizeForLabels,
            "The font size for the map labels", 
            2, false, "12px");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameFontSizeForLabels)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Adding the configuration variable module.ospman.fillColorForNodeLabels
try {
    aem.getConfigurationVariable(configVarNameFillColorForNodeLabels);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameFillColorForNodeLabels)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameFillColorForNodeLabels,
            "The fill color for the map node labels", 
            2, false, "#aaff7f");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameFillColorForNodeLabels)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Adding the configuration variable module.ospman.fillColorForEdgeLabels
try {
    aem.getConfigurationVariable(configVarNameFillColorForEdgeLabels);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameFillColorForEdgeLabels)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameFillColorForEdgeLabels,
            "The fill color for the map edge labels", 
            2, false, "#55aaff");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameFillColorForEdgeLabels)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Adding the configuration variable module.ospman.fillColorForSelectedNodeLabels
try {
    aem.getConfigurationVariable(configVarNameFillColorForSelectedNodeLabels);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameFillColorForSelectedNodeLabels)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameFillColorForSelectedNodeLabels,
            "The fill color for the map selected node labels", 
            2, false, "#55aa00");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameFillColorForSelectedNodeLabels)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Adding the configuration variable module.ospman.fillColorForSelectedEdgeLabels
try {
    aem.getConfigurationVariable(configVarNameFillColorForSelectedEdgeLabels);
    taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The configuration variable %s already exists", configVarNameFillColorForSelectedEdgeLabels)));
} catch (ApplicationObjectNotFoundException ex) {
    try {
        aem.createConfigurationVariable(configVarsPoolIdOutsidePlant, configVarNameFillColorForSelectedEdgeLabels,
            "The fill color for the map selected edge labels", 
            2, false, "#0055ff");
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created configuration variable %s", configVarNameFillColorForSelectedEdgeLabels)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
    }
}
// Adding attribute leafover in OptocalLink class
try {
    ClassMetadata classOpticalLink = mem.getClass(classNameOpticalLink);
    if (!classOpticalLink.hasAttribute(Constants.PROPERTY_LEFTOVER)) {
        AttributeMetadata attributeMetadata = new AttributeMetadata();
        attributeMetadata.setName(Constants.PROPERTY_LEFTOVER);
        attributeMetadata.setType(Constants.DATA_TYPE_BOOLEAN);
        attributeMetadata.setVisible(true);

        mem.createAttribute(classOpticalLink.getId(), attributeMetadata);
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created attribute %s of type %s in class %s", Constants.PROPERTY_LEFTOVER,Constants.DATA_TYPE_BOOLEAN, classNameOpticalLink)));
    }
    else if (!Constants.DATA_TYPE_BOOLEAN.equals(classOpticalLink.getAttribute(Constants.PROPERTY_LEFTOVER).getType()))
        taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The attribute %s of type %s in class %s must be of the type %s", Constants.PROPERTY_LEFTOVER, classOpticalLink.getAttribute(Constants.PROPERTY_LEFTOVER).getType(), classNameOpticalLink, Constants.DATA_TYPE_BOOLEAN)));
    else
        taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The attribute %s of type %s in class %s already exists", Constants.PROPERTY_LEFTOVER, classOpticalLink.getAttribute(Constants.PROPERTY_LEFTOVER).getType(), classNameOpticalLink)));

} catch (InventoryException ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
}
// Adding ColorType class
try {
    mem.getClass(classNameColor);
} catch (MetadataObjectNotFoundException ex) {
    try {
        ClassMetadata classColorType = new ClassMetadata();
        classColorType.setName(classNameColor);
        classColorType.setParentClassName("GenericType"); //NOI18N
        classColorType.setInDesign(false);
        mem.createClass(classColorType);
        taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created class %s", classNameColor)));
    } catch (InventoryException nestedEx) {
        taskResult.getMessages().add(TaskResult.createErrorMessage(nestedEx.getMessage()));
    }
}

try {
    ClassMetadata classColorType = mem.getClass(classNameColor);
    if (!classColorType.hasAttribute(attrValue)) {
        try {
            AttributeMetadata attributeMetadata = new AttributeMetadata();
            attributeMetadata.setName(attrValue);
            attributeMetadata.setType(Constants.DATA_TYPE_STRING);
            attributeMetadata.setVisible(true);
            mem.createAttribute(classColorType.getId(), attributeMetadata);
            taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created attribute %s of type %s in class %s", attrValue, Constants.DATA_TYPE_STRING, classNameColor)));
        } catch (InvalidArgumentException ex) {
            taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
        }
    } else if (!Constants.DATA_TYPE_STRING.equals(classColorType.getAttribute(attrValue).getType())) {
        taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The attribute %s of type %s in class %s must be of the type %s", attrValue, classColorType.getAttribute(attrColor).getType(), classColorType, Constants.DATA_TYPE_STRING)));
    } else {
        taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The attribute %s of type %s in class %s already exists", attrValue, Constants.DATA_TYPE_STRING, classColorType)));
    }
} catch (MetadataObjectNotFoundException ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
}
// Adding attribute color of type ColorType to class OpticalLink
try {
    ClassMetadata classOpticalLink = mem.getClass(classNameOpticalLink);
    if (!classOpticalLink.hasAttribute(attrColor)) {
        try {
            AttributeMetadata attributeMetadata = new AttributeMetadata();
            attributeMetadata.setName(attrColor);
            attributeMetadata.setType(classNameColor);
            attributeMetadata.setVisible(true);
            mem.createAttribute(classOpticalLink.getId(), attributeMetadata);
            taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created attribute %s of type %s in class %s", attrColor, classNameColor, classNameOpticalLink)));
        } catch (InvalidArgumentException ex) {
            taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
        }
    } else if (!classNameColor.equals(classOpticalLink.getAttribute(attrColor).getType())) {
        taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The attribute %s of type %s in class %s must be of the type %s", attrColor, classOpticalLink.getAttribute(attrColor).getType(), classNameOpticalLink, classNameColor)));
    } else {
        taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("The attribute %s of type %s in class %s already exists", attrColor, classNameColor, classNameOpticalLink)));
    }
} catch (MetadataObjectNotFoundException ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
}
// Updating configuration variable general.maps.provider value to com.neotropic.kuwaiba.modules.commercial.ospman.providers.google.GoogleMapsMapProvider
try {
    final String configVarValueGeneralMapsProvider = "com.neotropic.kuwaiba.modules.commercial.ospman.providers.google.GoogleMapsMapProvider";
    aem.updateConfigurationVariable(configVarNameGeneralMapsProvider, Constants.PROPERTY_VALUE, configVarValueGeneralMapsProvider);
    taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Updated the configuration variable %s value to %s", configVarNameGeneralMapsProvider, configVarValueGeneralMapsProvider)));
} catch (InventoryException ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(ex.getMessage()));
}
return taskResult;