package chat.hala.hala.http;

import android.util.Log;

import com.qiniu.android.common.AutoZone;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UploadPicManger {


    QiNiuUploadCompletionHandler completionHandler;
    private int imageIndex;
    private List<String> pathList=new ArrayList<>();

    public void uplaodPic(String filename, String remoteName, String token) {
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
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }

    public void uploadImageArray(final List<String> imageArray, final int index, final String token, final String qiNiuUrl, final QiNiuUploadCompletionHandler completionHandler ) {
        imageIndex = index;
        if (index == imageArray.size()) {
            if (completionHandler != null) {
                completionHandler.uploadSuccess(null, pathList);
            }
        } else {
            Configuration config = new Configuration.Builder()
                    .zone(AutoZone.autoZone)
                    .zone(FixedZone.zone0)
                    .build();
            String realFilePath = imageArray.get(imageIndex);
            final String name = realFilePath.substring(realFilePath.lastIndexOf("/") + 1, realFilePath.length());
            UploadManager uploadManager = new UploadManager(config);
                uploadManager.put(realFilePath, name, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                            if (responseInfo.isOK()) {
                                String path = qiNiuUrl + "/" + name;
                                pathList.add(path);
                                imageIndex++;
                                uploadImageArray(imageArray, imageIndex,token,qiNiuUrl,completionHandler);
                            } else {
                                if (completionHandler != null) {
                                    completionHandler.uploadFailure();
                                }
                            }
                        }
                    }, null);

        }
    }

    public interface QiNiuUploadCompletionHandler {
        //上传数组时候list有值，单个上传时候path有值
        void uploadSuccess(String path,List<String> paths);

        void uploadFailure();
    }


}
