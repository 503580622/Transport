package com.jiahelogistic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.jiahelogistic.R;
import com.jiahelogistic.config.SystemConfig;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.NetUtils;

/**
 * @author Li Huanling
 * 2016/07/12 23:15
 * splash界面
 */
public class SplashAppCompatActivity extends BasicAppCompatActivity {

	/**
	 * 标志
	 */
	private static final String TAG = "Splash_ACTIVITY";

	/**
	 * 设置自动跳转主界面的时间（默认3秒）
	 */
	private static final int AUTO_START_MAIN_ACTIVITY = 3000;

	private final BasicNetworkHandler mHandler = new BasicNetworkHandler() {
		@Override
		public void handleMessage(Message msg) {
			// 处理网络连接失败
			super.handleMessage(msg);

			switch (msg.what) {
				case SystemConfig.SYSTEM_MAIN_ACTIVITY:
					// 进入主activity
					Intent intent = new Intent(SplashAppCompatActivity.this, MainAppCompatActivity.class);
					startActivity(intent);
					// 不加入activity栈中，防止在主界面后退回到此界面
					SplashAppCompatActivity.this.finish();
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// 开启新线程
		new Thread() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				msg.what = SystemConfig.SYSTEM_MAIN_ACTIVITY;
				mHandler.sendMessageDelayed(msg, AUTO_START_MAIN_ACTIVITY);

				// TODO 其他网络需要
				// 检查是否有网络
				app.setHasNetwork(NetUtils.checkNet(SplashAppCompatActivity.this));
				// 检查更新
				NetUtils.checkUpdate(mHandler);
			}
		}.start();
	}

	/**
	 * 启动主activity后结束自己
	 */
	@Override
	protected void loadToStack() {	}
}
