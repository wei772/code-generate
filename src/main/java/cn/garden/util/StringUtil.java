package cn.garden.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 字符串工具类
 *
 * @author liwei
 */
public class StringUtil {

    /**
     * 返回字符串所有行
     */
    public static List<String> getLines(String value) {
        if (Objects.isNull(value)) {
            return new ArrayList<>();
        }
        // 支持各个操作系统的换行符
        String[] split = value.split("\r\n|\r|\n");
        return Arrays.asList(split);
    }

    /**
     * 统一使用\n,将所有行合并成一个字符串
     */
    public static String joinLine(List<String> lines) {
        if (CollectionUtils.isEmpty(lines)) {
            return StringUtils.EMPTY;
        }

        return String.join("\n", lines);
    }


}
