package com.example.kepler.jd.sdkdemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.kepler.jd.sdkdemo.fragment.TabFragmentActivity;
import com.example.kepler.jd.sdkdemo.fragment.WebViewFragmentActivity;
import com.example.sdkdemo.R;
import com.kepler.jd.Listener.ActionCallBck;
import com.kepler.jd.Listener.LoginListener;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.kepler.jd.sdk.bean.KeplerGlobalParameter;
import com.kepler.jd.sdk.bean.UrlConstant;
import com.kepler.jd.sdk.exception.KeplerBufferOverflowException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseStartActivityForSDK extends Activity implements OnClickListener {
    /**
     * 测试链接
     */
    public final static String oneDealUrl = "http://union.click.jd.com/jdc?p=AyIOZRprFQoSAlcZWCVGTV8LRGtMR1dGXgVF" +
            "SR1JUkpJBUkcU0QLTh9HRwwHXRteFwARGAxeB0gMVQsQDAFBSkVEC0dXZUNTcRFFBEFaakIBR2tOX1RkHUU5XWFuVyIYC00AZFsJXidlDh43VhleHA" +
            "YSB1UfaxUFFjdlfSYlVHwHVBpaFAMTBFASaxQyEgJRHV4cBBoFVBNfEjIVNwpPHkFSUFMdRR9AUkw3ZRo%3D&t=W1dCFBBFC14NXAAECUteDEYWRQ5RUFcZVRNbEAAQBEpC" +
            "HklfHEBZXkxPVlpQFkUHGXJTRiNfBUpWSn8QTwc%3D&e=25840255236224";
    /**
     * 外部链接
     */
    public final static String outhome = "http://www.smzdm.com/p/7392200/?be_invited_by=7906932675";
    /**
     * m首页
     */
    public final static String mhome = "http://m.jd.com";

//    public final static String openUrl = "https://item.m.jd.com/product/10380794260.html";
    /**
     * 售后链接
     */
    public final static String afterSaleUrl = "https://tuihuan.jd.com/afs/orders";
    /**
     * 超时时间设定
     */
    public static final int timeOut = 15;

    /**
     * 直接登录
     */
    View login;
    /**
     * 应用上下文
     */
    Context mContext;
    /**
     * 是否登录与取消登录视图容器
     */
    LinearLayout login_lin;
    /**
     * 显示多少个SKU
     */
    TextView show_sku_textview;
    /**
     * 存储skuid和对应的数量
     */
    Map<String, Integer> skuMap = new HashMap<String, Integer>();
    /**
     * skuid
     */
    String[] skus;
    /**
     * sku number
     */
    int[] numbers;

    protected int[] viewsIDs;

    Handler mHandler;

    /**
     * 一键加车传入skuId,unionId,webId,refer
     */
    private EditText et_skuid, et_unionid, et_webid, et_refer ;


    /**
     * 这个是即时性参数  可以设置
     */
    KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();

    /**
     * 请求权限 READ_PHONE_STATE
     */
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;


    OpenAppAction mOpenAppAction = new OpenAppAction() {
        @Override
        public void onStatus(final int status) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (status == OpenAppAction.OpenAppAction_start) {//开始状态未必一定执行，
                        dialogShow();
                    } else {
                        mKelperTask = null;
                        dialogDiss();
                    }
                }
            });
        }
    };
    /**
     * 加载对话框
     */
    LoadingDialog dialog;

    /**
     * 显示加载对话框
     */
    private void dialogShow() {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
//            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mKelperTask != null) {//取消
                        mKelperTask.setCancel(true);
                    }
                }
            });
        }
        dialog.show();
    }

    private void dialogDiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 网络请求对象
     */
    KelperTask mKelperTask;


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_page: {
                try {
                    KeplerApiManager.getWebViewService().openJDUrlWebViewPage(mhome, mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;


            case R.id.out_page: {
                KeplerGlobalParameter.getSingleton().setGoBackIgnoredUrl(new String[]{"https://www.linkstars.com/click.php", "https://www.linkstars.com/click.php"});
                try {
                    KeplerApiManager.getWebViewService().openJDUrlWebViewPage(outhome, mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;

            case R.id.home_page_app: {
                try {
                    mKelperTask = KeplerApiManager.getWebViewService().openJDUrlPage(mhome, mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;


            case R.id.item_detail: {// 商品详情 SKU直接打开
                EditText d = (EditText) findViewById(R.id.item_edit);
                String info = d.getEditableText().toString().trim();
                if ("".equals(info)) {
                    info = "2857483";
                }

                try {
                    KeplerApiManager
                            .getWebViewService()
                            .openItemDetailsWebViewPage(info,
                                    mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                    Toast.makeText(mContext, "传参出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            break;


            case R.id.item_detail_app: {// 商品详情 SKU直接打开
                EditText d = (EditText) findViewById(R.id.item_edit);
                String info = d.getEditableText().toString().trim();
                if ("".equals(info)) {
                    info = "2857483";
                }
                try {
                    mKelperTask = KeplerApiManager
                            .getWebViewService()
                            .openItemDetailsPage(info,
                                    mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                    Toast.makeText(mContext, "传参出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.list_page: {// 导航页面
                try {
                    KeplerApiManager.getWebViewService().openNavigationWebViewPage(
                            mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                    Toast.makeText(mContext, "传参出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
            break;

            case R.id.list_page_app: {// 导航页面
                try {
                    mKelperTask = KeplerApiManager.getWebViewService().openNavigationPage(
                            mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                    Toast.makeText(mContext, "传参出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.url_detail_union: {// union 地址
                try {
                    KeplerApiManager
                            .getWebViewService()
                            .openJDUrlWebViewPage(oneDealUrl,
                                    mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;


            case R.id.get_cart: {// 打开购物车
                try {
                    KeplerApiManager.getWebViewService().openCartWebViewPage(
                            mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;

            case R.id.get_cart_app: {// 打开购物车
                try {
                    mKelperTask = KeplerApiManager.getWebViewService().openCartPage(
                            mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.get_order: {// 用户订单页面
                try {
                    KeplerApiManager.getWebViewService().openOrderListWebViewPage(
                            mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;


            case R.id.get_order_app: {// 用户订单页面
                try {
                    mKelperTask = KeplerApiManager.getWebViewService().openOrderListPage(
                            mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;


            case R.id.get_afterSale: {// 用户售后
                try {
                    KeplerApiManager.getWebViewService().openJDUrlWebViewPage(afterSaleUrl,
                            mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;


            case R.id.get_afterSale_app: {// 用户
                try {
                    mKelperTask = KeplerApiManager.getWebViewService().openJDUrlPage(afterSaleUrl,
                            mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;


            case R.id.show_token: {// 展示token
                KeplerApiManager.getWebViewService().checkLoginState(new ActionCallBck() {
                    @Override
                    public boolean onDateCall(int key, String info) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "已登录", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    }

                    @Override
                    public boolean onErrCall(int key, String error) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "未登录", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    }
                });

            }
            break;
            case R.id.url_auth: {// 取消用户登录
                KeplerApiManager.getWebViewService().cancelAuth(mContext);
                login_lin.setVisibility(View.INVISIBLE);

            }
            break;

            case R.id.btn_fragment:
                Intent intent1 = new Intent();
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra(UrlConstant.EXTRA_Auxiliary, mKeplerAttachParameter);
                intent1.setClass(mContext, WebViewFragmentActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_tab:
                Intent intent2 = new Intent();
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra(UrlConstant.EXTRA_Auxiliary, mKeplerAttachParameter);
                intent2.setClass(mContext, TabFragmentActivity.class);
                startActivity(intent2);
                break;

            case R.id.addSKU2cart: {// 添加到购物车

                if (skuMap.size() == 0) {
                    Toast.makeText(mContext, "请添加SKU", Toast.LENGTH_SHORT).show();
                    return;
                }

                getSKUArray();

                KeplerApiManager.getWebViewService().add2Cart(BaseStartActivityForSDK.this, skus, numbers, new ActionCallBck() {

                    @Override
                    public boolean onDateCall(int key, String info) {
                        Log.w("add2cartOK", key + " " + info);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                                checkLoginStatus();
                            }
                        });

                        return false;
                    }

                    @Override
                    public boolean onErrCall(final int key, final String error) {
                        Log.d("kepler", "add2cart err：" + key + " " + error);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,
                                        "添加失败:" + key, Toast.LENGTH_SHORT).show();
                            }
                        });

                        switch (key) {
                            case KeplerApiManager.KeplerApiManagerActionErr:
                            case KeplerApiManager.KeplerApiManagerActionServerErr:
                                // 操作失败
                                break;
                            case KeplerApiManager.KeplerApiManagerActionErr_CartFullErr:
                                // 购物车上限
                                break;
                            case KeplerApiManager.KeplerApiManagerActionErr_DataErr:
                            case KeplerApiManager.KeplerApiManagerActionErr_ParameterErr:
                            case KeplerApiManager.KeplerApiManagerActionErr_ParserErr:
                            case KeplerApiManager.KeplerApiManagerActionErr_TokenLast:
                            case KeplerApiManager.NetLinker_Err_Not_200:
                                // 服务端出错
                                break;
                            case KeplerApiManager.KeplerApiManagerActionErr_AppKeyNotExist:// app_key不存在
                            case KeplerApiManager.KeplerApiManagerActionErr_AppKeyErr:// 无效app_key
                            case KeplerApiManager.KeplerApiManagerActionErr_AppKeyLast:// 缺少app_key参数
                                break;
                            case KeplerApiManager.KeplerApiManagerActionErr_TokenNotExist:
                            case KeplerApiManager.KeplerApiManagerActionErr_UNLogin:
                            case KeplerApiManager.KeplerApiManagerActionErr_TokenTimeOutTErr:
                                // KeplerApiManager.getWebViewService().login(
                                // StartActivityForSDK.this,
                                // mLoginListener);
                                break;
                            case KeplerApiManager.NetLinker_Err_NoNetwork:
                                // 没有网络
                                break;
                            case KeplerApiManager.NetLinker_Err_UnsupportedEncodingException:
                            case KeplerApiManager.NetLinker_Err_IOException:
                            case KeplerApiManager.NetLinker_Err_ClientProtocolException:
                            case KeplerApiManager.NetLinker_Err_NetException:
                                // 网络访问出错
                                break;
                            case KeplerApiManager.KeplerApiManagerAdd2CartErr_NoLogin:
                                //调用加车接口失败，失败原因：未登录状态
                                break;

                            default:
                                break;
                        }

                        return true;
                    }
                });
            }
            break;
            case R.id.addSKUToCart:{
                String skuId = et_skuid.getText().toString();
                String unionId = et_unionid.getText().toString();
                String webId = et_webid.getText().toString();
                String refer = et_refer.getText().toString();

                if(skuId == null || "".equals(skuId) ||skuId.length() == 0){
                    Toast.makeText(mContext, "skuId不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(unionId == null || "".equals(unionId)){
                    Toast.makeText(mContext, "unionId不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(webId == null || "".equals(webId)){
                    Toast.makeText(mContext, "webId不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(refer == null || "".equals(refer)){
                    Toast.makeText(mContext, "refer不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }

                KeplerApiManager.getWebViewService().addToCart(BaseStartActivityForSDK.this,
                        unionId,
                        webId,
                        skuId,
                        refer, new ActionCallBck() {
                            @Override
                            public boolean onDateCall(int key, final String info) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
                                        checkLoginStatus();
                                    }
                                });
                                return false;
                            }

                            @Override
                            public boolean onErrCall(final int key, final String error) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "加入购物车失败, code:" +key + "errorMessage:" + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });
            }
            break;

//		case R.id.addSKU2cart_openOrderConfirmationPage: {// 打开添加到购物车，直接购买页面
//
//			if (skuMap.size() == 0) {
//				Toast.makeText(mContext, "请添加SKU", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			getSKUArray();
//			try {
//				KeplerApiManager.getWebViewService().add2CartAndOpenPayPage(
//						skus, numbers, mKeplerAttachParameter);
//			} catch (KeplerBufferOverflowException e) {
//
//				e.printStackTrace();
//			}
//		}
//			break;

            case R.id.url_to: {// 打开任意页面

                EditText d = (EditText) findViewById(R.id.url_edit);
                d.setText("https://union-click.jd.com/jdc?e=&p=AyIGZRtaFAUbBFcSXBYyFwdQGVsUBREEVhxrUV1KWQorAlBHU0VeBUVOWk1RAk8ECllHGAdFBwtaV1MJBAJQXk8JF0EfGQcSAlcbWhIBEQRSDBsZdXtzPGwSdkZlBS14LEx9RXE8QiJ2W3FbIRk7cnthQVxsMk9hZnQcXiwXakFwIXAfdnBmYjV4MFV1WnMNbAJmQmR%2BPXwidgJFYB18JHV2YkUCTTBecRN0EXtZYVFyZFVbLHd9YGcMbAd2AGZUIng8V2FrdzFvOHJCZn4qayJmaU1%2FLG89YnFtRyFyP3V7cGdBGS4lQlRCVGUEQXB3YxFgG0ZSdV8HBTpcRB4LZRtTFwcWBl0dWBUyEgZUG1IdAxMGUitbFgAXA1IdaxUHIkY7G1ISCxUHZRprFQYUAlYbXRwKFwFUHWsVChU3JX4ld3tq0%2Bq1j4mjxo7lK2slASI3ZRtYJQIiWBFGBiUAEwZXGQ%3D%3D");
//                 d.setText("https://u.jd.com/3apcAG");
                String info = d.getEditableText().toString();
                if (TextUtils.isEmpty(info)) {
                    return;
                }
                if (!info.startsWith("http")) {
                    info = "http://" + info;
                }
                try {
                    KeplerApiManager.getWebViewService().openJDUrlWebViewPage(info,
                            mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;

            case R.id.url_to_app: {// 打开任意页面

                EditText d = (EditText) findViewById(R.id.url_edit);
                d.setText("https://union-click.jd.com/jdc?e=&p=AyIGZRtaFAUbBFcSXBYyFwdQGVsUBREEVhxrUV1KWQorAlBHU0VeBUVOWk1RAk8ECllHGAdFBwtaV1MJBAJQXk8JF0EfGQcSAlcbWhIBEQRSDBsZdXtzPGwSdkZlBS14LEx9RXE8QiJ2W3FbIRk7cnthQVxsMk9hZnQcXiwXakFwIXAfdnBmYjV4MFV1WnMNbAJmQmR%2BPXwidgJFYB18JHV2YkUCTTBecRN0EXtZYVFyZFVbLHd9YGcMbAd2AGZUIng8V2FrdzFvOHJCZn4qayJmaU1%2FLG89YnFtRyFyP3V7cGdBGS4lQlRCVGUEQXB3YxFgG0ZSdV8HBTpcRB4LZRtTFwcWBl0dWBUyEgZUG1IdAxMGUitbFgAXA1IdaxUHIkY7G1ISCxUHZRprFQYUAlYbXRwKFwFUHWsVChU3JX4ld3tq0%2Bq1j4mjxo7lK2slASI3ZRtYJQIiWBFGBiUAEwZXGQ%3D%3D");
//                 d.setText("https://u.jd.com/3apcAG");
                String info = d.getEditableText().toString();
                if (TextUtils.isEmpty(info)) {
                    return;
                }
                if (!info.startsWith("http")) {
                    info = "http://" + info;
                }

                try {
                    mKelperTask = KeplerApiManager.getWebViewService().openJDUrlPage(info,
                            mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.url_to_web: {

                EditText d = (EditText) findViewById(R.id.url_edit);
                d.setText("https://union-click.jd.com/jdc?e=&p=AyIGZRtaFAUbBFcSXBYyFwdQGVsUBREEVhxrUV1KWQorAlBHU0VeBUVOWk1RAk8ECllHGAdFBwtaV1MJBAJQXk8JF0EfGQcSAlcbWhIBEQRSDBsZdXtzPGwSdkZlBS14LEx9RXE8QiJ2W3FbIRk7cnthQVxsMk9hZnQcXiwXakFwIXAfdnBmYjV4MFV1WnMNbAJmQmR%2BPXwidgJFYB18JHV2YkUCTTBecRN0EXtZYVFyZFVbLHd9YGcMbAd2AGZUIng8V2FrdzFvOHJCZn4qayJmaU1%2FLG89YnFtRyFyP3V7cGdBGS4lQlRCVGUEQXB3YxFgG0ZSdV8HBTpcRB4LZRtTFwcWBl0dWBUyEgZUG1IdAxMGUitbFgAXA1IdaxUHIkY7G1ISCxUHZRprFQYUAlYbXRwKFwFUHWsVChU3JX4ld3tq0%2Bq1j4mjxo7lK2slASI3ZRtYJQIiWBFGBiUAEwZXGQ%3D%3D");

                //                 d.setText("https://u.jd.com/3apcAG");
                String info = d.getEditableText().toString();
                if (TextUtils.isEmpty(info)) {
                    return;
                }

                if (!info.startsWith("openapp") && !info.startsWith("http")) {
                    info = "http://" + info;
                }

                Intent in = new Intent(mContext, WebAvtivity4Test.class);
                in.putExtra("url", info);
                startActivity(in);

            }
            break;

            case R.id.serch_for: {// 打开搜索关键词页面


                EditText sku_edit = (EditText) findViewById(R.id.serch_key_edit);
                String info = sku_edit.getEditableText().toString();
                if (TextUtils.isEmpty(info)) {
                    Toast.makeText(mContext, "输入关键字", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(info)) {
                    info = "小米6";
                }
                try {
                    KeplerApiManager.getWebViewService().openSearchWebViewPage(
                            info, mKeplerAttachParameter);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                }
            }
            break;

            case R.id.serch_for_app: {// 打开搜索关键词页面

                EditText d = (EditText) findViewById(R.id.serch_key_edit);
                String info = d.getEditableText().toString();
                if (TextUtils.isEmpty(info)) {
                    info = "小米6";
                }
                try {
                    mKelperTask = KeplerApiManager.getWebViewService().openSearchPage(
                            info, mKeplerAttachParameter, this, mOpenAppAction, timeOut);
                } catch (KeplerBufferOverflowException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.add_sku_button: {// 添加SKU


                EditText sku_edit = (EditText) findViewById(R.id.sku_edit);
                String info = sku_edit.getEditableText().toString();
                if (TextUtils.isEmpty(info)) {
                    Toast.makeText(mContext, "输入SKU", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("kepler", info);

                EditText sku_number_edit = (EditText) findViewById(R.id.sku_number_edit);
                String sku_number_edit_info = sku_number_edit.getEditableText()
                        .toString();
                if (TextUtils.isEmpty(sku_number_edit_info)) {
                    Toast.makeText(this, "输入SKU", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer last = skuMap.get(info);
                if (last != null && last > 0) {
                    last += Integer.parseInt(sku_number_edit_info);
                } else {
                    last = Integer.parseInt(sku_number_edit_info);
                }
                skuMap.put(info, last);
                show_sku_textview.setText(skuMap.size() + "个SKU");

            }
            break;

            case R.id.dele_sku_button: {
                skuMap.clear();
                show_sku_textview.setText(skuMap.size() + "个SKU");
            }
            break;

            case R.id.qb_button: {
                String info = ((EditText) findViewById(R.id.test_edit)).getEditableText().toString();
                info = info.replace("\\", "");
                try{
                    int st = info.indexOf("{");
                    int end = info.indexOf("}");
                    info = info.substring(st, end + Toast.LENGTH_SHORT);
                    Log.d("kepler", info);
                }catch (StringIndexOutOfBoundsException e){
                    e.printStackTrace();
                    Toast.makeText(mContext, "设置(qb版本使用)内容不合法", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONObject bb = new JSONObject(info);
                    String ticket = bb.optString("ticket");
                    if(ticket instanceof String){
                        String tick = (String)ticket;
                        String type = bb.optString("type");

                        if ("".equals(type) || "".equals(tick)) {
                            Toast.makeText(mContext, "没有设置 ticket  null", Toast.LENGTH_SHORT).show();
                        } else {
                            MyApplication.ticket = tick;
                            MyApplication.type = type;
                            Toast.makeText(mContext, "设置OK", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "没有设置json", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.setting_appkey2_button: {
                String info = ((EditText) findViewById(R.id.setting_appkey2_edit)).getEditableText().toString();
                if ("".equals(info)) {
                    Toast.makeText(mContext, "is null ", Toast.LENGTH_SHORT).show();
                    return;
                }
                KeplerGlobalParameter.getSingleton().setVirtualAppkey(info);
            }
            break;


            case R.id.setting_JDappBackTagID_button: {
                String info = ((EditText) findViewById(R.id.setting_JDappBackTagID_edit)).getEditableText().toString();
                KeplerGlobalParameter.getSingleton().setJDappBackTagID(info);
            }
            break;

            case R.id.setting_art_button: {
                String info = ((EditText) findViewById(R.id.setting_crt_edit)).getEditableText().toString();
                KeplerGlobalParameter.getSingleton().setActId(info);
            }
            break;

            case R.id.setting_etc_button: {
                String info = ((EditText) findViewById(R.id.setting_etc_edit)).getEditableText().toString();
                KeplerGlobalParameter.getSingleton().setExt(info);
            }
            break;

            case R.id.setting_positionId_button: {
                String info = ((EditText) findViewById(R.id.setting_positionId_edit)).getEditableText().toString();
                if (!"".equals(info)) {
                    try {
                        try {
                            int kk = Integer.parseInt(info);
                            mKeplerAttachParameter.setPositionId(kk);
                        } catch (KeplerBufferOverflowException e) {
                            e.printStackTrace();
                            Toast.makeText(BaseStartActivityForSDK.this, "参数传递异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Throwable e) {
                        Toast.makeText(BaseStartActivityForSDK.this, "参数传递异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BaseStartActivityForSDK.this, "参数不能为null", Toast.LENGTH_SHORT).show();
                }


            }
            break;


            //========================================================
//$$isInJDMode&&+{..{
// $$isInJDMode&&            case R.id.setting_injd_Mopenbp_button: {
// $$isInJDMode&&                String info = ((EditText) findViewById(R.id.setting_Mopenbp_edit)).getEditableText().toString();
// $$isInJDMode&&                String index_info = ((EditText) findViewById(R.id.setting_Mopenbp_index_edit)).getEditableText().toString();
// $$isInJDMode&&                try{
// $$isInJDMode&&                    int index=Integer.parseInt(index_info);
// $$isInJDMode&&                    try {
// $$isInJDMode&&                        mKeplerAttachParameter.setInJDMopenbp(index,info);
// $$isInJDMode&&                    } catch (KeplerBufferOverflowException e) {
// $$isInJDMode&&                        e.printStackTrace();
// $$isInJDMode&&                        Toast.makeText(StartActivityForSDK.this, "参数传递异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
// $$isInJDMode&&                    }
// $$isInJDMode&&                }catch (Exception e){
// $$isInJDMode&&                    e.printStackTrace();
// $$isInJDMode&&                    Toast.makeText(StartActivityForSDK.this, "mopenbp index 输入异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
// $$isInJDMode&&                }
// $$isInJDMode&&
// $$isInJDMode&&            }
// $$isInJDMode&&            break;
            //$$isInJDMode&&+}..}
//========================================================

            case R.id.setting_customerInfo_button: {
                String info = ((EditText) findViewById(R.id.setting_customerInfo_edit)).getEditableText().toString();

                try {
                    mKeplerAttachParameter.setCustomerInfo(info);
                } catch (KeplerBufferOverflowException e) {
                    e.printStackTrace();
                    Toast.makeText(BaseStartActivityForSDK.this, "参数传递异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.show_customerInfo_button: {
                String info = mKeplerAttachParameter.getShowInfo();

                Toast.makeText(BaseStartActivityForSDK.this, info, Toast.LENGTH_LONG).show();
            }
            break;

            case R.id.clear_customerInfo_button: {
                mKeplerAttachParameter.reset();
            }
            break;


            default:
                break;
        }

    }

    /**
     *
     */
    private void getSKUArray() {

        skus = new String[skuMap.size()];
        numbers = new int[skuMap.size()];

        Iterator<String> it = skuMap.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            skus[i] = it.next();
            numbers[i] = skuMap.get(skus[i]);
            i++;
        }
    }

    LoginListener mLoginListener = new LoginListener() {

        @Override
        public void authSuccess() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "login成功", Toast.LENGTH_SHORT).show();
                    checkLoginStatus();
                }
            });

        }

        @Override
        public void authFailed(final int errorCode) {
            switch (errorCode) {
                case KeplerApiManager.KeplerApiManagerLoginErr_Init:// 初始化失败
                    break;
                case KeplerApiManager.KeplerApiManagerLoginErr_InitIng:// 初始化没有完成
                    break;
                case KeplerApiManager.KeplerApiManagerLoginErr_openH5authPageURLSettingNull:// 跳转url
                    break;
                case KeplerApiManager.KeplerApiManagerLoginErr_getTokenErr:// 获取失败(oath授权之后，获取cookie过程出错)
                    break;
                case KeplerApiManager.KeplerApiManagerLoginErr_User_Cancel:// 用户取消
                    break;
                case KeplerApiManager.KeplerApiManagerLoginErr_AuthErr_ActivityOpen:// 打开授权页面失败
                    break;
                default:
                    break;
            }
            if(!(errorCode == KeplerApiManager.KeplerApiManagerLoginErr_LoginNoConfirm||
                    errorCode == KeplerApiManager.KeplerApiManagerLoginErr_TokenNoUse)){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, errorCode + ":authFailed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    ToggleButton togglebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mHandler = new Handler();
        setContentView(R.layout.mall_union_jingdong_main);

        showJarInfo();// for 开普勒SDK开发 检查SDKjar引用

        for (int id : viewsIDs) {
            findViewById(id).setOnClickListener(this);
        }

        show_sku_textview = (TextView) findViewById(R.id.show_sku_textview);
        et_skuid = (EditText)findViewById(R.id.et_skuid);
        et_unionid = (EditText)findViewById(R.id.et_unionid);
        et_webid = (EditText)findViewById(R.id.et_webid);
        et_refer = (EditText)findViewById(R.id.et_refer);

        login = findViewById(R.id.login);
        login_lin = (LinearLayout) findViewById(R.id.login_lin);

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeplerApiManager.getWebViewService().checkLoginState(new ActionCallBck() {
                    @Override
                    public boolean onDateCall(int key, String info) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "已登录", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    }

                    @Override
                    public boolean onErrCall(int key, String error) {
                        // 直接授权登录京东​
                        KeplerApiManager.getWebViewService().login(
                                BaseStartActivityForSDK.this, mLoginListener);
                        return false;
                    }
                });
            }
        });

        //201版本添加
        togglebutton = (ToggleButton) findViewById(R.id.setting_forceH5_button);
        togglebutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (togglebutton.isChecked()) {
                    Toast.makeText(mContext, "强制H5", Toast.LENGTH_SHORT).show();
                    KeplerGlobalParameter.getSingleton().setIsOpenByH5Mode(true);
                } else {
                    Toast.makeText(mContext, "不强制H5,线上配置决定", Toast.LENGTH_SHORT).show();
                    KeplerGlobalParameter.getSingleton().setIsOpenByH5Mode(false);
                }
            }
        });

    }

    static boolean isShowDie = false;
    /**
     * skuid
     */
    static int sku = 1514842;


    @Override
    protected void onResume() {
        super.onResume();
        checkLoginStatus();

        if (KeplerGlobalParameter.getSingleton().isOpenByH5Mode()) {
            togglebutton.setChecked(true);
        }

        {
            KeplerApiManager.setD(isShowDie);
            if (isShowDie) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            KeplerApiManager.getWebViewService().openItemDetailsWebViewPage("" + sku++, mKeplerAttachParameter);
                        } catch (KeplerBufferOverflowException e) {

                            e.printStackTrace();
                        }

                    }
                }, 1000);
            }
        }
    }

    /**
     * 调用验证登录态接口判断是否登录状态
     */
    private void checkLoginStatus() {
        KeplerApiManager.getWebViewService().checkLoginState(new ActionCallBck() {
            @Override
            public boolean onDateCall(int key, String info) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        login_lin.setVisibility(View.VISIBLE);
                    }
                });
                return false;
            }

            @Override
            public boolean onErrCall(int key, String error) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        login_lin.setVisibility(View.INVISIBLE);
                    }
                });
                return false;
            }
        });

    }

    // ================================================

    /**
     * 设置sdk显示的版本号和是否使用sdk混淆加密等信息
     */
    private void showJarInfo() {
        String loadCalssName = "com.kepler.jd.sdk.util.Base64";
        boolean isSDKHX = false;
        try {
            Class c = Class.forName(loadCalssName);
            isSDKHX = (c == null);
        } catch (ClassNotFoundException e) {

            // e.printStackTrace();
            isSDKHX = true;
        }

        TextView show_jar_time_for_dev = (TextView) findViewById(R.id.show_jar_time_for_dev);
        if (show_jar_time_for_dev == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("SDK版本：").append(KeplerApiManager.getKeplerVersion())
                .append("\n").append((!isSDKHX ? "未使用SDK混淆加密" : "混淆"));
        sb.append("\n").append("for:" + KeplerApiManager.getMode());

        show_jar_time_for_dev.setVisibility(View.VISIBLE);
        show_jar_time_for_dev.setText(sb.toString());
    }
}

/**
 * 京东  优惠券链接
 *
 * ## 京东开普勒
 *
 * 长链接”clickURL”: “https://union-click.jd.com/jdc?e=&p=AyIGZRtaFAUbBFcSXBYyFwdQGVsUBREEVhxrUV1KWQorAlBHU0VeBUVOWk1RAk8ECllHGAdFBwtaV1MJBAJQXk8JF0EfGQcSAlcbWhIBEQRSDBsZdXtzPGwSdkZlBS14LEx9RXE8QiJ2W3FbIRk7cnthQVxsMk9hZnQcXiwXakFwIXAfdnBmYjV4MFV1WnMNbAJmQmR%2BPXwidgJFYB18JHV2YkUCTTBecRN0EXtZYVFyZFVbLHd9YGcMbAd2AGZUIng8V2FrdzFvOHJCZn4qayJmaU1%2FLG89YnFtRyFyP3V7cGdBGS4lQlRCVGUEQXB3YxFgG0ZSdV8HBTpcRB4LZRtTFwcWBl0dWBUyEgZUG1IdAxMGUitbFgAXA1IdaxUHIkY7G1ISCxUHZRprFQYUAlYbXRwKFwFUHWsVChU3JX4ld3tq0%2Bq1j4mjxo7lK2slASI3ZRtYJQIiWBFGBiUAEwZXGQ%3D%3D”,
 *
 *
 * 短连接"shortURL": "https://u.jd.com/3apcAG"
 */
