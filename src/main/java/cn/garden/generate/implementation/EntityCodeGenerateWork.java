package cn.garden.generate.implementation;

import cn.garden.entity.Entity;
import cn.garden.entity.reader.EntityReader;
import cn.garden.generate.BaseGenerateWorkContext;
import cn.garden.generate.CodeGenerateWork;
import cn.garden.util.Tags;
import cn.garden.generate.enums.EntityTemplatePropertyEnum;
import cn.garden.param.implementation.FileParam;
import cn.garden.param.implementation.FolderParam;
import cn.garden.template.TemplateEngine;
import cn.garden.template.factory.TemplateEngineFactory;
import cn.garden.util.ExceptionUtil;
import cn.garden.util.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 实体类代码生成工作类
 *
 * @author liwei
 */
public class EntityCodeGenerateWork extends CodeGenerateWork {

    private final TemplateEngine templateEngine;
    private final EntityReader entityReader;
    private final BaseGenerateWorkContext entityGenContext;

    public EntityCodeGenerateWork(BaseGenerateWorkContext entityGenContext, EntityReader entityReader) {
        this.entityGenContext = entityGenContext;
        this.templateEngine = TemplateEngineFactory.create(entityGenContext.getEngineName());
        this.entityReader = entityReader;

        templateEngine.init();
    }


    @Override
    public void run() {
        if (CollectionUtils.isEmpty(entityGenContext.getTemplateFiles())) {
            throw ExceptionUtil.createDefaultException("模板文件不能为空");
        }
        if (Objects.isNull(entityReader)) {
            throw ExceptionUtil.createDefaultException("实体读取器不能为空");
        }

        List<Entity> entities = entityReader.getEntities(entityGenContext.getTargetNames());
        if (CollectionUtils.isEmpty(entities)) {
            throw ExceptionUtil.createDefaultException("实体列表不能为空");
        }

        if (Objects.isNull(entityGenContext.getTargetLanguage())) {
            throw ExceptionUtil.createDefaultException("目标语言不能为空");
        }
        for (Entity entity : entities) {
            entity.setTargetLanguage(entityGenContext.getTargetLanguage());
        }

        for (String templateFile : entityGenContext.getTemplateFiles()) {
            for (Entity entity : entities) {
                String pathString = renderToFile(templateFile, entity);
                addOutputFile(pathString);
            }
        }

    }

    private String renderToFile(String templateFile, Entity entity) {
        Map<String, Object> templateProperties = initTemplateProperties(entity);

        StringWriter stringWriter = new StringWriter();
        templateEngine.render(templateFile, templateProperties, stringWriter);
        String result = stringWriter.toString();

        FolderParam folderParam = new FolderParam(result);
        FileParam fileParam = new FileParam(folderParam);
        String fileName = fileParam.getFile();
        if (!StringUtils.contains(fileName, ".")) {
            fileName = fileName + entity.getTargetLanguage().getFileExtension();
        }

        Path path = Paths.get(entityGenContext.getOutput(), folderParam.getPath(), fileName);

        String pathString = path.toString();
        FileUtil.write(pathString, fileParam.getNewContext());

        return pathString;
    }

    private Map<String, Object> initTemplateProperties(Entity entity) {
        Map<String, Object> templateProperties = new HashMap<>();

        templateProperties.put(EntityTemplatePropertyEnum.BASE_PACKAGE.getName(), entityGenContext.getBasePackage());
        templateProperties.put(EntityTemplatePropertyEnum.AUTHOR.getName(), entityGenContext.getAuthor());
        templateProperties.put(EntityTemplatePropertyEnum.TAGS.getName(), new Tags(entityGenContext.getTags()));

        templateProperties.put(EntityTemplatePropertyEnum.ENTITY.getName(), entity);
        templateProperties.put(EntityTemplatePropertyEnum.IMPORT_STRINGS.getName(), entity.getImportStrings());
        templateProperties.put(EntityTemplatePropertyEnum.DESCRIPTION.getName(), entity.getDescription());
        templateProperties.put(EntityTemplatePropertyEnum.SOURCE_NAME.getName(), entity.getSourceName());
        templateProperties.put(EntityTemplatePropertyEnum.PASCAL_NAME.getName(), entity.getPascalName());
        templateProperties.put(EntityTemplatePropertyEnum.CAMEL_NAME.getName(), entity.getCamelName());
        templateProperties.put(EntityTemplatePropertyEnum.PROPERTIES.getName(), entity.getProperties());
        return templateProperties;
    }

}
