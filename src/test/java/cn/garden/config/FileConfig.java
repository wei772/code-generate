package cn.garden.config;

import cn.garden.template.TemplateRepository;
import cn.garden.util.ResourceUtil;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author liwei
 */
public class FileConfig {

    public static String getProjectFolder() {

        String fullPath = ResourceUtil.convertResourceToFullPath(TemplateRepository.BASE_FOLDER);
        File parentFile = new File(fullPath).getParentFile().getParentFile().getParentFile();
        return parentFile.toString();
    }

    /**
     * 源代码路径不好获取，暂时只能通过硬编码的方式
     */
    public static String getCodeResourcesFolder() {
        return Paths.get(getProjectFolder(), "src", "main", "resources").toString();
    }

    /**
     * 源代码路径不好获取，暂时只能通过硬编码的方式
     */
    public static String getTestResourcesFolder() {
        return Paths.get(getProjectFolder(), "src", "test", "resources").toString();
    }


    /**
     * 源代码路径不好获取，暂时只能通过硬编码的方式
     */
    public static String getGenerateResultFolder() {
        return Paths.get(getTestResourcesFolder(), "generateResult").toString();
    }

}
