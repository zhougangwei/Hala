package chat.hala.hala.avchat;

import chat.hala.hala.bean.QiNiuToken;

public class QiniuInfo {


    private static QiNiuToken.DataBean.StarchatanchorBean mStarchatanchorBean;
    private static QiNiuToken.DataBean.StarchatfeedbackBean mStarchatfeedbackBean;
    private static QiNiuToken.DataBean.StarchatmemberBean mStarchatmemberBean;


    public static QiNiuToken.DataBean.StarchatanchorBean getmStarchatanchorBean() {
        return mStarchatanchorBean;
    }

    public static void setmStarchatanchorBean(QiNiuToken.DataBean.StarchatanchorBean mStarchatanchorBean) {
        QiniuInfo.mStarchatanchorBean = mStarchatanchorBean;
    }

    public static QiNiuToken.DataBean.StarchatfeedbackBean getmStarchatfeedbackBean() {
        return mStarchatfeedbackBean;
    }

    public static void setmStarchatfeedbackBean(QiNiuToken.DataBean.StarchatfeedbackBean mStarchatfeedbackBean) {
        QiniuInfo.mStarchatfeedbackBean = mStarchatfeedbackBean;
    }

    public static QiNiuToken.DataBean.StarchatmemberBean getmStarchatmemberBean() {
        return mStarchatmemberBean;
    }

    public static void setmStarchatmemberBean(QiNiuToken.DataBean.StarchatmemberBean mStarchatmemberBean) {
        QiniuInfo.mStarchatmemberBean = mStarchatmemberBean;
    }
}
