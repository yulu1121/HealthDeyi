package com.anshi.healthdeyi.utils;


/**
 *
 * Created by yulu on 2017/9/1.
 */

public class Constants {
    public static final String TAG = "xxx";
    public static final String COMMON_HEADER = "http://47.128.149.98:8080/deyishou/a/interface/";
    public static final String COMMON_IMAGE_HEADER = "http://47.128.149.98:8080/deyishou";

    public static final String REGISTER = "member/register";
    public static final String FORGET_PASS = "member/updatePass";
    public static final String LOGIN = "member/login";
    public static final String HAS_LOGIN_ACTION = "has_login";
    public static final String RELATED_RECOMMEND_TYPE = "9";
    public static final String NEWS_TYPE = "8";
    public static final String SELF_TEST_TYPE = "7";
    public static final String METHOD_HEALTH_TYPE = "6";
    public static final String GONGFA_HEALTH_TYPE = "5";
    public static final String POINT_HEALTH_TYPE = "4";
    public static final String FAMILY_HEALTH_TYPE = "3";
    public static final String VIDEO_HEALTH_TYPE = "2";
    public static final String VOICE_HEALTH_TYPE = "1";
    public static final String WEB_FORMATION_URL = COMMON_HEADER+"api/getDetailsById";
    public static final String GET_TYPE_URL = COMMON_HEADER+"api/getType";
    public static final String GET_INFO_URL = COMMON_HEADER+"api/listInfo";
    public static final String WEB_ACTION = "web_action";
    public static final String MOVIE_ACTION = "movie_action";



//--------------------//
    public static final String BANNER_URL = "http://171.221.208.2:8090/api/channel/getChannelContent?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22section_id%22%3A%22d3b78e4b680611e6aed90894ef02dbe0%22%7D";
    public static final String RECOMMEND_URL = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22parent_class%22%3A%22eba54a6f144b11e7bb370894ef02dbe0%22%2C%22row%22%3A%2210%22%2C%22class_id%22%3A%22%22%2C%22startIndex%22%3A%220%22%7D";
    public static final String NEWS_ONE_URL = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%2C%22class_id%22%3A%228dbaa2ec622211e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%7D";
    public static final String NEWS_TWO_URL = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%2C%22class_id%22%3A%22b7e31c7c622211e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%7D";
    public static final String LONG_HEALTH_DAO = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22773e4608410f11e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%7D";
    public static final String SECONDS_HEALTH = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%222c434279476511e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%7D";
    public static final String VOICE_LIGHT = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22dfeb51bf476811e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%7D";
    public static final String MOVIE_TEACHER = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22800e3a9c410f11e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%7D";
    public static final String MOVIE_PUBLIC = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22c1f9f280476511e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%7D";
    public static final String MOVIE_LONG = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22c9f67f4c476511e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%7D";
    public static final String VOICE_LONG_TYPE = "long_type";
    public static final String VOICE_SECONDS_TYPE = "seconds_type";
    public static final String VOICE_LIGHT_TYPE = "light_type";
    public static final String MOVIE_TEACHER_CLASS_ROOM = "teacher_room";
    public static final String MOVIE_PUBLIC_CLASS_ROOM ="public_room";
    public static final String MOVIE_LONG_CLASS_ROOM = "long_room";
    public static final String VIDEO_PLAYING_URL = "http://171.221.208.2:8090/api/content/getContentAccessoryByContentId?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=";
    public static final String VOICE_ACTION = "voice_action";
    public static final String VIDEO_ACTION = "video_action";
    public static final String PRE_TITLE = "http://171.221.208.2:8090/api/content/getContentClassIsLevelTwoByBClassId?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22eba54a6f144b11e7bb370894ef02dbe0%22%7D";
    public static final String PRE_FORMATION = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=";
    public static final String HEALTH_POINT = "http://171.221.208.2:8090/api/bodypart/getBodyPartOne?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=androidapp_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22dfeb51bf476811e7b0820894ef159f97%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22%22%2C%22row%22%3A%2210%22%7D";
    public static final String HEALTH_POINT_PARENT = "http://171.221.208.2:8090/api/bodypart/getBodyPartTwoByOne?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=";
    public static final String HEALTH_POINT_PART = "http://171.221.208.2:8090/api/bodypart/getBodyAcupoint?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=";
    public static final String HEALTH_RELATED = "http://171.221.208.2:8090/api/content/getContentRelated?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=";
    public static final String HEALTH_METHOD = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22%22%2C%22admin_id%22%3A%22%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%2270af02f0405011e787cb0894ef02dbe0%22%2C%22row%22%3A%2210%22%7D";
    public static final String HEALTH_METHOD_VIDEO = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22%22%2C%22admin_id%22%3A%22%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22831807a7406d11e787cb0894ef02dbe0%22%2C%22row%22%3A%2210%22%7D";
    public static final String HEALTH_TEST = "http://171.221.208.2:8090/api/content/getContentList?app_package=com.juchiwang.app.healthy&app_sign=123456&version=2.0&device=android&in=%7B%22class_id%22%3A%22%22%2C%22admin_id%22%3A%22%22%2C%22startIndex%22%3A%220%22%2C%22parent_class%22%3A%22669b11c8621f11e784650894ef02dbe0%22%2C%22row%22%3A%2210%22%7D";
    public static final String PARTY_PERSONAL = "party_personal";
    public static final String PARTY_FREE = "party_free";
    public static final String PARTY_SCORE = "party_score";
    public static final String PARTY_PERSONAL_COMPANY = "party_company";
}
