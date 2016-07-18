package com.jiahelogistic.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/18 0018.
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
}
