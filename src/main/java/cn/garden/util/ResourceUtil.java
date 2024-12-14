package cn.garden.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * 读取资源工具类
 *
 * @author liwei
 */
public class ResourceUtil {

    /**
     * 通过java资源名获取文件文本内容
     *
     * @param name 资源文件名
     * @return 返回UTF_8编码文本
     */
    public static String getResourceToString(String name) {
        try (
                InputStream resourceAsStream = getClassLoader().getResourceAsStream(name)
        ) {
            if (Objects.isNull(resourceAsStream)) {
                throw ExceptionUtil.createDefaultException("找不到资源文件" + name);
            }
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw ExceptionUtil.createDefaultException("读取资源文件失败" + name, e);
        }
    }

    /**
     * 读取资源目录中路径 path 下的所有文件名
     * 这个方法不能用在jar包中
     * <p>
     * File f = new File(this.getClass().getResource(“路径/目录”).toURI());
     * 只适合于要读取的文件在文件夹中，如果要读取的文件在jar中，就会报错：
     * java.lang.IllegalArgumentException: URI is not hierarchical
     * 原因：jar包中的文件不能通过这种方式读取，因为目录是不透明的
     */
    public static List<String> readAllResourceFileNames(String path) {
        List<String> allFile = new ArrayList<>();
        Enumeration<URL> urlEnumeration;
        try {
            urlEnumeration = getClassLoader().getResources(path);
        } catch (IOException e) {
            throw ExceptionUtil.createDefaultException("加载资源失败" + path, e);
        }

        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            File fileDir;
            try {
                fileDir = new File(new URI(url.toString()));
            } catch (URISyntaxException e) {
                throw ExceptionUtil.createDefaultException("打开文件失败" + url, e);
            }
            allFile.addAll(FileUtil.recursiveReadFile(fileDir));
        }
        return allFile;
    }

    /**
     * 将资源名转换成文件路径
     * 这个方法不能用在jar包中
     */
    public static String convertResourceToFullPath(String resourceName) {

        try {
            URL resource = getClassLoader().getResource(resourceName);
            if (Objects.isNull(resource)) {
                throw ExceptionUtil.createDefaultException("打开资源文件失败" + resourceName);
            }

            File file = new File(new URI(resource.toString()));
            return file.getAbsolutePath();
        } catch (URISyntaxException e) {
            throw ExceptionUtil.createDefaultException("打开资源文件失败" + resourceName, e);
        }
    }


    private static ClassLoader getClassLoader() {
        return ResourceUtil.class.getClassLoader();
    }

}
