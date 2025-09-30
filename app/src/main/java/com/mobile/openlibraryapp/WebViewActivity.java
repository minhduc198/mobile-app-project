package com.mobile.openlibraryapp;


import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);

        // Enable JavaScript if needed
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Open links inside WebView instead of external browser
        webView.setWebViewClient(new WebViewClient());

        // Get the URL from intent
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
