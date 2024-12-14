package cn.garden.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author liwei
 */
public class TestResourceUtil {


    @Test
    public void getNotExistResourceToString() {
        assertThrows(RuntimeException.class, () -> ResourceUtil.getResourceToString("entityGenerate/user/userDefinitionNotExist.json"));
    }

    @Test
    public void readAllResourceFileNames() {
        List<String> fileNames = ResourceUtil.readAllResourceFileNames(
                "templateRepository"
        );

        assertTrue(CollectionUtils.isNotEmpty(fileNames));
    }

    @Test
    public void readNotExistAllResourceFileNames() {
        List<String> fileNames = ResourceUtil.readAllResourceFileNames(
                "templateRepositoryNotExist"
        );
        assertTrue(CollectionUtils.isEmpty(fileNames));
    }

    @Test
    public void convertResourceToFullPath() {
        String full = ResourceUtil.convertResourceToFullPath("entityGenerate/userDefinition.json");
        if (SystemUtil.isWindows()) {
            assertTrue(StringUtils.contains(full, ":"));
        } else {
            assertTrue(StringUtils.startsWith(full, "/"));
        }
    }

    @Test
    public void convertNotExistResourceToFullPath() {
        assertThrows(
                RuntimeException.class
                , () -> ResourceUtil.convertResourceToFullPath("entityGenerate/user/userDefinitionNotExist.json"));
    }
}
