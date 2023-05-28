package com.jhb.util;

import com.jhb.common.myEnum.ElementComponent;
import com.jhb.common.myEnum.MysqlField;

import java.util.HashMap;
import java.util.Map;

public class ComponentFieldTransferUtil {
    private static final Map<MysqlField, ElementComponent> mysqlFieldToElementComponent = new HashMap<>();

    static {
        mysqlFieldToElementComponent.put(MysqlField.BIT, ElementComponent.SELECT);
        mysqlFieldToElementComponent.put(MysqlField.DATE, ElementComponent.DATE_PICKER);
        mysqlFieldToElementComponent.put(MysqlField.VARCHAR, ElementComponent.INPUT);
        mysqlFieldToElementComponent.put(MysqlField.DATE_TIME, ElementComponent.DATE_TIME_PICKER);
        mysqlFieldToElementComponent.put(MysqlField.INT, ElementComponent.INPUT);
        mysqlFieldToElementComponent.put(MysqlField.BIGINT, ElementComponent.INPUT);
    }

    public static ElementComponent transferMysqlFieldToElementComponent(MysqlField mysqlField){
        return mysqlFieldToElementComponent.get(mysqlField);
    }
}
