package com.jhb.develop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jhb.develop.entity.SysGeneratorField;
import com.jhb.develop.entity.SysGeneratorTable;
import com.jhb.develop.mapper.DevelopMapper;
import com.jhb.develop.mapper.SysGeneratorFieldMapper;
import com.jhb.develop.service.SysGeneratorFieldService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhb.develop.vo.FrontGeneratorVo;
import com.jhb.develop.vo.TableFieldVo;
import com.jhb.util.MyIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 江辉彬
 * @since 2023-04-09
 */
@Service
public class SysGeneratorFieldServiceImpl extends ServiceImpl<SysGeneratorFieldMapper, SysGeneratorField> implements SysGeneratorFieldService {
    @Resource
    private DevelopMapper developMapper;

    @Override
    public boolean doSaveGeneratorConfig(FrontGeneratorVo frontGeneratorVo) {
        String tableName = frontGeneratorVo.getTableName();
        List<TableFieldVo> tableFields = frontGeneratorVo.getTableFields();
        LambdaQueryWrapper<SysGeneratorTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysGeneratorTable::getTableName, tableName);
        wrapper.eq(SysGeneratorTable::getIsDeleted, false);
        SysGeneratorTable sysGeneratorTable = developMapper.selectOne(wrapper);
        List<SysGeneratorField>list = new ArrayList<>();
        tableFields.forEach(tableField -> {
            SysGeneratorField sysGeneratorField = new SysGeneratorField();
            BeanUtils.copyProperties(tableField, sysGeneratorField);
            boolean isInsert = StrUtil.isBlank(sysGeneratorField.getId());
            if(isInsert){
                sysGeneratorField.setId(MyIdUtil.nextIdStr());
                sysGeneratorField.setCreateTime(LocalDateTime.now());
                sysGeneratorField.setIsDeleted(false);
                sysGeneratorField.setTableId(sysGeneratorTable.getId());
                sysGeneratorField.setTableName(tableName);
            }else{
                sysGeneratorField.setUpdateTime(LocalDateTime.now());
            }
            list.add(sysGeneratorField);
        });
        return this.saveOrUpdateBatch(list);
    }
}
