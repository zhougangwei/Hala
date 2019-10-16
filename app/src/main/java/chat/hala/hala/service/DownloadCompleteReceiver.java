package chat.hala.hala.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class DownloadCompleteReceiver extends BroadcastReceiver {

    private String MIME_TYPE = "application/vnd.android.package-archive";


    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long queueId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(queueId);
        Cursor cursor = dm.query(query);

        if (cursor.moveToFirst())
        {
            int fileNameId = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
            int fileUriId = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
            String fileUri = cursor.getString(fileUriId);
            String fileName = null;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
            {
                fileName = Uri.parse(fileUri).getPath();
            }
            else
            {
                /**Android 7.0以上的方式：请求获取写入权限，这一步报错**/
                fileName = cursor.getString(fileNameId);
            }
            InstallApk(fileName, context);
        }
        ((Service)context).stopSelf();
    }

    private void InstallApk(String fileName, Context context)
    {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(context, "chat.hala.hala.fileprovider",
                    new File(fileName));
        else
            uri = Uri.fromFile(new File(fileName));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, MIME_TYPE);
        context.startActivity(intent);
    }
}
