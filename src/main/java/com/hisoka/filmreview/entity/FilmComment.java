package com.hisoka.filmreview.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-05
 */
@Getter
@Setter
@TableName("film_comment")
@ApiModel(value = "FilmComment对象", description = "")
public class FilmComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("点评的id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("用户icon")
    @TableField(exist = false)
    private String icon;

    @ApiModelProperty("用户昵称")
    @TableField(exist = false)
    private String nickName;

    @ApiModelProperty("该用户是否购票")
    @TableField(exist = false)
    private Boolean hasPurchase;

    @ApiModelProperty("电影id")
    @TableField("film_id")
    private Long filmId;

    @ApiModelProperty("1:该点评含有评分，0：该点评不含评分")
    @TableField("scored")
    private Integer scored;

    @ApiModelProperty("0-5星评分")
    @TableField("score")
    private Integer score;

    @ApiModelProperty("1:该点评含有评论，0：该点评不含评论")
    @TableField("commented")
    private Integer commented;

    @ApiModelProperty("小标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("点评的内容")
    @TableField("content")
    private String content;

    @TableField("publish_time")
    private LocalDateTime publishTime;

    @TableField(exist = false)
    private String pubTime;

    @ApiModelProperty("顶的数量")
    @TableField("up")
    private Integer up;

    @ApiModelProperty("踩的数量")
    @TableField("down")
    private Integer down;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    @ApiModelProperty("0:未删除，1：已删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;

    @Override
    public String toString() {
        return "FilmComment{" +
                "id=" + id +
                ", userId=" + userId +
                ", icon='" + icon + '\'' +
                ", filmId=" + filmId +
                ", scored=" + scored +
                ", score=" + score +
                ", commented=" + commented +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                ", up=" + up +
                ", down=" + down +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
