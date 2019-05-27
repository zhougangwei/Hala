package com.hala.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;


import java.io.File;



public class FaceBookShareMananger {

    private static CallbackManager mCallbackManager;
    public static final int SHARE_REQUEST_CODE = 0x111;


    public static CallbackManager initCallbackManager(Activity activity) {
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(activity);
        }
        return mCallbackManager = CallbackManager.Factory.create();
    }



    /**
     * @param activity
     * @param facebookCallback
     * @param contentTitle
     * @param videoUrl
     * @param desc
     * @param contentUrl
     * @param type             1链接 2视频
     */
    public static void share(final Activity activity, FacebookCallback facebookCallback, String contentTitle,
                             String videoUrl, String desc, String contentUrl, int type) {
        if (TextUtils.isEmpty(contentUrl)) {
            return;
        }

        final ShareDialog shareDialog = new ShareDialog(activity);
        //注册分享状态监听回调接口
        shareDialog.registerCallback(mCallbackManager, facebookCallback, FaceBookShareMananger.SHARE_REQUEST_CODE);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent.Builder shareLinkContentBuilder = new ShareLinkContent.Builder();
            shareLinkContentBuilder.setContentUrl(Uri.parse(contentUrl));
            ShareLinkContent shareLinkContent = shareLinkContentBuilder.build();
            shareDialog.show(shareLinkContent, ShareDialog.Mode.AUTOMATIC);
        }
    }

    public static void share(final Activity activity, FacebookCallback facebookCallbackm, String contentUrl, int type) {

        final ShareDialog shareDialog = new ShareDialog(activity);
        //注册分享状态监听回调接口
        shareDialog.registerCallback(mCallbackManager, facebookCallbackm, FaceBookShareMananger.SHARE_REQUEST_CODE);

//        File dir = new File(Environment.getExternalStorageDirectory() + "/" +
//                Environment.DIRECTORY_DCIM,
//                "/Camera");
            File video1 = new File(contentUrl);
            Uri videoFileUri = Uri.fromFile(video1);
            ShareVideo videoUrl = new ShareVideo.Builder()
                    .setLocalUrl(videoFileUri)
                    .build();
            ShareVideoContent content = new ShareVideoContent.Builder()
                    .setVideo(videoUrl)
                    .build();
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

    }


    public static void share(final Activity activity, FacebookCallback facebookCallback, String contentUrl) {
        if (TextUtils.isEmpty(contentUrl)) {
            return;
        }

        final ShareDialog shareDialog = new ShareDialog(activity);
        //注册分享状态监听回调接口
        shareDialog.registerCallback(mCallbackManager, facebookCallback, FaceBookShareMananger.SHARE_REQUEST_CODE);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent.Builder shareLinkContentBuilder = new ShareLinkContent.Builder();
            shareLinkContentBuilder.setContentUrl(Uri.parse(contentUrl));
            ShareLinkContent shareLinkContent = shareLinkContentBuilder.build();
            shareDialog.show(shareLinkContent, ShareDialog.Mode.AUTOMATIC);
        }
    }





    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        if (FaceBookShareMananger.SHARE_REQUEST_CODE == requestCode) {
            if(mCallbackManager!=null){
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
            return;
        }
    }

    FacebookCallback facebCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
        }
        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
        }
    };


    public void shareFaceBook( Activity activity,  String contentUrl){
        share(activity,facebCallback,contentUrl);
    }

    public  void init(Activity activity) {
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(activity);
        }
        mCallbackManager = CallbackManager.Factory.create();
    }


}
