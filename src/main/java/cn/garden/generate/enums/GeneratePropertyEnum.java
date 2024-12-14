package cn.garden.generate.enums;

import cn.garden.entity.reader.EntityReader;
import cn.garden.entity.reader.enums.EntityReaderEnum;
import cn.garden.entity.reader.implementation.JdbcFileEntityReader;
import cn.garden.entity.reader.implementation.JsonFileEntityReader;
import cn.garden.generate.implementation.EntityCodeGenerateWork;
import cn.garden.template.TemplateRepository;

/**
 * 代码生成参数枚举
 *
 * @author liwei
 */
public enum GeneratePropertyEnum {

    /**
     * @see TemplateRepository #code  模板仓库编码
     */
    TEMPLATE_REPOSITORY_CODE("template.repository", "模板仓库编码"),


    /**
     * @see EntityCodeGenerateWork #renderToFile  代码生成目标文件夹
     */
    BASE_FOLDER("generate.output", "代码生成目标文件夹"),

    /**
     * 包名(用于代码生成的变量)
     */
    BASE_PACKAGE("generate.basePackage", "包名(用于代码生成的变量)"),


    /**
     * 代码生成作者
     */
    AUTHOR("generate.author", "代码生成作者"),

    /**
     * 标签列表,使用逗号分割多个值(用于代码生成的开关配置)
     */
    TAG("generate.tags", "标签列表,使用逗号分割多个值(用于代码生成的开关配置)"),

//    /**
//     * 目前只支持entity
//     */
//    GENERATE_WORK("generate.work","生成代码工作类,目前只支持entity"),

    /**
     * @see EntityReaderEnum 枚举值  EntityReader名称，目前支持 jsonFile 与jdbc
     */
    ENTITY_READER("entity.reader", "EntityReader名称，目前支持 jsonFile 与jdbc"),

    /**
     * @see EntityReader #names 代码生成目标名称,目前支持实体名称,使用逗号分割多个值
     */
    ENTITY_NAMES("entity.names", "代码生成目标名称,目前支持实体名称,使用逗号分割多个值"),


    /**
     * @see JsonFileEntityReader # JsonFileEntityReader的Json配置文件
     */
    JSON_FILE("entity.reader.json.file", "JsonFileEntityReader的Json配置文件"),

    /**
     * @see JdbcFileEntityReader #  JdbcFileEntityReader的数据库连接
     */
    URL("entity.reader.jdbc.url", "JdbcFileEntityReader的数据库连接"),

    /**
     * @see JdbcFileEntityReader #  JdbcFileEntityReader的数据库用户
     */
    USER("entity.reader.jdbc.user", "JdbcFileEntityReader的数据库用户"),

    /**
     * @see JdbcFileEntityReader #   JdbcFileEntityReader的数据库密码
     */
    PASSWORD("entity.reader.jdbc.password", "JdbcFileEntityReader的数据库密码"),

    ;

    private final String name;

    private final String description;

    GeneratePropertyEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
