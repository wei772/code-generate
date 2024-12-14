package cn.garden.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签类
 *
 * @author liwei
 */
public class Tags {

    private final List<String> values;

    public Tags(List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            this.values = new ArrayList<>();
        } else {
            this.values = values;
        }
    }

    /**
     * 检验是否存在该标签
     */
    public Boolean hasTag(String tag) {
        return values.contains(tag);
    }

}
