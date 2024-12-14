package cn.garden.param;

import cn.garden.param.implementation.FileParam;
import cn.garden.param.implementation.FolderParam;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class TestParam {

    /**
     * 1. 获取生成文件的folder
     * 2. 并且移除掉生成文本中的@folder所在行
     */
    @Test
    public void getFolder() {
        String result = "@folder cn.garden.test\n" +
                "test";
        FolderParam folderParam = new FolderParam(result);
        List<String> folderItems = folderParam.getFolderItems();
        String testResult = folderParam.getNewContext();

        assertEquals(3, CollectionUtils.size(folderItems));
        assertTrue(folderParam.hasValue());
        assertTrue(CollectionUtils.isEqualCollection(folderItems, Arrays.asList("cn", "garden", "test")));
        assertEquals("test", testResult);
    }

    /**
     * 1. 获取生成文件的file
     * 2. 并且移除掉生成文本中的@file所在行
     */
    @Test
    public void getFile() {
        String result = """
                @file user.java
                line2
                                
                                
                                
                line5""";
        FileParam folderParam = new FileParam((result));
        String fileName = folderParam.getFile();
        String testResult = folderParam.getNewContext();

        assertEquals("user.java", fileName);
        assertEquals("""
                line2
                                
                                
                                
                line5""", testResult);
    }


    @Test
    public void getFolderAndFile() {
        String result = """
                @folder cn.garden.test
                @file user.java
                test""";
        FolderParam folderParam = new FolderParam(result);
        List<String> folderItems = folderParam.getFolderItems();

        assertEquals(3, CollectionUtils.size(folderItems));
        assertTrue(CollectionUtils.isEqualCollection(folderItems, Arrays.asList("cn", "garden", "test")));

        FileParam fileParam = new FileParam(folderParam.getNewContext());
        String fileName = fileParam.getFile();
        String testFileResult = fileParam.getNewContext();

        assertEquals("user.java", fileName);
        assertEquals("test", testFileResult);
    }


    @Test
    public void getEmptyFile() {
        assertThrows(RuntimeException.class, () -> new FileParam("test "));
    }

    @Test
    public void getEmptyFolder() {
        assertThrows(RuntimeException.class, () -> new FolderParam(""));
    }
}
