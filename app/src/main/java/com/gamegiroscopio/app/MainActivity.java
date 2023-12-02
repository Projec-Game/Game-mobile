package com.gamegiroscopio.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class MainActivity extends AppCompatActivity { 

    String url = " http://172.21.139.233:3000";
    SwipeRefreshLayout mySwipeRefreshLayout;
    WebView myWebView;
    final Context context = this;

    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STORAGE = 0;
    private final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int INPUT_FILE_REQUEST_CODE = 1;

    //private ValueCallBack<uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstBundle){
        //RequestPermission()

        super.onCreate(savedInstBundle);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.webview);
        assert  myWebView != null;

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        //myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setDatabaseEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= 22){
            webSettings.setMixedContentMode(0);
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, new Paint(null));
        }else if (Build.VERSION.SDK_INT >= 19){
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, new Paint(null));
        }else if (Build.VERSION.SDK_INT < 19){
            myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, new Paint(null));
        }
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient());
        //myWebView.setWebChromeClient(new MyWebChromeClient());
        myWebView.setWebViewClient(new MyWebViewCliente());
        myWebView.setVerticalScrollBarEnabled(false);

        myWebView.loadUrl(url);
        mySwipeRefreshLayout = this.findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        findViewById(R.id.loaderwebview).setVisibility(View.VISIBLE);
                        myWebView.reload();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }
    private class  MyWebViewCliente extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted( view,url,favicon);
        };
        @Override
        public void onPageFinished(WebView view, String url){
            findViewById(R.id.loaderwebview).setVisibility(View.GONE);
            findViewById(R.id.webview).setVisibility(View.VISIBLE);

        }
    }

}