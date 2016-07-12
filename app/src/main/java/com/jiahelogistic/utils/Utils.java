package com.jiahelogistic.utils;

/**
 * Created by Li Huanling on 2016/7/12 0012.
 * 应用通用工具类
 */
public class Utils {
	public static final void Exit() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
}
