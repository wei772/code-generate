package cn.garden.generate.factory;

import cn.garden.generate.entity.LanguageType;
import cn.garden.generate.entity.reader.EntityReader;
import cn.garden.generate.entity.reader.factory.EntityReaderFactory;
import cn.garden.generate.BaseGenerateWorkContext;
import cn.garden.generate.CodeGenerateContext;
import cn.garden.generate.CodeGenerateWork;
import cn.garden.generate.CodeGenerateWorkType;
import cn.garden.generate.GenerateProperty;
import cn.garden.generate.implementation.EntityCodeGenerateWork;
import cn.garden.generate.template.TemplateRepository;
import cn.garden.generate.template.TemplateRepositoryConfig;
import cn.garden.generate.util.ExceptionUtil;

/**
 * 代码生成工作类工厂方法
 *
 * @author liwei
 */
public class CodeGenerateWorkFactory {

    public static CodeGenerateWork create(CodeGenerateContext codeGenerateContext) {
        return createCore(codeGenerateContext);
    }


    private static EntityCodeGenerateWork createCore(CodeGenerateContext codeGenerateContext) {
        TemplateRepository templateRepository = codeGenerateContext.getTemplateRepository();
        TemplateRepositoryConfig config = templateRepository.getConfig();
        CodeGenerateWorkType generateWorkEnum = CodeGenerateWorkType.of(config.getGenerateType());

        if (generateWorkEnum == CodeGenerateWorkType.ENTITY) {
            BaseGenerateWorkContext entityGenerateContext = new BaseGenerateWorkContext();
            entityGenerateContext.setEngineName(config.getEngineName());
            entityGenerateContext.setTemplateFiles(templateRepository.getTemplateFiles());
            entityGenerateContext.setTargetLanguage(LanguageType.of(config.getTargetLanguage()));

            entityGenerateContext.setAuthor(codeGenerateContext.getAuthor());
            entityGenerateContext.setTargetNames(codeGenerateContext.getTargetNames());
            entityGenerateContext.setOutput(codeGenerateContext.getOutput());
            entityGenerateContext.setBasePackage(codeGenerateContext.getBasePackage());
            entityGenerateContext.setTags(codeGenerateContext.getTags());

            EntityReader reader = EntityReaderFactory.create(
                    (String) codeGenerateContext.get(GenerateProperty.ENTITY_READER.getName())
                    , codeGenerateContext.getExtendMap());

            return new EntityCodeGenerateWork(entityGenerateContext, reader);
        }

        throw ExceptionUtil.createDefaultException(config.getGenerateType() + "找不到对应的CodeGenerateWork");
    }


}
