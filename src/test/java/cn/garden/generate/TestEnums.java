package cn.garden.generate;

import cn.garden.generate.enums.GeneratePropertyEnum;
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
        long count = Arrays.stream(GeneratePropertyEnum.values())
                .map(GeneratePropertyEnum::getName)
                .distinct().count();
        assertEquals(GeneratePropertyEnum.values().length, count);

    }

    /**
     * 命令行参数名称重复会有大问题，所以需要校验一下
     */
    @Test
    public void checkGeneratePropertyEnumDuplicateName() {
        long count = Arrays.stream(GeneratePropertyEnum.values())
                .map(GeneratePropertyEnum::getName)
                .distinct().count();
        assertEquals(GeneratePropertyEnum.values().length, count);

    }
}
