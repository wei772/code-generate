package cn.garden.param.implementation;

import cn.garden.param.Param;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 获取文件夹参数，用于生成文件夹
 *
 * @author liwei
 */
public class FolderParam extends Param {

    public static final String FOLDER = "@folder";

    public FolderParam(String context) {
        super(context);
    }


    @Override
    public String getParamName() {
        return FOLDER;
    }

    @Override
    public Boolean must() {
        return Boolean.TRUE;
    }

    public List<String> getFolderItems() {
        if (StringUtils.isEmpty(getValue())) {
            return new ArrayList<>();
        }
        return Arrays.asList(StringUtils.split(getValue(), "."));
    }

    public String getPath() {
        return String.join(File.separator, getFolderItems());
    }

}
