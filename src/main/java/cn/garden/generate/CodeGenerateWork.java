package cn.garden.generate;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成工作基类
 *
 * @author liwei
 */
public abstract class CodeGenerateWork {

    private final List<String> outputFiles = new ArrayList<>();

    public abstract void run();

    protected void addOutputFile(String resultFile) {
        outputFiles.add(resultFile);
    }

    public List<String> getOutputFiles() {
        return outputFiles;
    }


}
