package com.jhb.common.myEnum;

import lombok.Getter;

@Getter
public enum JavaField {
    Integer("整形", "Integer"),
    Long("长整形", "Long"),
    Boolean("布尔", "Boolean"),
    String("字符串", "String"),
    LocalDateTime("时间", "LocalDateTime");

    private final String javaTypeName;
    private final String javaType;

    JavaField(String javaTypeName, String javaType){
        this.javaTypeName = javaTypeName;
        this.javaType = javaType;
    }
}
