package com.jiahelogistic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.jiahelogistic.handler.CrashHandler;

import java.util.Stack;

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

	/**
	 * 应用程序栈管理
	 */
	private Stack<Activity> mStack;

	/**
	 * 自己引用
	 */
	private static JiaHeLogistic instance;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		mContext = getApplicationContext();

		mStack = new Stack<>();

		// 注册全局异常处理器
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	public Stack<Activity> getStack() {
		return mStack;
	}

	public static Context getContext() {
		return mContext;
	}

	/**
	 * 返回实例
	 * @return JiaHeLogistic
	 */
	public static JiaHeLogistic getInstance() {
		return instance;
	}
}
