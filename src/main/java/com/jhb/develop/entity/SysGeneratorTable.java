package com.jhb.develop.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysGeneratorTable {
    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableLogic
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField(exist = false)
    private String inTable;

    @TableField("table_name")
    private String tableName;

    @TableField("module_name")
    private String moduleName;

    @TableField("is_generator_front")
    private String isGeneratorFront;

    @TableField("is_generator_end")
    private String isGeneratorEnd;

    @TableField("can_generator")
    private String canGenerator;

    @TableField("remarks")
    private String remarks;

    @TableField("author")
    private String author;

    @TableField("need_crud_controller")
    private String needCrudController;
}
