package com.jhb.develop.mapper;

import com.jhb.develop.entity.SysDictItems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhb.develop.vo.SelectVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 江辉彬
 * @since 2023-04-10
 */
public interface SysDictItemsMapper extends BaseMapper<SysDictItems> {
    List<SelectVo<String, String>> getByDictType(String dictType);
}
