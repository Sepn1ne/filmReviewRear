package com.hisoka.filmreview.dto;

import lombok.Data;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/20 15:18
 */

@Data
public class AbstractFilm {
    private long id;
    private String filmName;
    private String cover;
}
