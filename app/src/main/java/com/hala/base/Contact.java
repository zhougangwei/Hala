package com.hala.base;

public class Contact {
    public static final String UPLOAD_HOST = "http://47.244.164.243";
    public static final String HOST = "http://47.244.164.243";

    public static final String TOKEN = "token";
    public static final String SIGN_UP = "sign_up";
    public static final String SIGN_IN = "sign_in";

    public static final int REPONSE_CODE_FAIL = 0;        //可捕获的服务器错误
    public static final int REPONSE_CODE_SUCCESS = 1;        //成功
    public static final int REPONSE_CODE_WRONG_PARAM = 2;        //参数错误
    public static final int REPONSE_CODE_WRONG_VER = 3;        //验证码错误
    public static final int REPONSE_CODE_OUT_WRONG = 4;        //外部服务错误，如短信平台发送失败等。

}
