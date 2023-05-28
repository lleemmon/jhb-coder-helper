package com.jhb.develop.vo;

import com.jhb.develop.entity.SysDictItems;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictItemsVo extends SysDictItems {
    private Integer page;
    private Integer limit;
}