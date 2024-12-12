/*
 * Copyright 2024 Neotropic SAS.
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
package org.neotropic.kuwaiba.core.persistence.reference.naming.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;

/**
 * Implements the <code>multiple-mirror(a, b)</code> function, 
 * Generates one input port and n output ports between <code>a</code> and <code>b</code>. 
 * <code>a</code> and <code>b</code> must be integers greater or equal to 0
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public class MultipleMirrorPortsFunction extends DynamicSectionFunction {
    public static final String FUNCTION_PATTERN = "multiple-mirror\\([0-9]+,[0-9]+\\)";
    protected int parameter1;
    protected int parameter2;

    protected MultipleMirrorPortsFunction(String functionPattern, String dynamicSection) throws InvalidArgumentException {
        super(functionPattern, dynamicSection);
    }

    public MultipleMirrorPortsFunction(String dynamicSectionFunction) throws InvalidArgumentException {
        this(FUNCTION_PATTERN, dynamicSectionFunction);

        Pattern pattern = Pattern.compile("[0-9]+,[0-9]+");
        Matcher matcher = pattern.matcher(dynamicSectionFunction);
        if (matcher.find()) {
            parameter1 = Integer.parseInt(matcher.group().split(",")[0]);
            parameter2 = Integer.parseInt(matcher.group().split(",")[1]);

            if (parameter1 >= parameter2) {
                throw new InvalidArgumentException("Dynamic section function malformed \"" + dynamicSectionFunction + "\" the parameter " + parameter1 + " greater than or equal to " + parameter2);
            }
        }
    }

    @Override
    public List<String> getPossibleValues() {
        List<String> dynamicSections = new ArrayList();
        dynamicSections.add("001-IN");
        for (int c = parameter1; c <= parameter2; c++) {
            String formattedNumber = String.format("%03d", c);
            dynamicSections.add(formattedNumber + "-OUT");
        }
        return dynamicSections;
    }
}
