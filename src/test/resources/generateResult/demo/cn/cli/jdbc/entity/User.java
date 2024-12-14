package demo.cn.cli.jdbc.entity;

import java.time.LocalDateTime;

/**
 * 该类包含Id字段
 * Description:用户
 *
 * @author
 */
public class User {

    /**
     * ID
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 时间
     */
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