package com.jiahelogistic.utils;

import android.app.Activity;

import com.jiahelogistic.JiaHeLogistic;

/**
 * Created by Li Huanling on 2016/7/12 0012.
 * 应用通用工具类
 */
public class Utils {
	public static final void Exit() {
		JiaHeLogistic app = JiaHeLogistic.getInstance();
		for (Activity activity : app.getStack()) {
			if (activity != null) {
				activity.finish();
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
}
