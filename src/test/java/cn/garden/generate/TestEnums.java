package cn.garden.generate;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author liwei
 */
public class TestEnums {

    /**
     * 命令行参数名称重复会有大问题，所以需要校验一下
     */
    @Test
    public void checkGeneratePropertyEnumDuplicateShortName() {
        long count = Arrays.stream(GenerateProperty.values())
                .map(GenerateProperty::getName)
                .distinct().count();
        assertEquals(GenerateProperty.values().length, count);

    }

    /**
     * 命令行参数名称重复会有大问题，所以需要校验一下
     */
    @Test
    public void checkGeneratePropertyEnumDuplicateName() {
        long count = Arrays.stream(GenerateProperty.values())
                .map(GenerateProperty::getName)
                .distinct().count();
        assertEquals(GenerateProperty.values().length, count);

    }
}
