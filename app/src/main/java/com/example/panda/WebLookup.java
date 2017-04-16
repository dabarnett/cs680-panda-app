package com.example.panda;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by nataliehaugenhusby on 16/04/2017.
 */

public class WebLookup extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_lookup);

        webView = (WebView) findViewById(R.id.webView);
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.bentley.edu");

        //intercept URL loading and load in widget
        webView.setWebViewClient(new WebViewClient(){

            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

        });
    }


    //the back key navigates back to the previous web page
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "WebLookup has finished!", Toast.LENGTH_LONG)
                .show();

    }
}
