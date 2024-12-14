package cn.garden.param.implementation;

import cn.garden.param.Param;

/**
 * 获取文件参数，用于生成文件名
 *
 * @author liwei
 */
public class FileParam extends Param {

    public static final String FILE = "@file";

    public FileParam(String context) {
        super(context);
    }

    public FileParam(Param param) {
        this(param.getNewContext());
    }

    @Override
    public String getParamName() {
        return FILE;
    }

    @Override
    public Boolean must() {
        return Boolean.TRUE;
    }

    public String getFile() {
        return getValue();
    }
}
