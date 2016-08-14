package com.jiahelogistic.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.jiahelogistic.bean.UpgradeBean;
import com.jiahelogistic.config.NetConfig;
import com.jiahelogistic.config.SystemConfig;
import com.jiahelogistic.handler.BasicNetworkHandler;

import java.util.HashMap;

/**
 * Created by Li Huanling on 2016/7/17 0017.
 *
 * 检查网络状态
 */
public class NetUtils {
	private static final String TAG = "NetUtils";

	/**
	 * 检查用户的网络:是否有网络
	 * @param context 上下文
	 * @return 是否有网络
	 */
	public static boolean checkNet(Context context) {
		// 判断：WIFI链接
		boolean isWIFI = isWIFIConnection(context);
		Log.e(TAG, "Wifi:" + Boolean.toString(isWIFI));
		// 判断：Mobile链接
		boolean isMOBILE = isMOBILEConnection(context);
		Log.e(TAG, "Mobile:" + Boolean.toString(isMOBILE));
		if (!isWIFI && !isMOBILE) {
			return false;
		}

		return true;
	}

	/**
	 * 判断：Mobile链接
	 *
	 * @param context 上下文
	 * @return 是否是手机网络
	 */
	private static boolean isMOBILEConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断：WIFI链接
	 *
	 * @param context 上下文
	 * @return 是否是Wifi
	 */
	private static boolean isWIFIConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 检查是否有更新
	 */
	public static final void checkUpdate(final BasicNetworkHandler handler) {
		VolleyManager volleyManager = VolleyManager.newInstance();
		volleyManager.GsonGetRequest("upgrade", NetConfig.URL_UPLOAD_APP, UpgradeBean.class,
				new Response.Listener<UpgradeBean>() {
					@Override
					public void onResponse(UpgradeBean response) {
						Log.e(TAG, response.toString());
						Message msg = Message.obtain();
						msg.what = SystemConfig.SYSTEM_CHECK_UPGRADE;
						msg.obj = response;
						handler.sendMessage(msg);
					}
				},
				new BasicResponseError(handler));
	}
}
