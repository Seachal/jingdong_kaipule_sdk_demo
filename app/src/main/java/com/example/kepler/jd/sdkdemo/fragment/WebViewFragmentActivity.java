package com.example.kepler.jd.sdkdemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.sdkdemo.R;
import com.kepler.jd.Listener.LevelCallback;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.kepler.jd.sdk.bean.UrlConstant;
import com.kepler.jd.sdk.fragment.NewSuActivity;
import com.kepler.jd.sdk.fragment.WebViewFragment;

import org.json.JSONObject;

import java.io.Serializable;

/**
 *
 * 集成Fragment案例
 * Created by kepler on 2018/4/28.
 */
public class WebViewFragmentActivity extends NewSuActivity {

//    private String url = "https://m.jd.com/";  //首页
    /**
     * 京东微联URL
     */

//    private String url = "https:\\/\\/pro.m.jd.com\\/mall\\/active\\/2YuqFtp2HQTUjUYSXMUuWmSRoBjw\\/index.html";
//    private String finalUrl = "https:\\/\\/pro.m.jd.com\\/mall\\/active\\/2YuqFtp2HQTUjUYSXMUuWmSRoBjw\\/index.html";
//    private String url = "https://un.m.jd.com/cgi-bin/app/appjmp?wjmpkey=AAxsaXVqaW5saTA1NDEAcgBGQUFFQU1ManVjR19BZlJFZ2dNX3FEYk9DWEpwTEJPcFo4d0lHdWJwakwwS3duZ3hKVWVtSXNVNl9iRGZQWWoyUWM0emFKdw&to=https%3A%2F%2Fpro.m.jd.com%2Fmall%2Factive%2F2YuqFtp2HQTUjUYSXMUuWmSRoBjw%2Findex.html";
//    private String finalUrl = "https://pro.m.jd.com/mall/active/2YuqFtp2HQTUjUYSXMUuWmSRoBjw/index.html";
    /**
     * demo的url
     */
    private String url = "https://pro.m.jd.com/mall/active/2YuqFtp2HQTUjUYSXMUuWmSRoBjw/index.html";
    /**
     * 最终的落地页
     */
    private String finalUrl = "https://pro.m.jd.com/mall/active/2YuqFtp2HQTUjUYSXMUuWmSRoBjw/index.html";

    /**
     * 商品skuid
     */
    private String SKUID = "1032737";

    /**
     * 构造方法
     */
    public WebViewFragmentActivity(){
        super();
    }
    /**
     * 带参构造方法
     */
    public WebViewFragmentActivity(Activity this2) {
        super(this2);
    }

    /**
     * 生命周期函数-onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_new);
    }

    /**
     * 在此方法中自定义WebViewFragment
     */
    @Override
    protected void initWebViewFragment() {
        Intent intent = getIntent();
        KeplerAttachParameter auxiliary = new KeplerAttachParameter();
        Serializable serializableExtra = intent.getSerializableExtra(UrlConstant.EXTRA_Auxiliary);
        if(serializableExtra instanceof KeplerAttachParameter){
            auxiliary = (KeplerAttachParameter)serializableExtra;
        }

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        try{
            //普通接入方式(以首页为例)
//            String params = new JSONObject().put("type", "-1")
//                    .put(UrlConstant.URLFLAG_KEY, UrlConstant.URL_HomePage).toString();

            //搜索关键字接入方式
//            String params = new JSONObject().put("type", "-1")
//                    .put(UrlConstant.URLFLAG_KEY, UrlConstant.URL_Search)
//                    .put(UrlConstant.SEARCH_KEY, "小米6").toString();

            //通过url接入方式（外部链接接入方式）
            String params = new JSONObject().put("type", "-1")
                    .put(UrlConstant.URLFLAG_KEY, UrlConstant.URL_OuterLink)
                    .put(UrlConstant.URL, finalUrl)
                    .put(UrlConstant.FINAL_URL, finalUrl).toString();

            //商品详情接入方式
//            String params = new JSONObject()
//                    .put("type", "-1")
//                    .put(UrlConstant.URLFLAG_KEY, UrlConstant.URL_ProductDetail)
//                    .put("sku", (StringUtil.isEmpty(SKUID) ? "" : SKUID)).toString();

            params.replace(" ", "");

            //传递给Fragment的数据
            bundle.putSerializable(UrlConstant.EXTRA_Auxiliary, auxiliary); //用户传递
            bundle.putString(UrlConstant.EXTRA_PARAMS, params);  //用户传递
            bundle.putBoolean(UrlConstant.EXTRA_isGetTokenAcFinish, false);  //直接打开登录页标识
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            finish();
        }
        webViewFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, webViewFragment);
        webViewFragment.setTopLevelListener(new LevelCallback() {
            @Override
            public void callback(boolean isLevelTop) {
                if(isLevelTop){
                    //是第一级别
                }else{
                    //不是第一级别
                }
            }
        });
        mWebViewFragments.add(webViewFragment);
        fragmentTransaction.commit();
    }
}
