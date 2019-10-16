package chat.hala.hala.service;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;

import chat.hala.hala.R;


public class DownloadServiceSe extends Service {
    private String MIME_TYPE = "application/vnd.android.package-archive";
    private BroadcastReceiver broadcastReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            startForeground(4,new Notification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent==null) {
            return super.onStartCommand(intent, flags, startId);
        }
        broadcastReceiver = new DownloadCompleteReceiver();
        registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownloadTask(intent.getStringExtra("url"));
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownloadTask(String downloadUrl)
    {
        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(downloadUrl));
        request.setTitle(getString(R.string.app_name));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setMimeType(MIME_TYPE);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getApkName(downloadUrl));
        dm.enqueue(request);
    }

    private String getApkName(String url)
    {
        int lastIndex = url.lastIndexOf("/");
        return url.substring(lastIndex);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
