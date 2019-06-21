package com.hala.avchat;

public class AvchatInfo {
    private static int account;
    private static int name;
    private static String RTMToken;
    private static int anchorId;    //主播Id

    public static int getAccount() {
        return account;
    }

    public static void setAccount(int account) {
        AvchatInfo.account = account;
    }

    public static int getName() {
        return name;
    }

    public static void setName(int name) {
        AvchatInfo.name = name;
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
}
