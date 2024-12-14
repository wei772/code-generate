package cn.garden.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class TestStringUtil {

    @Test
    public void getEmptyLines() {
        List<String> lines = StringUtil.getLines(null);
        assertTrue(CollectionUtils.isEmpty(lines));
    }

    @Test
    public void joinEmptyLines() {
        String line = StringUtil.joinLine(null);
        assertTrue(StringUtils.isEmpty(line));
    }
}
