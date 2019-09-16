package com.yzb.andong.config.security;

/**
 * jwt常量
 *
 * @author b
 */
public class JwtConstant {

    public static String JWT_ID = "098f6bcd4621d373cade4e832627b4f6";
    public static String JWT_NAME = "app.api.pl.com";
    public static String JWT_SECRET = "UJHBSZ5SPEBEVVOT0ONNYLFGHTHU5J8WSGWUDOTYWAC";
    public static String MANAGE_JWT_SECRET = "P3QLLQ46SY6ELCKCXI0ZFX4IAQJV6E3XFASFE2V01NK";
    public static long JWT_REFRESH_TTL = 3600000L * 24 * 365;

    public static void main(String[] args) {
        System.out.println(JWT_SECRET.length());
    }

}
