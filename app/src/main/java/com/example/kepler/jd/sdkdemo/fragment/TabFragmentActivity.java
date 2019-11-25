package com.example.kepler.jd.sdkdemo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.sdkdemo.R;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.kepler.jd.sdk.bean.UrlConstant;
import com.kepler.jd.sdk.fragment.NewSuActivity;
import com.kepler.jd.sdk.fragment.WebViewFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kepler on 2018/5/8.
 */
public class TabFragmentActivity extends NewSuActivity {
    /**
     * 首页
     */
    private String url = "https://m.jd.com/";
    /**
     * skuid
     */
    private String SKUID = "1032737";  //
    /**
     * ViewPager
     */
    private ViewPager viewPager;
    /**
     * 自定义Adapter
     */
    private MyPagerAdapter myPagerAdapter;

    /**
     * 构造方法
     */
    public TabFragmentActivity(){
        super();
    }

    /**
     * 构造方法
     * @param this2
     */
    public TabFragmentActivity(Activity this2) {
        super(this2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);

        //安全图片校验通过，才会初始化
        if(isLegal){

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("商品详情"));
            tabLayout.addTab(tabLayout.newTab().setText("首页"));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            viewPager = (ViewPager) findViewById(R.id.pager);

            try {
                myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), mWebViewFragments);
            } catch (Exception e) {
                e.printStackTrace();
                this.finish();
            }
            viewPager.setAdapter(myPagerAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    currentPos = position;
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }
    /**
     * 初始化Fragment
     */
    @Override
    protected void initWebViewFragment(){
        try {
            //传递给第一个Fragment的数据
            Bundle bundle0 = new Bundle();
            String params0 = new JSONObject()
                    .put("type", "-1")
                    .put(UrlConstant.URLFLAG_KEY, UrlConstant.URL_ProductDetail)
                    .put("sku", SKUID).toString();
            params0.replace(" ", "");
            KeplerAttachParameter auxiliary = null;
            Serializable serializableExtra = getIntent().getSerializableExtra(UrlConstant.EXTRA_Auxiliary);
            if(serializableExtra instanceof KeplerAttachParameter){
                auxiliary = (KeplerAttachParameter)serializableExtra;
            }
            if(auxiliary != null){
                bundle0.putSerializable(UrlConstant.EXTRA_Auxiliary, auxiliary); //用户传递
            }
            bundle0.putString(UrlConstant.EXTRA_PARAMS, params0);  //用户传递
            bundle0.putBoolean(UrlConstant.EXTRA_isGetTokenAcFinish, false);  //直接打开登录页标识true
            WebViewFragment mWebViewFragment1 = new WebViewFragment();
            mWebViewFragment1.setArguments(bundle0);
            mWebViewFragments.add(mWebViewFragment1);

            //传递给第二个Fragment的数据
            Bundle bundle1 = new Bundle();
            String params1 = new JSONObject().put("type", "-1")
                    .put(UrlConstant.URLFLAG_KEY, UrlConstant.URL_HomePage).toString();
            params1.replace(" ", "");
            if(auxiliary != null){
                bundle1.putSerializable(UrlConstant.EXTRA_Auxiliary, auxiliary); //用户传递
            }
            bundle1.putString(UrlConstant.EXTRA_PARAMS, params1);  //用户传递
            bundle1.putBoolean(UrlConstant.EXTRA_isGetTokenAcFinish, false);  //直接打开登录页标识true

            WebViewFragment mWebViewFragment2 = new WebViewFragment();
            mWebViewFragment2.setArguments(bundle1);
            mWebViewFragments.add(mWebViewFragment2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
