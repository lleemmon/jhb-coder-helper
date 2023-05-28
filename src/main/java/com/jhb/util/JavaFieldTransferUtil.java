package com.jhb.util;

import com.jhb.common.myEnum.JavaField;
import com.jhb.common.myEnum.MysqlField;

import java.util.HashMap;
import java.util.Map;

public class JavaFieldTransferUtil {
    private static final Map<MysqlField, JavaField> mysqlFieldToJavaFieldMap = new HashMap<>();

    static {
        mysqlFieldToJavaFieldMap.put(MysqlField.BIT, JavaField.String);
        mysqlFieldToJavaFieldMap.put(MysqlField.DATE, JavaField.LocalDateTime);
        mysqlFieldToJavaFieldMap.put(MysqlField.VARCHAR, JavaField.String);
        mysqlFieldToJavaFieldMap.put(MysqlField.DATE_TIME, JavaField.LocalDateTime);
        mysqlFieldToJavaFieldMap.put(MysqlField.INT, JavaField.Integer);
        mysqlFieldToJavaFieldMap.put(MysqlField.BIGINT, JavaField.Long);
    }

    public static JavaField transferMysqlFieldToJavaField(MysqlField mysqlField){
        return mysqlFieldToJavaFieldMap.get(mysqlField);
    }
}
