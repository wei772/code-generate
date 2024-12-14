package cn.garden.generate;

import cn.garden.generate.factory.CodeGenerateWorkFactory;
import cn.garden.template.TemplateRepository;

import java.util.List;

/**
 * 代码生成入口类
 *
 * @author liwei
 */
public class CodeGenerate {

    private final CodeGenerateWork codeGenerateWork;

    public CodeGenerate(CodeGenerateContext codeGenerateContext) {
        codeGenerateContext.setTemplateRepository(
                new TemplateRepository(codeGenerateContext.getTemplateRepositoryCode())
        );
        codeGenerateWork = CodeGenerateWorkFactory.create(codeGenerateContext);
    }


    public void run() {

        codeGenerateWork.run();
        //使用标准输出文件名，方便管道处理
        for (String outputFiles : getOutputFiles()) {
            System.out.println(outputFiles);
        }
    }


    public CodeGenerateWork getCodeGenerateWork() {
        return codeGenerateWork;
    }

    public List<String> getOutputFiles() {
        return codeGenerateWork.getOutputFiles();
    }


}
