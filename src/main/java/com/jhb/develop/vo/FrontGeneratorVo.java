package com.jhb.develop.vo;

import lombok.Data;

import java.util.List;

@Data
public class FrontGeneratorVo {
    private String author;
    private String moduleName;
    private String tableName;
    private List<TableFieldVo> tableFields;
}
