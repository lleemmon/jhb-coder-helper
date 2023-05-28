package com.jhb.developUtil;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.jhb.common.BasePath;
import com.jhb.config.spring.SpringConfig;
import com.jhb.develop.vo.TableFieldVo;
import com.jhb.util.MyFileUtil;
import com.jhb.util.MyStrUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CodeGenerator {
    @Resource
    private SpringConfig springConfig;
    @Resource(name = "templateVelocityEngine")
    private VelocityEngine velocityEngine;

    private static CodeGenerator instance;
    private DataSourceConfig dsc;

    @PostConstruct
    public void init(){
        instance = this;
        instance.springConfig = this.springConfig;
        instance.velocityEngine = this.velocityEngine;
        instance.dsc = new DataSourceConfig();
        dsc.setUrl(instance.springConfig.getDatasourceUrl());
        dsc.setDriverName(instance.springConfig.getDatasourceDriverClassName());
        dsc.setUsername(instance.springConfig.getDatasourceUsername());
        dsc.setPassword(instance.springConfig.getDatasourcePassword());
        dsc.setDbType(DbType.MYSQL);
    }

    //本项目专用的代码生成
    public static void execute(String moduleName, String author, String tableName){
        //删除相关文件
        String fullParentPackageDir = BasePath.FULL_PARENT_PACKAGE_DIR;
        String className = MyStrUtil.ab_cToAbC(tableName);
        String[] filePathList = {"\\vo\\" + className + "Vo.java", "\\entity\\"+ className + ".java",
                "\\controller\\" + className + "Controller.java", "\\mapper\\" + className + "Mapper.java",
                "\\mapper\\xml\\" + className + "Mapper.xml", "\\service\\" + className + "Service.java",
                "\\service\\impl\\" + className + "ServiceImpl.java"
        };
        for (String s : filePathList) {
            File file = new File(fullParentPackageDir + (StrUtil.isBlank(moduleName) ? "" : "\\")
                    + moduleName + s);
            if (file.exists()) {
                file.delete();
            }
        }

        AutoGenerator mpg = new AutoGenerator();
        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(BasePath.ROOT_FULL_DIR);
        gc.setServiceName("%sService");	//去掉Service接口的首字母I
        gc.setAuthor(author);
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        mpg.setDataSource(instance.dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(BasePath.PARENT_PACKAGE_DIR);
        pc.setModuleName(moduleName); //模块名
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tableName);
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
//    List<TableFieldVo> tableColumns = developService.getTableFieldListForJava(tableName);
    //通过表名生成详细的controller
    public static void generatorCrudController(String tableName, String author, String moduleName, List<TableFieldVo> tableColumns) throws IOException {
        String className = MyStrUtil.ab_cToAbC(tableName);
        String fieldName = MyStrUtil._aToA(tableName);
        String apiName = MyStrUtil.ab_cToab__c(tableName);
        String templateName = "ControllerDevelop.java.vm";
        String outDirPath = BasePath.FULL_PARENT_PACKAGE_DIR + "\\";
        String moduleNamePoint = moduleName;
        String moduleName_ = moduleName;
        if(StrUtil.isNotBlank(moduleName)){
            outDirPath = outDirPath + moduleName + "\\controller";
            moduleName_ = "/" + moduleName_;
            moduleNamePoint = moduleNamePoint + ".";
        }else{
            outDirPath = outDirPath + "controller";
        }
        MyFileUtil.checkAndMkdirs(outDirPath);
        String outFilePath = outDirPath + "\\" + className + "Controller.java";
        Map<String, Object> controllerParams = new HashMap<>();
        controllerParams.put("apiName", apiName);
        controllerParams.put("tableName", tableName);
        controllerParams.put("className", className);
        controllerParams.put("fieldName", fieldName);
        controllerParams.put("rootPackage", BasePath.PARENT_PACKAGE_DIR);
        controllerParams.put("moduleName_", moduleName_);
        controllerParams.put("moduleNamePoint", moduleNamePoint);
        controllerParams.put("author", author);
        controllerParams.put("createTime", new Date());
        controllerParams.put("tableColumns", tableColumns);
        //生成Controller.java
        buildFile(templateName, outFilePath, controllerParams);
        //生成vo
        generatorPageVo(moduleName, className);
    }

    //生成entity类
    public static void generatorEntity(String moduleName, String className, List<TableFieldVo> tableColumns) throws IOException {
        String templateName = "EntityDevelop.java.vm";
        String outDirPath = BasePath.FULL_PARENT_PACKAGE_DIR + "\\" + moduleName + "\\entity";
        MyFileUtil.checkAndMkdirs(outDirPath);
        String outFilePath = outDirPath + "\\" + className + ".java";
        Map<String, Object> params = new HashMap<>();
        if(StrUtil.isNotBlank(moduleName)){
            moduleName += '.';
        }
        params.put("className", className);
        params.put("rootPackage", BasePath.PARENT_PACKAGE_DIR);
        params.put("moduleName", moduleName);
        params.put("tableColumns", tableColumns);
        //生成Vo.java
        buildFile(templateName, outFilePath, params);
    }

    //生成vo类
    private static void generatorPageVo(String moduleName, String className) throws IOException {
        String templateName = "EntityVoDevelop.java.vm";
        String outDirPath = BasePath.FULL_PARENT_PACKAGE_DIR + "\\" + moduleName + "\\vo";
        MyFileUtil.checkAndMkdirs(outDirPath);
        String outFilePath = outDirPath + "\\" + className + "Vo.java";
        Map<String, Object> voParams = new HashMap<>();
        if(StrUtil.isNotBlank(moduleName)){
            moduleName += '.';
        }
        voParams.put("className", className);
        voParams.put("rootPackage", BasePath.PARENT_PACKAGE_DIR);
        voParams.put("moduleName", moduleName);
        //生成Vo.java
        buildFile(templateName, outFilePath, voParams);
    }

    //生成前端代码文件
    public static void generatorWebFile(List<TableFieldVo> tableColumns, String tableName, String moduleName) throws IOException {
        String apiName = MyStrUtil.ab_cToab__c(tableName);
        String fieldName = MyStrUtil._aToA(tableName);
        generatorJs(apiName, fieldName, moduleName);
        generatorTableVue(tableColumns, fieldName, moduleName);
        generatorFormVue(tableColumns, fieldName, moduleName);
    }

    //生成js文件
    private static void generatorJs(String apiName, String fieldName, String moduleName) throws IOException {
        String templateName = "ApiJsDevelop.js.vm";
        String outDirPath = instance.springConfig.webProjectPath + "\\src\\api\\";
        Map<String, Object> jsParams = new HashMap<>();
        if(StrUtil.isNotBlank(moduleName)){
            outDirPath = outDirPath + moduleName + "\\" + fieldName;
            jsParams.put("pathOffset", "../");
            moduleName = "/" + moduleName;
        }else{
            outDirPath = outDirPath + fieldName;
            jsParams.put("pathOffset", "");
        }
        MyFileUtil.checkAndMkdirs(outDirPath);
        String outFilePath = outDirPath + "\\index.js";
        jsParams.put("apiName", apiName);
        jsParams.put("moduleName", moduleName);
        //生成index.js
        buildFile(templateName, outFilePath, jsParams);
    }

    //生成Vue表格文件
    private static void generatorTableVue(List<TableFieldVo> tableColumns, String fieldName, String moduleName) throws IOException {
        String templateName = "VueDevelop.vue.vm";
        String outDirPath = instance.springConfig.webProjectPath + "\\src\\views\\";
        Map<String, Object> params = new HashMap<>();
        List<String> dictTypes = tableColumns.stream().filter(tableColumn -> "el-select".equals(tableColumn.getComponent()) && StrUtil.isNotBlank(tableColumn.getType())).
                map(TableFieldVo::getType).distinct().collect(Collectors.toList());
        if(StrUtil.isNotBlank(moduleName)){
            outDirPath = outDirPath + moduleName + "\\" + fieldName;
            params.put("pathOffset", "../");
            moduleName = "/" + moduleName;
        }else{
            outDirPath = outDirPath + fieldName;
            params.put("pathOffset", "");
        }
        MyFileUtil.checkAndMkdirs(outDirPath);
        String outFilePath = outDirPath + "\\index.vue";
        params.put("fieldName", fieldName);
        params.put("moduleName", moduleName);
        params.put("tableColumns", tableColumns);
        params.put("dictTypes", dictTypes);
        //生成index.vue
        buildFile(templateName, outFilePath, params);
    }

    //生成Vue表单文件
    private static void generatorFormVue(List<TableFieldVo> tableColumns, String fieldName, String moduleName) throws IOException {
        String templateName = "VueDialogFormDevelop.vue.vm";
        String outDirPath = instance.springConfig.webProjectPath + "\\src\\views\\";
        Map<String, Object> params = new HashMap<>();
        List<String> dictTypes = tableColumns.stream().filter(tableColumn -> "el-select".equals(tableColumn.getComponent()) && StrUtil.isNotBlank(tableColumn.getType())).
                map(TableFieldVo::getType).distinct().collect(Collectors.toList());
        if(StrUtil.isNotBlank(moduleName)){
            outDirPath = outDirPath + moduleName + "\\" + fieldName;
            params.put("pathOffset", "../");
            moduleName = "/" + moduleName;
        }else{
            outDirPath = outDirPath + fieldName;
            params.put("pathOffset", "");
        }
        MyFileUtil.checkAndMkdirs(outDirPath);
        String outFilePath = outDirPath + "\\formDialog.vue";
        params.put("fieldName", fieldName);
        params.put("moduleName", moduleName);
        params.put("tableColumns", tableColumns);
        params.put("dictTypes", dictTypes);
        //生成formDialog.vue
        buildFile(templateName, outFilePath, params);
    }

    //生成代码文件
    private static void buildFile(String templateName, String outFilePath, Map<String, Object> voParams) throws IOException {
        VelocityContext context = new VelocityContext(voParams);
        Template tpl = instance.velocityEngine.getTemplate(templateName, "UTF-8");
        Writer Writer = new PrintWriter(Files.newOutputStream(new File(outFilePath).toPath()));
        tpl.merge(context, Writer);
        Writer.flush();
        Writer.close();
    }
}
