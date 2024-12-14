package cn.garden.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件工具类
 *
 * @author liwei
 */
public class FileUtil {

    public static String readFileToString(String fileName) {
        try {
            return FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw ExceptionUtil.createDefaultException("读取文件失败 " + fileName, e);
        }
    }

    public static void write(String path, String content) {
        try {
            FileUtils.write(new File(path), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw ExceptionUtil.createDefaultException("写入文件失败 " + path, e);
        }
    }

    public static Boolean exist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 读取文件或递归读取文件夹内的文件
     */
    public static List<String> recursiveReadFile(File fileOrDir) {
        List<String> allFile = new ArrayList<>();

        if (Objects.isNull(fileOrDir)) {
            return allFile;
        }

        if (fileOrDir.isFile()) {
            allFile.add(fileOrDir.getPath());
        } else {
            for (var file : Objects.requireNonNull(fileOrDir.listFiles())) {
                allFile.addAll(recursiveReadFile(file)); // 如果不需要递归遍历子文件夹，可将此处改为对 fileOrDir 的直接处理
            }
        }
        return allFile;
    }
}
