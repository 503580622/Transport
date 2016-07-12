package com.jiahelogistic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jiahelogistic.BasicActivity;
import com.jiahelogistic.R;
import com.jiahelogistic.config.SystemConfig;

/**
 * splash界面，默认展示信息，3秒钟后跳转主界面
 */
public class SplashActivity extends BasicActivity {

	/**
	 * 标志
	 */
	private final String TAG = "Splash_ACTIVITY";

	/**
	 * 设置自动跳转主界面的时间（默认3秒）
	 */
	private static final int AUTO_START_MAIN_ACTIVITY = 3000;


	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case SystemConfig.MAIN_ACTIVITY:
					// 进入主activity
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(intent);
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Thread() {
			@Override
			public void run() {
				super.run();
				Message msg = Message.obtain();
				msg.what = SystemConfig.MAIN_ACTIVITY;
				mHandler.sendMessageDelayed(msg, AUTO_START_MAIN_ACTIVITY);
			}
		}.run();
	}

	@Override
	protected void LoadToStack() {
		stack.push(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stack.pop();
	}
}
