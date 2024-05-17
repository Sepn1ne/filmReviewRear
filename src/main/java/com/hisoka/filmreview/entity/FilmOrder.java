package com.hisoka.filmreview.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2024-05-11
 */
@Getter
@Setter
@TableName("film_order")
@ApiModel(value = "FilmOrder对象", description = "")
public class FilmOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("user_id")
    private Long userId;

    @TableField("cinema_screening_id")
    private Long cinemaScreeningId;

    @TableField("purchase_time")
    private LocalDateTime purchaseTime;

    @TableField("use_time")
    private LocalDateTime useTime;

    @ApiModelProperty("0:未使用，1：已使用")
    @TableField("status")
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;


}
