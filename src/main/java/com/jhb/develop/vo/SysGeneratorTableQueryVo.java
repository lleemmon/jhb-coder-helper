package com.jhb.develop.vo;

import lombok.Data;

@Data
public class SysGeneratorTableQueryVo {
    private String inTable;
    private String isGeneratorFront;
    private String isGeneratorEnd;
    private String tableName;
    private String moduleName;
    private Integer page;
    private Integer limit;
    private Integer start;
}
