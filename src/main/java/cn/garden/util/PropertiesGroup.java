package cn.garden.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Properties集合
 *
 * @author liwei
 */
public class PropertiesGroup {

    private final List<Properties> propertiesList = new ArrayList<>();

    public void addProperties(Properties properties) {
        if (Objects.nonNull(properties) && !properties.isEmpty()) {
            propertiesList.add(properties);
        }
    }

    public String getProperty(String propertyKey) {
        for (Properties property : propertiesList) {
            if (property.containsKey(propertyKey)) {
                String value = property.getProperty(propertyKey);
                if (StringUtils.isEmpty(value)) {
                    return value;
                }
                return StringUtils.trim(value);
            }
        }
        return null;
    }

    public Boolean isEmpty() {
        return CollectionUtils.isEmpty(propertiesList);
    }
}
