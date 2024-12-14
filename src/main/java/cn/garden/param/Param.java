package cn.garden.param;

import cn.garden.util.ExceptionUtil;
import cn.garden.util.StringUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 参数，支持在配置文件里使用参数,参数以换行介绍
 * 例如 @folder、@file
 *
 * @author liwei
 */
public abstract class Param {

    /**
     * 入参文本
     */
    private final String context;
    /**
     * 参数值
     */
    private String value;
    /**
     * 处理完参数之后的文本
     */
    private String newContext;

    public Param(String context) {
        this.context = context;
        this.newContext = context;
        initParam();
    }


    public abstract String getParamName();

    public abstract Boolean must();

    public String getValue() {
        return value;
    }

    public Boolean hasValue() {
        return StringUtils.isNotEmpty(getValue());
    }

    public String getNewContext() {
        return newContext;
    }

    private void initParam() {
        if (StringUtils.isEmpty(context) || !StringUtils.contains(context, getParamName())) {
            if (BooleanUtils.isTrue(must())) {
                throw ExceptionUtil.createDefaultException(getParamName() + ":变量是必须的");
            }
            return;
        }

        List<String> lines = StringUtil.getLines(context);
        String paramLine = lines
                .stream()
                .filter(m -> StringUtils.contains(m, getParamName()))
                .findFirst()
                .orElse(StringUtils.EMPTY);

        value = StringUtils.trim(
                paramLine.substring(paramLine.indexOf(getParamName()) + getParamName().length())
        );
        newContext = StringUtil.joinLine(
                lines.stream().filter(m -> !StringUtils.contains(m, getParamName())).toList()
        );

    }
}
