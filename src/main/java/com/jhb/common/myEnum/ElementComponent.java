package com.jhb.common.myEnum;

import com.jhb.develop.vo.SelectVo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum ElementComponent {
    SELECT("下拉框", "el-select", null, "select"),
    INPUT("文本输入框", "el-input", null, "input"),
    DATE_PICKER("日期选择器", "el-date-picker", "date", "date"),
    DATE_TIME_PICKER("时间选择器", "el-date-picker", "datetime", "datetime");;
    private final String componentName;
    private final String componentType;
    private final String type;
    private final String value;

    private static final List<SelectVo<String, String>> selectVoList = new ArrayList<>();
    private static final Map<String, ElementComponent> valueToElementComponent = new HashMap<>();

    static {
        for (ElementComponent elementComponent : ElementComponent.values()) {
            SelectVo<String, String> selectVo = new SelectVo<>();
            selectVo.setLabel(elementComponent.getComponentName());
            selectVo.setValue(elementComponent.getValue());
            selectVoList.add(selectVo);
            valueToElementComponent.put(elementComponent.getValue(), elementComponent);
        }
    }

    ElementComponent(String componentName, String componentType, String type, String value){
        this.componentName = componentName;
        this.componentType = componentType;
        this.type = type;
        this.value = value;
    }

    public static List<SelectVo<String, String>> getSelectVoList(){
        return selectVoList;
    }

    public static ElementComponent getByValue(String value){
        return valueToElementComponent.get(value);
    }
}
