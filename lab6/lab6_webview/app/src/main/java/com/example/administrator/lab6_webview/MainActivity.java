package com.example.administrator.lab6_webview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri=getIntent().getData();
        String url=uri.getHost();
        WebView webView=(WebView)findViewById(R.id.webview);
        webView.loadUrl(url);

    }

}


