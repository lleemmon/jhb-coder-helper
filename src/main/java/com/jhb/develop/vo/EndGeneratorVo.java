package com.jhb.develop.vo;

import lombok.Data;

@Data
public class EndGeneratorVo {
    private String id;
    private String tableName;
    private String moduleName;
    private String author;
    private String remarks;
    private String needCrudController;
}
