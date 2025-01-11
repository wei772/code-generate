package cn.garden.generate.template.factory;

import cn.garden.generate.template.TemplateEngine;
import cn.garden.generate.template.TemplateEngineType;
import cn.garden.generate.template.implementation.VelocityTemplateEngine;
import cn.garden.generate.util.ExceptionUtil;

/**
 * 模板引擎工厂方法
 *
 * @author liwei
 */
public class TemplateEngineFactory {

    public static TemplateEngine create(String name) {
        TemplateEngineType templateEngineEnum = TemplateEngineType.of(name);
        if (templateEngineEnum == TemplateEngineType.VELOCITY) {
            return new VelocityTemplateEngine();
        }

        throw ExceptionUtil.createDefaultException("找不到模板引擎" + name);
    }
}
