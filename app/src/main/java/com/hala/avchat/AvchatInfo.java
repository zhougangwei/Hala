package com.hala.avchat;

public class AvchatInfo {
    private static int account;
    private static int name;
    private static String RTMToken;

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
}
