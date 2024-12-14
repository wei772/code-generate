package cn.garden.external;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author liwei
 */
public class TestVelocity {

    private static final String VM_PATH = "template/velocity/helloworld.vtl";

    @Test
    public void helloWorld() {
        // 初始化模板引擎
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "class");
        velocityEngine.setProperty("resource.loader.class.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        // 获取模板文件
        Template template = velocityEngine.getTemplate(VM_PATH);

        // 设置变量，velocityContext是一个类似map的结构
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("name", "World");
        List<String> list = new ArrayList<>();
        list.add("jack");
        list.add("kitty");
        velocityContext.put("list", list);

        // 输出渲染后的结果
        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        assertEquals("Hello World", stringWriter.toString());
    }
}
