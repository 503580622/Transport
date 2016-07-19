package com.jiahelogistic.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Li Huanling
 * On 2016/7/18 01:12.
 *
 * 普通工具类
 */
public class Utils {
	/**
	 * 提示用户，如有需要，方便自定义
	 * @param c 上下文
	 * @param msg 提示内容
	 * @param duration 显示持续时间
	 */
	public static final void showToast(Context c, String msg, int duration) {
		Toast.makeText(c, msg, duration).show();
	}

	/**
	 * 返回当前程序版本号
	 *
	 * @param context 上下文
	 * @return 版本号
	 */
	@SuppressWarnings("Unused")
	public static String getAppVersionName(Context context) {
		String versionName = "";
		int versioncode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
}
