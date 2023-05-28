package com.jhb.develop.vo;

import com.jhb.develop.entity.SysFrontProject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysFrontProjectVo extends SysFrontProject {
    private Integer page;
    private Integer limit;
}