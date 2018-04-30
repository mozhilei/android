package com.example.administrator.lab6_webview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri=getIntent().getData();

        String s=new String(uri.getHost());

        WebView webView=findViewById(R.id.webview);
        webView.loadUrl(s);
    }
}
