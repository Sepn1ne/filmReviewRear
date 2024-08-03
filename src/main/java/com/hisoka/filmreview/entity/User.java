package com.hisoka.filmreview.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-13
 */

@Data
@TableName("user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户的ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("nick_name")
    private String nickName;

    @TableField("email")
    private String email;

    public User() {
    }

    public User(Long id, String nickName, String email, String password, String icon) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.icon = icon;
    }

    @TableField("password")
    private String password;

    @TableField("icon")
    private String icon;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;


}
