package com.jhb.develop.vo;

import lombok.Data;

@Data
public class TableFieldVo {
    private String columnName;//数据库中
    private String fieldName;//项目中
    private String fieldNameUp;//项目中_首字母大写
    private String javaType;//java中的类型
    private String dataType;//数据库类型
    private String component;//组件类型
    private String type;//组件的type参数
    private String id;//字段id
    private String tableId;//表id
    private String needList;//是否展示列表
    private String needWhere;//是否展示查询框
    private String needForm;//是否展示表单
    private String remarks;//备注
    private String isNullable;//是否可为空
}
