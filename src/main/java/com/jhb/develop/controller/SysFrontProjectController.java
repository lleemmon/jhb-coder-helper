package com.jhb.develop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhb.common.result.Result;
import com.jhb.common.result.ResultCodeEnum;
import com.jhb.config.execption.MyException;
import com.jhb.develop.entity.SysFrontProject;
import com.jhb.develop.service.SysFrontProjectService;
import com.jhb.develop.vo.SysFrontProjectVo;
import com.jhb.util.MyIdUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 江辉彬
 * @since Mon Apr 10 01:46:31 CST 2023
 */
@RestController
@RequestMapping("/develop/sys-front-project")
public class SysFrontProjectController {
    @Resource
    private SysFrontProjectService sysFrontProjectService;

    @GetMapping("/get/{id}")
    public Result<SysFrontProject> get(@PathVariable String id){
        return Result.ok(sysFrontProjectService.getById(id));
    }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody SysFrontProject sysFrontProject){
        String id = MyIdUtil.nextIdStr();
        sysFrontProject.setCreateTime(LocalDateTime.now());
        sysFrontProject.setId(id);
        sysFrontProject.setIsDeleted(false);
        boolean isSuccess = sysFrontProjectService.save(sysFrontProject);
        if(isSuccess){
            return Result.ok(id);
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody SysFrontProject sysFrontProject){
        sysFrontProject.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = sysFrontProjectService.updateById(sysFrontProject);
        if(isSuccess){
            return Result.ok("修改成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/delete")
    public Result<String> delete(String id){
        boolean isSuccess = sysFrontProjectService.removeById(id);
        if(isSuccess){
            return Result.ok("删除成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_DELETE_FAIL);
        }
    }

    @PostMapping("/page")
    public Result<Page<SysFrontProject>> page(@RequestBody SysFrontProjectVo sysFrontProjectVo){
        Page<SysFrontProject> sysFrontProjectPage = new Page<>(sysFrontProjectVo.getPage(), sysFrontProjectVo.getLimit());
        return Result.ok(sysFrontProjectService.page(sysFrontProjectPage));
    }
}