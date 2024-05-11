package com.hisoka.filmreview.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("city")
@ApiModel(value = "City对象", description = "")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("pid")
    private Integer pid;

    @TableField("cityname")
    private String cityname;

    @TableField("type")
    private Integer type;


}
