package com.jhb.develop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 江辉彬
 * @since 2023-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysGeneratorField implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    @TableField("IS_DELETED")
    private Boolean isDeleted;

    @TableField("TABLE_ID")
    private String tableId;

    @TableField("TABLE_NAME")
    private String tableName;

    @TableField("COLUMN_NAME")
    private String columnName;

    @TableField("REMARKS")
    private String remarks;

    @TableField("DATA_TYPE")
    private String dataType;

    @TableField("COMPONENT")
    private String component;

    @TableField("NEED_LIST")
    private String needList;

    @TableField("NEED_WHERE")
    private String needWhere;

    @TableField("NEED_FORM")
    private String needForm;

    @TableField("TYPE")
    private String type;
}
