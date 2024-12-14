package cn.garden.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liwei
 */
public class SystemUtil {

    public static Boolean isWindows() {
        String os = System.getProperty("os.name");
        return StringUtils.contains(os, "Windows");
    }
}
