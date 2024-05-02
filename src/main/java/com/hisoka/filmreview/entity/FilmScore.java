package com.hisoka.filmreview.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("film_score")
@ApiModel(value = "FilmScore对象", description = "")
public class FilmScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("film_id")
    private Long filmId;

    @TableField("normal_score")
    private Float normalScore;

    @TableField("normal_five")
    private Integer normalFive;

    @TableField("normal_four")
    private Integer normalFour;

    @TableField("normal_three")
    private Integer normalThree;

    @TableField("normal_two")
    private Integer normalTwo;

    @TableField("normal_one")
    private Integer normalOne;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("0表示未删除，1表示已删除")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
