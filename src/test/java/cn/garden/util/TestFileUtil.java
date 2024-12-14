package cn.garden.util;

import cn.garden.config.FileConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class TestFileUtil {


    private String getUserDefinition() {
        String userDefinition = ResourceUtil.convertResourceToFullPath("entityGenerate/userDefinition.json");
        return Paths.get(userDefinition).toString();
    }

    @Test
    public void readFileToString() {
        String string = FileUtil.readFileToString(getUserDefinition());
        assertTrue(StringUtils.isNotEmpty(string));
    }

    @Test
    public void readNotExistFileToString() {
        String file = Paths.get(FileConfig.getTestResourcesFolder()
                , "entityGenerate"
                , "userDefinition123123.json").toString();

        assertThrows(RuntimeException.class, () -> FileUtil.readFileToString(file));
    }

    @Test
    public void exist() {
        Boolean exist = FileUtil.exist(getUserDefinition());
        assertTrue(exist);
    }

    @Test
    public void notExist() {
        Boolean exist = FileUtil.exist("entityGenerate/user/userDefinition123123.json");
        assertFalse(exist);
    }

    @Test
    public void write() {
        String file = Paths.get(FileConfig.getTestResourcesFolder()
                , "entityGenerate"
                , "userDefinitionWrite.md").toString();
        String content = "testWrite";
        FileUtil.write(file, content);
        assertEquals(content, FileUtil.readFileToString(file));
    }

    @Test
    public void notExistDiskWrite() {
        if (SystemUtil.isWindows()) {
            assertThrows(RuntimeException.class, () -> {
                String file = "W:\\source\\code-generate\\src\\test\\resources\\entityGen\\user\\userDefinitionWrite.json";
                String content = "test";
                FileUtil.write(file, content);

            });
        } else {
            // linux下会创建一个带 W: 的文件
            String file = "W:\\source\\code-generate\\src\\test\\resources\\entityGen\\user\\userDefinitionWrite.json";
            String content = "test";
            FileUtil.write(file, content);
        }

    }


    @Test
    public void recursiveReadFile() {
        List<String> entityGen = FileUtil.recursiveReadFile(
                new File(ResourceUtil.convertResourceToFullPath(
                        "entityGenerate")));

        assertTrue(CollectionUtils.isNotEmpty(entityGen));
    }

    @Test
    public void recursiveNullReadFile() {
        List<String> entityGen = FileUtil.recursiveReadFile(null);
        assertTrue(CollectionUtils.isEmpty(entityGen));
    }

}
