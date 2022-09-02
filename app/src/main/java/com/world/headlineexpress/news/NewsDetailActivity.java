package com.world.headlineexpress.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title, desc, content, imageURL, url;
    private TextView titleTV, subtititleTV, contentTV;
    private ImageView newsIV;
    private Button readButton;
    private WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
//        getSupportActionBar().hide();
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");


        titleTV = findViewById(R.id.TVNewsDetail);
        subtititleTV = findViewById(R.id.TVSubNewsDetail);
        contentTV = findViewById(R.id.TVContent);
        newsIV = findViewById(R.id.IVNewsDetail);
        readButton = findViewById(R.id.readButton);

        webview = findViewById(R.id.webView);
        new AdBlockerWebView.init(this).initializeWebView(webview);
        webview.setWebViewClient(new Browser_home());


        titleTV.setText(title);
        subtititleTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageURL).into(newsIV);

        readButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RequiresFeature")
            @Override
            public void onClick(View v) {

                Toast.makeText(NewsDetailActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                webview.setVisibility(View.VISIBLE);
                readButton.setVisibility(View.INVISIBLE);

//                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
//                    // Turning on the dark mode
//                    WebSettingsCompat.setForceDark(webview.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
//
//
//            }else {
////                    WebSettingsCompat.setForceDark(webview.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
//
//                }


                ////
                WebSettings settings = webview.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setAllowContentAccess(true);
                settings.setDomStorageEnabled(true);
                //

                webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                webview.getSettings().setAppCacheEnabled(true);
                webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                settings.setUseWideViewPort(true);
                settings.setSaveFormData(true);
                //

                webview.setWebViewClient(new WebViewClient());
                webview.loadUrl(url);


            }
        });

    }


    private class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            return AdBlockerWebView.blockAds(view, url) ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);

        }

    }


}