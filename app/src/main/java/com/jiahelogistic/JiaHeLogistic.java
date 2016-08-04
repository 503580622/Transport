package com.jiahelogistic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.jiahelogistic.handler.CrashHandler;

import java.io.File;
import java.util.Stack;

/**
 * Created by Li Huanling
 * On 2016/7/11 20:59.
 *
 * 定义全局常量，处理全局异常
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

	/**
	 * 是否有网络
	 */
	private boolean hasNetwork = true;

	/**
	 * 是否已经提示用户网络状态，启动检查提示一次就可以，以后网络状态有变化需再次提示
	 */
	private boolean hasPromptNetwork = false;

	/**
	 * 新版本
	 */
	private File file;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		mContext = getApplicationContext();

		mStack = new Stack<>();

		file = new File(getFilesDir().getAbsolutePath() + "/new_apk.apk");

		// 注册全局异常处理器
		//CrashHandler crashHandler = CrashHandler.getInstance();
		//crashHandler.init(getApplicationContext());
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

	/**
	 * 获取网络状态
	 * @return 网络状态
	 */
	public boolean isHasNetwork() {
		return hasNetwork;
	}

	/**
	 * 设置网络状态
	 * @param hasNetwork 网络状态
	 */
	public void setHasNetwork(boolean hasNetwork) {
		this.hasNetwork = hasNetwork;
	}

	/**
	 * 是否已经提示用户网络状态
	 * @return 是否提示
	 */
	public boolean isHasPromptNetwork() {
		return hasPromptNetwork;
	}

	/**
	 * 设置是否提示用户网络状态
	 * @param hasPromptNetwork 是否提示
	 */
	public void setHasPromptNetwork(boolean hasPromptNetwork) {
		this.hasPromptNetwork = hasPromptNetwork;
	}

	public File getUpgradeFile() {
		return file;
	}
}
