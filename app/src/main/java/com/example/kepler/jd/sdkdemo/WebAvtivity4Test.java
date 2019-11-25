/**
 * 
 */
package com.example.kepler.jd.sdkdemo;



import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author "suwg" 2016年8月9日
 */
public class WebAvtivity4Test extends Activity {

	WebView webView;
	String url ;


	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		url	= getIntent().getStringExtra("url");
		if (TextUtils.isEmpty(url)) {
			finish();
		}

		webView = new WebView(this);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.getSettings().setAllowFileAccess(false);
		webView.getSettings().setAllowFileAccessFromFileURLs(false);
		webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);

		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setLoadWithOverviewMode(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (0 != (getApplicationContext().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE))

			{
				WebView.setWebContentsDebuggingEnabled(true);
			}
		}
		
		
	
		webView.setWebViewClient(new WebViewClient() {
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {

				super.onReceivedError(view, errorCode, description, failingUrl);
				Log.w("suwg", "onReceivedError" + errorCode + " " + description + "  " + failingUrl);
			}
			
			
			
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				
				if (url.startsWith("weixin:")||url.startsWith("openapp.jdmobile:")) {
					try {
						final Intent intent = new Intent(Intent.ACTION_VIEW,
								Uri.parse(url));
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_SINGLE_TOP);
						WebAvtivity4Test.this.startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return true;
				}
				
				return false;
			}
			
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
			


			@Override
			public void onLoadResource(WebView view, String url) {

				super.onLoadResource(view, url);
			}


			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view,
					String url) {

				return super.shouldInterceptRequest(view, url);
			}

		});
		

		if (url.startsWith("openapp.jdmobile:")) {
			try {
				final Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(url));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				WebAvtivity4Test.this.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
		

		// WebView加载web资源
		webView.loadUrl(url);
		}
		
		LinearLayout linearLayout =new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		Button bu=new Button(this);
		bu.setText("reload");
		bu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				webView.loadUrl(url);
			}
		});
		linearLayout.addView(bu);
		linearLayout.addView(webView);

		setContentView(linearLayout);

	}

}
