package cn.garden.external;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class TestSystemProperty {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSystemProperty.class);

    @Test
    public void getOS() {
        String os = System.getProperty("os.name");

        LOGGER.info(os);
        assertNotNull(os);
    }

    @Test
    public void isWindowsSystem() {
        String os = System.getProperty("os.name");

        assertTrue(os.contains("Windows"));
    }

    @Test
    @Disabled
    public void isOtherSystem() {
        String os = System.getProperty("os.name");

        assertFalse(os.contains("Windows"));
    }
}
