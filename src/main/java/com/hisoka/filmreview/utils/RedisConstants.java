package com.hisoka.filmreview.utils;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/16 13:00
 */
public class RedisConstants {
    public static final String LOGIN_CODE = "login:code:";
    public static final String LOGIN_TOKEN = "login:token:";
    public static final Integer LOGIN_TOKEN_TTL = 60 * 24 * 7;

    public static final String FILM_SCREENING_STOCK = "film:screening:stock:";
    public static final String FILM_SCREENING_ORDER= "film:screening:order:";
    public static final String CACHE_FILM_KEY = "cache:film:";

}
