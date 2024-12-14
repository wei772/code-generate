package dto.cn.garden;

import com.baomidou.mybatisplus.annotation.*;
import org.apache.ibatis.type.JdbcType;

/**
 * Description:
 */
@TableName("user_group")
public class UserGroupDO {

    /**
     *
     */
    @TableField(value = "id",
            jdbcType = JdbcType.BIGINT,
            insertStrategy = FieldStrategy.NOT_NULL,
            updateStrategy = FieldStrategy.NOT_NULL
    )
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}