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

	private JiaHeLogistic app = JiaHeLogistic.getInstance();

	public BasicNetworkHandler() {
	}

	@Override
	public void handleMessage(Message msg) {
		Log.e("BasicNetworkHandler", app.getStack().firstElement().toString());

		// 设置已经提示用户网络状态
		app.setHasPromptNetwork(true);

		if (msg.what == NetConfig.STATUS_NO_INTERNET_CONNECTION) {
			// 没有连接网络
			Utils.showToast(app.getStack().firstElement(), app.getString(R.string.jh_state_no_internet), Toast.LENGTH_SHORT);
		} else if (msg.what == NetConfig.STATUS_HTTP_TIMEOUT) {
			// 连接超时
			Utils.showToast(app.getStack().firstElement(), app.getString(R.string.jh_state_http_timeout), Toast.LENGTH_SHORT);
		} else if (msg.what == NetConfig.STATUS_HTTP_NOT_FOUND) {
			// 404
			Utils.showToast(app.getStack().firstElement(), app.getString(R.string.jh_state_http_not_found), Toast.LENGTH_SHORT);
		} else if (msg.what == SystemConfig.SYSTEM_CHECK_UPGRADE) {
			UpgradeBean bean = (UpgradeBean)msg.obj;
			String versionName = Utils.getAppVersionName(app);
			if (!TextUtils.equals(versionName, bean.getVersionName())) {
				// TODO 有更新可用，判断是否在主页面，是就弹出升级提醒，否则就等待进入主页面再提醒
			}
		}
	}
}
