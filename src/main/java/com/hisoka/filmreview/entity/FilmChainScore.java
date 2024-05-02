package com.hisoka.filmreview.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
@TableName("film_chain_score")
@ApiModel(value = "FilmChainScore对象", description = "")
public class FilmChainScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("film_id")
    private Long filmId;

    @TableField("cinema_chain_score")
    private Float cinemaChainScore;

    @TableField("cinema_five")
    private Integer cinemaFive;

    @TableField("cinema_four")
    private Integer cinemaFour;

    @TableField("cinema_three")
    private Integer cinemaThree;

    @TableField("cinema_two")
    private Integer cinemaTwo;

    @TableField("cinema_one")
    private Integer cinemaOne;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("0表示未删除，1表示已删除")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
