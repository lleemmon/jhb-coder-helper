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
 * @since 2023-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFrontProject implements Serializable {

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

    @TableField("REMARKS")
    private String remarks;

    @TableField("NAME")
    private String name;

    @TableField("NODEJS_VERSION")
    private String nodejsVersion;

    @TableField("START_COMMAND")
    private String startCommand;

    @TableField("PATH")
    private String path;
}
