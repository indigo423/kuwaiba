/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.neotropic.util.visual.colorpicker;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import elemental.json.Json;
import elemental.json.JsonArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag("paper-swatch-picker")
@JsModule("@polymer/paper-swatch-picker/paper-swatch-picker.js")
@NpmPackage(value = "@polymer/paper-swatch-picker", version = "3.0.1")
@JavaScript("https://unpkg.com/web-animations-js@^2.0.0/web-animations-next-lite.min.js")
/**
 * Polymer web Components to pick colors
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class ColorPicker extends AbstractSinglePropertyField<ColorPicker, String> {

    public ColorPicker() {
        super("color", "", false);
    }
    /**
     * default list color
     */
    private List<String> colorList =  new ArrayList( Arrays.asList(
      "#ffebee", "#ffcdd2", "#ef9a9a", "#e57373", "#ef5350", "#f44336",
      "#e53935", "#d32f2f", "#c62828", "#b71c1c", "#fce4ec", "#f8bbd0",
      "#f48fb1", "#f06292", "#ec407a", "#e91e63", "#d81b60", "#c2185b",
      "#ad1457", "#880e4f", "#f3e5f5", "#e1bee7", "#ce93d8", "#ba68c8",
      "#ab47bc", "#9c27b0", "#8e24aa", "#7b1fa2", "#6a1b9a", "#4a148c",
      "#ede7f6", "#d1c4e9", "#b39ddb", "#9575cd", "#7e57c2", "#673ab7",
      "#5e35b1", "#512da8", "#4527a0", "#311b92", "#e8eaf6", "#c5cae9",
      "#9fa8da", "#7986cb", "#5c6bc0", "#3f51b5", "#3949ab", "#303f9f",
      "#283593", "#1a237e", "#e3f2fd", "#bbdefb", "#90caf9", "#64b5f6",
      "#42a5f5", "#2196f3", "#1e88e5", "#1976d2", "#1565c0", "#0d47a1",
      "#e1f5fe", "#b3e5fc", "#81d4fa", "#4fc3f7", "#29b6f6", "#03a9f4",
      "#039be5", "#0288d1", "#0277bd", "#01579b", "#e0f7fa", "#b2ebf2",
      "#80deea", "#4dd0e1", "#26c6da", "#00bcd4", "#00acc1", "#0097a7",
      "#00838f", "#006064", "#e0f2f1", "#b2dfdb", "#80cbc4", "#4db6ac",
      "#26a69a", "#009688", "#00897b", "#00796b", "#00695c", "#004d40",
      "#e8f5e9", "#c8e6c9", "#a5d6a7", "#81c784", "#66bb6a", "#4caf50",
      "#43a047", "#388e3c", "#2e7d32", "#1b5e20", "#f1f8e9", "#dcedc8",
      "#c5e1a5", "#aed581", "#9ccc65", "#8bc34a", "#7cb342", "#689f38",
      "#558b2f", "#33691e", "#f9fbe7", "#f0f4c3", "#e6ee9c", "#dce775",
      "#d4e157", "#cddc39", "#c0ca33", "#afb42b", "#9e9d24", "#827717",
      "#fffde7", "#fff9c4", "#fff59d", "#fff176", "#ffee58", "#ffeb3b",
      "#fdd835", "#fbc02d", "#f9a825", "#f57f17", "#fff8e1", "#ffecb3",
      "#ffe082", "#ffd54f", "#ffca28", "#ffc107", "#ffb300", "#ffa000",
      "#ff8f00", "#ff6f00", "#fff3e0", "#ffe0b2", "#ffcc80", "#ffb74d",
      "#ffa726", "#ff9800", "#fb8c00", "#f57c00", "#ef6c00", "#e65100",
      "#fbe9e7", "#ffccbc", "#ffab91", "#ff8a65", "#ff7043", "#ff5722",
      "#f4511e", "#e64a19", "#d84315", "#bf360c", "#efebe9", "#d7ccc8",
      "#bcaaa4", "#a1887f", "#8d6e63", "#795548", "#6d4c41", "#5d4037",
      "#4e342e", "#3e2723", "#fafafa", "#f5f5f5", "#eeeeee", "#e0e0e0",
      "#bdbdbd", "#9e9e9e", "#757575", "#616161", "#424242", "#212121") );
    
    public JsonArray getColorList() {
        return (JsonArray) getElement().getPropertyRaw("colorList");
    }
    
    public void setColorList() {
        JsonArray json = Json.createArray();
        
        for (int i = 0; i < colorList.size(); i++) {
            json.set(i, colorList.get(i));
        }
                
        getElement().setPropertyJson("colorList", json);
    }
    
    public void getDefaultColors() {
       getElement().executeJs("this.defaultColors()").then(JsonArray.class, res -> {
             System.out.println(
                            "Feature is supported");
            });       
        }
    
    @Override
    public void onAttach(AttachEvent ev) {
        
    }

    @Override
    public void setValue(String value) {
        super.setValue(value); 
        if (!colorList.contains(value)) {
            colorList = new ArrayList(colorList); //Arrays.asList creates a list inmutable
            colorList.add(0, value);
            setColorList();
        }
    }
    
    
    
    
    


}
