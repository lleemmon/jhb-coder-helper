package com.jhb.develop.service;

import com.jhb.develop.entity.SysDictItems;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhb.develop.vo.SelectVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 江辉彬
 * @since 2023-04-10
 */
public interface SysDictItemsService extends IService<SysDictItems> {

    List<SelectVo<String, String>> getByDictType(String dictType);
}
