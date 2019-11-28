package com.example.kepler.jd.sdkdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.login.KeplerApiManager;



public class BaseApplication extends Application {

	// 需要改成给您的 appKey 还有keySecret
	// 京东开普勒
//	public static final String appKey = "85c191682c36444cac39c72567201e95";
//	public static final String keySecret = "ba3dc750f6ee44eaba1bce8b4732383c";

	public static final String appKey = "7ac94770f7574f00a401657d8c9ac40d";
	public static final String keySecret = "8f53126d9a5041bcbe58947f147ff443";


	// 预发测试demo
	// public static final String appKey = "b72def22cc8c40b89f2512440d8e6de3";
	// public static final String keySecret = "dbb0ce9ff69e4bab82913784316509aa";



	public static String ticket,type;//QB流程
	
	

	@Override
	public void onCreate() {
		Log.i("kepler", "app pro on  creat");
		super.onCreate();
		Log.e("kepler", "app pro:" + getCurProcessName(this) + "  "
				+ getApplicationContext());

		// // 注册 这里需要校验 您的证书(shh)，还有包名（package name）,请保证lib工程safe图片是您的
		 KeplerApiManager.asyncInitSdk(BaseApplication.this, appKey, keySecret,
		 new AsyncInitListener() {
		
		 @Override
		 public void onSuccess() {
		 	Log.e("kepler", "Kepler asyncInitSdk onSuccess ");
		 }
		
		 @Override
		 public void onFailure() {

		 Log.e("kepler",
		 "Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
		
		 }
		 });
	}

	private String getCurProcessName(Context context) {
		int pid = android.os.Process.myPid();
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
				.getRunningAppProcesses()) {
			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

}
