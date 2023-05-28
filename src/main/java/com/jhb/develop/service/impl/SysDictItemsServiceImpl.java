package com.jhb.develop.service.impl;

import com.jhb.develop.entity.SysDictItems;
import com.jhb.develop.mapper.SysDictItemsMapper;
import com.jhb.develop.service.SysDictItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhb.develop.vo.SelectVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 江辉彬
 * @since 2023-04-10
 */
@Service
public class SysDictItemsServiceImpl extends ServiceImpl<SysDictItemsMapper, SysDictItems> implements SysDictItemsService {

    @Override
    public List<SelectVo<String, String>> getByDictType(String dictType) {
        return baseMapper.getByDictType(dictType);
    }
}
