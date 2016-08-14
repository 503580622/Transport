package com.jiahelogistic.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jiahelogistic.JiaHeLogistic;
import com.jiahelogistic.net.NetUtils;

/**
 * Created by Li Huanling on 2016/07/18 23:46.
 *
 * 网络状态监听
 */
public class NetStateBroadcast extends BroadcastReceiver {

	private JiaHeLogistic app;

	public NetStateBroadcast() {
		app = JiaHeLogistic.getInstance();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("NetStateBroadcast", intent.getAction());

		// 网络状态有改变
		String action = intent.getAction();
		if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
			app.setHasNetwork(NetUtils.checkNet(context));

			// 重置是否提示用户网络状态
			app.setHasPromptNetwork(false);
		}
	}
}
