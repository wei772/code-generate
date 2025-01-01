package cn.garden.generate.template.factory;

import cn.garden.generate.template.TemplateEngine;
import cn.garden.generate.template.enums.TemplateEngineEnum;
import cn.garden.generate.template.implementation.VelocityTemplateEngine;
import cn.garden.generate.util.ExceptionUtil;

/**
 * 模板引擎工厂方法
 *
 * @author liwei
 */
public class TemplateEngineFactory {

    public static TemplateEngine create(String name) {
        TemplateEngineEnum templateEngineEnum = TemplateEngineEnum.getEnum(name);
        if (templateEngineEnum == TemplateEngineEnum.VELOCITY) {
            return new VelocityTemplateEngine();
        }

        throw ExceptionUtil.createDefaultException("找不到模板引擎" + name);
    }
}
