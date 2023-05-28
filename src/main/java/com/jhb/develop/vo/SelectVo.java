package com.jhb.develop.vo;

import lombok.Data;

@Data
public class SelectVo<T, V> {
    private T value;
    private V label;
}
