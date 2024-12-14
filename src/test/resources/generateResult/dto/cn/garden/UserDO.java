package dto.cn.garden;

import com.baomidou.mybatisplus.annotation.*;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;

/**
 * Description:用户
 */
@TableName("user")
public class UserDO {

    /**
     * ID
     */
    @TableField(value = "id",
            jdbcType = JdbcType.BIGINT,
            insertStrategy = FieldStrategy.NOT_NULL,
            updateStrategy = FieldStrategy.NOT_NULL
    )
    private Long id;

    /**
     * 编码
     */
    @TableField(value = "code",
            jdbcType = JdbcType.VARCHAR,
            insertStrategy = FieldStrategy.NOT_NULL,
            updateStrategy = FieldStrategy.NOT_NULL
    )
    private String code;

    /**
     * 时间
     */
    @TableField(value = "time",
            jdbcType = JdbcType.TIMESTAMP,
            insertStrategy = FieldStrategy.NOT_NULL,
            updateStrategy = FieldStrategy.NOT_NULL
    )
    private LocalDateTime time;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}