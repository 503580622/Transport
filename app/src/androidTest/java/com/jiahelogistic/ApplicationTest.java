package com.jiahelogistic;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.test.ApplicationTestCase;
import android.text.TextUtils;
import android.util.Log;

import com.jiahelogistic.utils.DataCleanManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
	private final static String TAG = "ApplicationTest";

	public ApplicationTest() {
		super(Application.class);
	}

	public void testDeviceInfo() {
		String deviceId = getDeviceInfo(getContext());
		Log.e(TAG, deviceId == null ? "none" : deviceId);
	}

	public static boolean checkPermission(Context context, String permission) {
		boolean result = false;
		if (Build.VERSION.SDK_INT >= 23) {
			try {
				int rest = context.checkSelfPermission(permission);
				if (rest == PackageManager.PERMISSION_GRANTED) {
					result = true;
				} else {
					result = false;
				}
			} catch (Exception e) {
				result = false;
			}
		} else {
			PackageManager pm = context.getPackageManager();
			if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
				result = true;
			}
		}
		return result;
	}
	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = null;
			if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
				device_id = tm.getDeviceId();
			}
			String mac = null;
			FileReader fstream = null;
			try {
				fstream = new FileReader("/sys/class/net/wlan0/address");
			} catch (FileNotFoundException e) {
				fstream = new FileReader("/sys/class/net/eth0/address");
			}
			BufferedReader in = null;
			if (fstream != null) {
				try {
					in = new BufferedReader(fstream, 1024);
					mac = in.readLine();
				} catch (IOException e) {
				} finally {
					if (fstream != null) {
						try {
							fstream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			json.put("mac", mac);
			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}
			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}
			json.put("device_id", device_id);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 测试计算缓存，清除缓存
	 */
	public void testCalcCache() {
		String cacheSize;
		try {
			cacheSize = DataCleanManager.getTotalCacheSize(getContext());
		} catch (Exception e) {
			cacheSize = "未知";
		}
		Log.e(TAG, "清除缓存前有:" + cacheSize);

		// 清除缓存
		DataCleanManager.clearAllCache(getContext());
		try {
			cacheSize = DataCleanManager.getTotalCacheSize(getContext());
		} catch (Exception e) {
			cacheSize = "未知";
		}
		Log.e(TAG, "清除缓存后有:" + cacheSize);
	}

	/**
	 * 测试Stack
	 */
	public void testStack() {
		Stack<String> stack = new Stack<>();
		stack.push("haha");
		stack.push("huohuo");
		Log.e(TAG, stack.firstElement());
	}
}