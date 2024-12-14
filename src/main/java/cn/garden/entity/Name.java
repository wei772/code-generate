package cn.garden.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 名称转换接口类
 */
public interface Name {

    String NAME_SPLIT = "_";

    private static String convertSplitName(String name) {
        if (StringUtils.contains(name, NAME_SPLIT)) {
            String[] splitNames = StringUtils.split(name, NAME_SPLIT);
            name = Arrays.stream(splitNames).
                    map(StringUtils::capitalize).
                    collect(Collectors.joining());
        }
        return name;
    }

    String getSourceName();

    default String getCamelName() {
        String name = getSourceName();
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        name = convertSplitName(name);
        return StringUtils.uncapitalize(name);
    }

    default String getPascalName() {
        String name = getSourceName();
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        name = convertSplitName(name);
        return StringUtils.capitalize(name);
    }
}
