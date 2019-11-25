package com.example.kepler.jd.sdkdemo.fragment;

import android.support.v4.app.FragmentManager;

import com.kepler.jd.sdk.fragment.BasePagerAdapter;
import com.kepler.jd.sdk.fragment.WebViewFragment;

import java.util.List;

/**
 * 自定义Adapter
 * Created by kepler on 2018/5/15.
 */

public class MyPagerAdapter extends BasePagerAdapter {
    /**
     * 构造方法
     * @param fm
     * @param NumOfTabs Tab数目
     * @param webViewFragments 所有WebViewFragment
     * @throws Exception
     */
    public MyPagerAdapter(FragmentManager fm, int NumOfTabs, List<WebViewFragment> webViewFragments) throws Exception {
        super(fm, NumOfTabs, webViewFragments);
    }
}
