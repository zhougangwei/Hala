package chat.hala.hala.base;

public class Contact {
    public static final String UPLOAD_HOST = "http://47.244.164.243";
    public static final String HOST        = "http://47.244.164.243";

    public static final String TOKEN   = "token";
    public static final String RONG_TOKEN   = "rong_token";
    public static final String MEMBER_DATA   = "member_dta";
    public static final String USER_ID   = "userid";
    public static final String ANCHOR_ID   = "anchorid";
    public static final String SIGN_UP = "sign_up";
    public static final String SIGN_IN = "sign_in";

    public static final int REPONSE_CODE_FAIL        = 0;        //可捕获的服务器错误
    public static final int REPONSE_CODE_SUCCESS     = 1;        //成功
    public static final int REPONSE_CODE_WRONG_PARAM = 2;        //参数错误
    public static final int REPONSE_CODE_WRONG_VER   = 3;        //验证码错误
    public static final int REPONSE_CODE_OUT_WRONG   = 4;        //外部服务错误，如短信平台发送失败等。


     public static final int REPONSE_CODE_REGIST_FAIL_ALREADY_NAME=               101  ;//   用户名存在，无法注册
     public static final int REPONSE_CODE_REGIST_FAIL_ALREADY_PHONE=   102  ;//   用户手机号存在，无法注册
     public static final int REPONSE_CODE_REGIST_FAIL_ALREADY_FACEBOOK=   103  ;//   用户facebookId存在，无法注册
     public static final int REPONSE_CODE_APPLYANCHOR_FAIL_ALREADY_NAME_OR_PHONE=           201  ;//   主播用户名或手机号存在，无法申请
     public static final int REPONSE_CODE_APPLYANCHOR_FAIL_ANCHOR_NOT_EXIT=   202  ;//   请求的主播不存在

    public static final int REPONSE_CODE_REVERSE_NOMONEY  = 301;//     余额不足，无法预约
    public static final int REPONSE_CODE_REVERSE_SAME  = 302;//     已经有一个与相同主播的预约，无法预约
    public static final int REPONSE_CODE_CALL_IN_CALL  = 303;//     通话对象在通话中
    public static final int REPONSE_CODE_CALL_NO_ID  = 304;//     无法对应Id的通话
    public static final int REPONSE_CODE_STATE_USER_CANT_CHANGE  = 305;//     当前用户无法更改此通话状态
    public static final int REPONSE_CODE_STATE_ERROR_CHANGE_  = 306;//     即将改变的通话状态与现有状态逻辑不符
    public static final int REPONSE_CODE_CALL_NO_MONEY  = 307;//     没有足够的金币发起通话
    public static final int REPONSE_CODE_CALL_OFFLINE  = 308;//     对方不在线无法拨打
    public static final int REPONSE_CODE_REVERSE_NO_ITEM  = 309;//     未找到此预约
    public static final int REPONSE_CODE_REVERSE_OUTDATE  = 310;//     预约已完成或过期，无法在拨打

    public static final int PAGE_SIZE = 20;

    public static final String RTM_HANG_UP_STRING = "tel://hangup?";
    public static final String RTM_DO_CALL_STRING = "tel://call?";
    public static final String RTM_ANSWER_STRING  = "tel://answer?";


    public static final int REQUEST_BIO = 222;
    public static final int REQUEST_CHOOSE_COUNTRY = 666;
    public static final int REQUEST_PHONE = 668;
    public static final int REQUEST_CODE_CHOOSE = 224;
    public static final int REQUEST_CHOOSE_CARD = 225;      //身份验证
}
