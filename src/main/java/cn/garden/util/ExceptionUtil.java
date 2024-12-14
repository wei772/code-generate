package cn.garden.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常工具
 */
public class ExceptionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);

    public static RuntimeException createDefaultException(String message) {
        return createDefaultException(message, null);
    }

    public static RuntimeException createDefaultException(String message, Exception exception) {
        System.out.println();
        LOGGER.warn(message, exception);
        throw new RuntimeException(message, exception);
    }
}
