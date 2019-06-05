package com.hala.http;

import android.util.Log;

import com.qiniu.android.common.AutoZone;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

public class UploadPicManger {

    public void uplaodPic(String filename,String remoteName,String token){
        Configuration config = new Configuration.Builder()
        .zone(AutoZone.autoZone)
        .zone(FixedZone.zone0)
        .build();
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(filename, remoteName, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }
}
