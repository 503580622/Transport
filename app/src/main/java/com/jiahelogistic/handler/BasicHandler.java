package com.jiahelogistic.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.jiahelogistic.JiaHeLogistic;
import com.jiahelogistic.R;
import com.jiahelogistic.config.NetConfig;
import com.jiahelogistic.utils.Utils;

/**
 * Created by Li Huanling on 2016/7/16 0016.
 * 基础的消息处理
 */
public class BasicHandler extends Handler {

	/**
	 * 上下文
	 */
	protected Context mContext;

	private JiaHeLogistic app = JiaHeLogistic.getInstance();

	public BasicHandler(Context c) {
		mContext = c;

	}

	@Override
	public void handleMessage(Message msg) {
		Log.e("BasicHandler", app.getStack().pop().toString());
		if (msg.what == NetConfig.STATUS_NO_INTERNET_CONNECTION) {
			Utils.showToast(app.getStack().firstElement(), app.getStack().firstElement().getString(R.string.wl_no_internet_connect), Toast.LENGTH_SHORT);
		} else if (msg.what == NetConfig.STATUS_HTTP_NOT_FOUND) {
			Utils.showToast(app.getStack().firstElement(), app.getStack().firstElement().getString(R.string.wl_url_not_found), Toast.LENGTH_SHORT);
		}
	}
}
