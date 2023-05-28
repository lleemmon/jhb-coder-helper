package com.jhb.develop.vo;

import com.jhb.develop.entity.SysDict;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictVo extends SysDict {
    private Integer page;
    private Integer limit;
}