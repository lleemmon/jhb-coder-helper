package com.jhb.develop.vo;

import com.jhb.develop.entity.SysGeneratorField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysGeneratorFieldVo extends SysGeneratorField {
    private Integer page;
    private Integer limit;
}