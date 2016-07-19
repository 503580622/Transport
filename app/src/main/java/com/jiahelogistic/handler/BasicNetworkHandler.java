package com.jiahelogistic.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jiahelogistic.JiaHeLogistic;
import com.jiahelogistic.R;
import com.jiahelogistic.bean.UpgradeBean;
import com.jiahelogistic.config.NetConfig;
import com.jiahelogistic.config.SystemConfig;
import com.jiahelogistic.net.NetUtils;
import com.jiahelogistic.utils.Utils;

/**
 * @author  Li Huanling.
 * 2016/7/16 21:23
 * 基础的网络消息处理
 */
public class BasicNetworkHandler extends Handler {

	/**
	 * 全局应用
	 */
	protected JiaHeLogistic app;

	public BasicNetworkHandler(JiaHeLogistic app) {
		this.app = app;
	}

	@Override
	public void handleMessage(Message msg) {
		// 设置已经提示用户网络状态
		app.setHasPromptNetwork(true);

		switch (msg.what) {
			case NetConfig.STATUS_NO_INTERNET_CONNECTION:
				// 没有连接网络
				Utils.showToast(app.getStack().firstElement(), app.getString(R.string.jh_state_no_internet), Toast.LENGTH_SHORT);
				break;

			case NetConfig.STATUS_HTTP_TIMEOUT:
				// 连接超时
				Utils.showToast(app.getStack().firstElement(), app.getString(R.string.jh_state_http_timeout), Toast.LENGTH_SHORT);
				break;

			case NetConfig.STATUS_HTTP_NOT_FOUND:
				// 404
				Utils.showToast(app.getStack().firstElement(), app.getString(R.string.jh_state_http_not_found), Toast.LENGTH_SHORT);
				break;

			default:
		}
	}
}
