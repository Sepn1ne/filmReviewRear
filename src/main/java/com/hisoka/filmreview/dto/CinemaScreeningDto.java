package com.hisoka.filmreview.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/11 22:47
 */
@Data
public class CinemaScreeningDto {
    private Long id;
    private String cinemaName;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer stock;
    private String keepTimes;
}
