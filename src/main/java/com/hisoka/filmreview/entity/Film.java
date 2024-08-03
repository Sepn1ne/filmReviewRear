package com.hisoka.filmreview.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Year;
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
 * @since 2024-04-19
 */
@Data
@TableName("film")
@ApiModel(value = "Film对象", description = "")
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @ApiModelProperty("电影名字")
    @TableField("film_name")
    private String filmName;

    @ApiModelProperty("电影评分")
    @TableField("score")
    private Float score;

    @ApiModelProperty("电影封面链接")
    @TableField("cover")
    private String cover;


    @ApiModelProperty("上映年份")
    @TableField("release_year")
    private Year releaseYear;

    @ApiModelProperty("制片国家")
    @TableField("producing_country")
    private String producingCountry;

    @ApiModelProperty("主演")
    @TableField("actor_first")
    private String actorFirst;

    @ApiModelProperty("配角1")
    @TableField("actor_second")
    private String actorSecond;

    @ApiModelProperty("配角2")
    @TableField("actor_third")
    private String actorThird;

    @ApiModelProperty("导演")
    @TableField("director")
    private String director;

    @ApiModelProperty("编剧")
    @TableField("scriptwriter")
    private String scriptwriter;

    @ApiModelProperty("片长")
    @TableField("movie_length")
    private String movieLength;

    @ApiModelProperty("简介")
    @TableField("intro")
    private String intro;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    @ApiModelProperty("0：未删除，1：已删除")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
