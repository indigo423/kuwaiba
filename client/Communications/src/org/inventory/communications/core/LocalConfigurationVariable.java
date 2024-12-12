/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.inventory.communications.core;

/**
 * Local representation of RemoteConfigurationVariable
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class LocalConfigurationVariable {
    /**
     * The variable is an integer
     */
    public final static int TYPE_INTEGER = 0;
    /**
     * The variable is a float
     */
    public final static int TYPE_FLOAT = 1;
    /**
     * The variable is a string
     */
    public final static int TYPE_STRING = 2;
    /**
     * The variable is a boolean
     */
    public final static int TYPE_BOOLEAN = 3;
    /**
     * The variable is a unidimensional array. The format that should be used to store arrays is as follows: (value1,value2,value3,valueN)
     */
    public final static int TYPE_ARRAY = 4;
    /**
     * The variable is a bidimensional array. The format that should be used to store arrays is as follows: [(row1col1, row1col2,... row1colN), (row2col1, row2col2,... row2colN), (rowNcol1, rowNcol2,... rowNcolN)]
     */
    public final static int TYPE_MATRIX = 5;
    /**
     * Variable id
     */
    private long id;
    /**
     * Variable name
     */
    private String name;
    /**
     * Description of the variable
     */
    private String description;
    /**
     * The value of the variable
     */
    private String valueDefinition;
    /**
     * If the value should be masked when rendered, and in the future, when stored (for security reasons, for example)
     */
    private boolean masked;
    /**
     * Variable type. See TYPE_XXX for possible values;
     */
    private int type;

    public LocalConfigurationVariable(long id, String name, String description, String valueDefinition, boolean masked, int type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.masked = masked;
        this.type = type;
        this.valueDefinition = valueDefinition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getMasked() {
        return masked;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return valueDefinition;
    }

    public void setValue(String valueDefinition) {
        this.valueDefinition = valueDefinition;
    }
}
