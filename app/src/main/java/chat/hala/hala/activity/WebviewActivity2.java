package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import io.rong.common.RongWebView;


public class WebviewActivity2 extends BaseActivity {


    @BindView(R.id.rc_web_progressbar)
    ProgressBar progress;
    @BindView(R.id.rc_webview)
    RongWebView webview;
    private String url;
    private String title;

    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebviewActivity2.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }


    @Override
    protected void beforeInitView() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("webTitle");
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {


        WebSettings webSettings = webview.getSettings();

        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);    //支持自动加载图片
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSaveFormData(true);    //设置webview保存表单数据
        webSettings.setSavePassword(true);    //设置webview保存密码
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true); //设置APP可以缓存
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);//返回上个界面不刷新  允许本地缓存


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        webSettings.setAllowFileAccess(true);// 设置可以访问文件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//不支持放大缩小
        webSettings.setDisplayZoomControls(false);//不支持放大缩小


        webview.setLongClickable(true);
        webview.setScrollbarFadingEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setDrawingCacheEnabled(true);

        webview.addJavascriptInterface(new HtmlInterface(), "android");


//        if (!"undefined".equals(title)) {
//            tvToolbarTitle.setText(title);
//        }

        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Android8.0以下的需要返回true 并且需要loadUrl；8.0之后效果相反
                if (Build.VERSION.SDK_INT < 26) {
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.equals(WebviewActivity2.this.url)) {
                    progress.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }

        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return true;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
    }

    @Override
    public void onStop() {

        super.onStop();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            //获取历史列表
            WebBackForwardList mWebBackForwardList = webview.copyBackForwardList();
            //判断当前历史列表是否最顶端,其实canGoBack已经判断过
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                //获取历史列表
                String historyUrl = mWebBackForwardList.getItemAtIndex(
                        mWebBackForwardList.getCurrentIndex() - 1).getUrl();
                //按照自己规则检查是否为可跳转地址
                //注意:这里可以根据自己逻辑循环判断,拿到可以跳转的那一个然后webView.goBackOrForward(steps)
                String curUrl = mWebBackForwardList.getItemAtIndex(
                        mWebBackForwardList.getCurrentIndex()).getUrl();
                if (curUrl.contains("?")) {
                    curUrl = curUrl.substring(0, curUrl.indexOf("?"));
                }
                //解决返回不出去的问题
                if (url.contains(curUrl)) {
                    super.onBackPressed();
                    return;
                }
            }
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }




    @Override
    protected int getContentViewId() {
        return R.layout.activity_webview;
    }


    private final class HtmlInterface {
        @JavascriptInterface
        public void goBack() {
            webview.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }
        @JavascriptInterface
        public void feedBack () {
            webview.post(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WebviewActivity2.this, FeedBackActivity.class));
                }
            });
        }

    }

}
