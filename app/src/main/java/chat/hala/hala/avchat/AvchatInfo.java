package chat.hala.hala.avchat;

import android.content.Context;

import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.SPUtil;

public class AvchatInfo {
    private static int account;
    private static String name;
    private static String RTMToken;
    private static int anchorId;    //主播Id
    private static boolean sIsInCall;
    private static String sMediaToken;
    private static int coin;
    private static String sAvatarUrl;
    private static String sCallText;
    private static String sGender;
    private static String sBirthDate;
    private static int sMemberId;


    public static int getAccount() {
        return account;
    }

    public static void setAccount(int account) {
        AvchatInfo.account = account;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        AvchatInfo.name = name;
    }

    public static int getCoin() {
        return coin;
    }

    public static void setCoin(int coin) {
        AvchatInfo.coin = coin;
    }

    public static void setRTMToken(String RTMToken) {
        AvchatInfo.RTMToken = RTMToken;
    }

    public static String getRTMToken() {
        return RTMToken;
    }

    public static void setAnchorId(int anchorId) {
        AvchatInfo.anchorId = anchorId;
    }

    public static int getAnchorId() {
        return anchorId;
    }

    public static boolean isAnchor(){
        return anchorId!=0;
    }

    public static void setIsInCall(boolean isInCall) {
        sIsInCall = isInCall;
    }

    public static boolean isIsInCall() {
        return sIsInCall;
    }

    public static String getMediaToken() {
        return sMediaToken;
    }

    public static void setMediaToken(String mediaToken) {
        sMediaToken = mediaToken;
    }

    public static void setAvatarUrl(String avatarUrl) {
        sAvatarUrl = avatarUrl;
    }

    public static String getAvatarUrl() {
        return sAvatarUrl;
    }


    public static void setCallText(String callText) {
        sCallText = callText;
    }

    public static String getCallText() {
        return sCallText;
    }

    public static void saveBaseData(LoginBean.DataBean.MemberBean member, Context context) {

        String memeberJson = GsonUtil.parseObjectToJson(member);
      //  AvchatInfo.setAnchorId(member.getAnchorId());
        AvchatInfo.setMemberId(member.getMemberId());
        AvchatInfo.setAccount(member.getMemberId());
        AvchatInfo.setName(member.getUsername());
        AvchatInfo.setCoin(member.getCoin());
        AvchatInfo.setAvatarUrl(member.getAlbum().get(0).getMediaUrl());
        AvchatInfo.setGender(member.getGender());
        AvchatInfo.setBirthDate(member.getBirthDate());
        String accessToken = member.getAccessToken();
        String rongToken = member.getRongToken();
        SPUtil.getInstance(context).setString(Contact.TOKEN, accessToken);
        SPUtil.getInstance(context).setString(Contact.RONG_TOKEN, rongToken);
        SPUtil.getInstance(context).setUserId(member.getMemberId());
      //  SPUtil.getInstance(context).setAnchorId(member.getAnchorId());
        SPUtil.getInstance(context).setMemberJson(memeberJson);
    }


    public static void clearBaseData(Context context) {
        AvchatInfo.setAnchorId(0);
        AvchatInfo.setMemberId(0);
        AvchatInfo.setAccount(0);
        AvchatInfo.setName("");
        AvchatInfo.setCoin(0);
        AvchatInfo.setAvatarUrl("");
        AvchatInfo.setGender("secret");
        AvchatInfo.setBirthDate("1991-10-10");


        SPUtil.getInstance(context).setString(Contact.TOKEN, "");
        SPUtil.getInstance(context).setString(Contact.RONG_TOKEN, "");
        SPUtil.getInstance(context).setUserId(0);
        SPUtil.getInstance(context).setAnchorId(0);
        SPUtil.getInstance(context).setMemberJson("");
    }

    public static void setGender(String gender) {
        sGender = gender;
    }

    public static String getGender() {
        return sGender;
    }

    public static void setBirthDate(String birthDate) {
        sBirthDate = birthDate;
    }

    public static String getBirthDate() {
        return sBirthDate;
    }

    public static void setMemberId(int memberId) {
        sMemberId = memberId;
    }

    public static int getMemberId() {
        return sMemberId;
    }
}
