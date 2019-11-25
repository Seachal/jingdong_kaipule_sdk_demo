package com.example.kepler.jd.sdkdemo;

import android.os.Bundle;

import com.example.sdkdemo.R;

/**
 * Created by genglei5 on 2018/3/26.
 */

public class StartActivityForSDK extends BaseStartActivityForSDK {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewsIDs=new int[]{
                R.id.qb_button,
                R.id.item_detail, R.id.item_detail_app,//单品
                R.id.list_page, R.id.list_page_app,//
                R.id.url_detail_union,//推广

                R.id.url_to, R.id.url_to_app,//任意jd地址
                R.id.url_to_web,//纯净webview
                R.id.serch_for, R.id.serch_for_app,//搜索
                R.id.home_page, R.id.home_page_app,//首页
                R.id.out_page,//外部地址  返回地址需跳转
                //我的
                R.id.get_cart, R.id.get_cart_app,//购物车
                R.id.get_order, R.id.get_order_app,//订单
                R.id.get_afterSale, R.id.get_afterSale_app,//我的售后

                R.id.add_sku_button, R.id.dele_sku_button,//（添加购物车）添加，删除SKU
                R.id.addSKU2cart,//添加购物车
                R.id.addSKUToCart, //添加购物车(新)
                R.id.show_token,//展示授权
                R.id.url_auth,//登录

                R.id.addSKU2cart_openOrderConfirmationPage,//直接购买 （暂时不支持）
                R.id.setting_art_button,//设置参数  全局
                R.id.setting_etc_button,//设置参数  全局
                R.id.setting_JDappBackTagID_button,//设置参数  全局
                R.id.setting_appkey2_button,//设置参数  全局

                R.id.setting_positionId_button,//设置参数  即时参数（广告ID）
                R.id.setting_customerInfo_button,//设置参数  即时参数（customerInfo）

                R.id.show_customerInfo_button,
                R.id.clear_customerInfo_button,
                R.id.btn_fragment,
                R.id.btn_tab
        };
        super.onCreate(savedInstanceState);
    }
}
