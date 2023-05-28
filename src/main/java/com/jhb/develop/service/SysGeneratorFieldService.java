package com.jhb.develop.service;

import com.jhb.develop.entity.SysGeneratorField;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhb.develop.vo.FrontGeneratorVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 江辉彬
 * @since 2023-04-09
 */
public interface SysGeneratorFieldService extends IService<SysGeneratorField> {
    boolean doSaveGeneratorConfig (FrontGeneratorVo frontGeneratorVo);
}
