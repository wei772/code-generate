package cn.garden.entity;

import cn.garden.entity.enums.LanguageEnum;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.Objects;

/**
 * 属性类
 *
 * @author liwei
 */
public class Property implements Name {

    /**
     * 原始名称
     */
    private String sourceName;

    /**
     * 描述
     */
    private String description = StringUtils.EMPTY;


    /**
     * 实体类型
     */
    private EntityType entityType;

    /**
     * 是否主键
     */
    private Boolean primaryKey;

    private LanguageEnum targetLanguage;

    public Property() {

    }


    public String getType() {
        return entityType.getValue();
    }

    public void setType(String type) {
        this.entityType = new EntityType(type);
        //避免顺序依赖
        this.setTargetLanguage(targetLanguage);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }


    public JDBCType getJdbcType() {
        return entityType.getJdbcType();
    }

    public void setJdbcType(JDBCType jdbcType) {
        this.entityType = new EntityType(jdbcType);
        //避免顺序依赖
        this.setTargetLanguage(targetLanguage);
    }

    public LanguageEnum getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(LanguageEnum targetLanguage) {
        this.targetLanguage = targetLanguage;

        //类型的目标语言与属性的保持一致
        if (Objects.nonNull(entityType)) {
            entityType.setTargetLanguage(targetLanguage);
        }
    }

    public String getImportString() {
        return entityType.getImportString();
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
