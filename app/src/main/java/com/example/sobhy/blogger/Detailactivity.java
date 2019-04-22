package com.example.sobhy.blogger;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Detailactivity extends AppCompatActivity {

    ProgressBar progressBar;
    Toolbar toolbar;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);

        progressBar = findViewById(R.id.prog_bar);
        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.webview);

        setSupportActionBar(toolbar);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Toast.makeText(Detailactivity.this,"Page started loading",Toast.LENGTH_SHORT).show();
                }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                Toast.makeText(Detailactivity.this,"Page Loaded",Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl(getIntent().getStringExtra("url"));


    }
}
