package com.hala.avchat;

import com.hala.bean.MediaToken;

public class AvchatInfo {
    private static int account;
    private static String name;
    private static String RTMToken;
    private static int anchorId;    //主播Id
    private static boolean sIsInCall;
    private static String sMediaToken;
    private static int coin;
    private static String sAvatarUrl;

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
}
