package cn.garden.generate;

import cn.garden.util.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class TestTags {

    @Test
    public void hasTag() {
        Tags tags = new Tags(List.of("mysql"));
        assertTrue(tags.hasTag("mysql"));
    }
}
