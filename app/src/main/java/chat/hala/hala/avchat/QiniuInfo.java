package chat.hala.hala.avchat;

import chat.hala.hala.base.App;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.SPUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    public static void initQiniu() {
        RetrofitFactory
                .getInstance()
                .getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<QiNiuToken>() {
                    @Override
                    public void onGetData(QiNiuToken baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            return;
                        }
                        QiNiuToken.DataBean.StarchatanchorBean starchatanchor = baseBean.getData().getStarchatanchor();
                        QiNiuToken.DataBean.StarchatfeedbackBean starchatfeedback = baseBean.getData().getStarchatfeedback();
                        QiNiuToken.DataBean.StarchatmemberBean starchatmember = baseBean.getData().getStarchatmember();
                        QiniuInfo.setmStarchatanchorBean(starchatanchor);
                        QiniuInfo.setmStarchatfeedbackBean(starchatfeedback);
                        QiniuInfo.setmStarchatmemberBean(starchatmember);
                    }
                });
    }
}
