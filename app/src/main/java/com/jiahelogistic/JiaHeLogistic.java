package com.jiahelogistic;

import android.app.Application;
import android.content.Context;

import com.jiahelogistic.crash.CrashHandler;

/**
 * Created by Li Huanling on 2016/7/11 0011.
 * 定义全局常量，处理全局异常
 *
 * @version 1.0
 */
public class JiaHeLogistic extends Application {
	/**
	 * 应用上下文
	 */
	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();

		mContext = getApplicationContext();

		// 注册全局异常处理器
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	public static Context getContext() {
		return mContext;
	}
}
