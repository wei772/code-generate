package cn.garden.entity;

import cn.garden.entity.enums.LanguageEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实体类
 *
 * @author liwei
 */
public class Entity implements Name {

    /**
     * 原始名称
     */
    private String sourceName;

    /**
     * 属性集合
     */
    private List<Property> properties = new ArrayList<>();

    /**
     * 描述
     */
    private String description = StringUtils.EMPTY;

    private LanguageEnum targetLanguage;

    public Entity() {
    }

    public Entity(String sourceName) {
        this.sourceName = sourceName;
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


    public List<Property> getProperties() {
        return properties;
    }

    /**
     * 为了能顺利反json序列化才添加的，没有啥好办法
     */
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public Boolean hasProperty(String sourceName) {
        return Objects.nonNull(getProperty(sourceName));
    }

    public void addProperty(Property property) {
        if (Objects.isNull(property)) {
            return;
        }
        property.setTargetLanguage(targetLanguage);
        properties.add(property);
    }

    public Property getProperty(String sourceName) {
        return properties
                .stream()
                .filter(m -> StringUtils.equals(sourceName, m.getSourceName()))
                .findFirst()
                .orElse(null);
    }

    public LanguageEnum getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(LanguageEnum targetLanguage) {
        this.targetLanguage = targetLanguage;

        for (Property property : properties) {
            property.setTargetLanguage(targetLanguage);
        }
    }

    public List<String> getImportStrings() {
        return properties
                .stream()
                .map(Property::getImportString)
                .distinct()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }
}
