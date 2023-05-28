package com.jhb.develop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhb.develop.entity.SysGeneratorTable;
import com.jhb.develop.vo.SysGeneratorTableQueryVo;
import com.jhb.develop.vo.SelectVo;
import com.jhb.develop.vo.TableFieldVo;

import java.util.List;

public interface DevelopMapper extends BaseMapper<SysGeneratorTable> {
    /**
     * 获取所有可操作的数据表
     * @return
     */
    List<SelectVo<String, String>> getTableNameSelection();

    /**
     * 获取所有可操作的数据表(详细)
     * @return
     */
    List<SysGeneratorTable> getSysGeneratorTableList(SysGeneratorTableQueryVo queryVo);

    List<TableFieldVo> getTableFieldList(String tableName);
}
