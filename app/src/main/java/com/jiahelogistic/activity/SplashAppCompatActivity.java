package com.jiahelogistic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.jiahelogistic.R;
import com.jiahelogistic.bean.UpgradeBean;
import com.jiahelogistic.config.SystemConfig;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.NetUtils;
import com.jiahelogistic.utils.Utils;

/**
 * Created by Li Huanling
 * On 2016/07/12 23:15
 *
 * splash界面
 */
public class SplashAppCompatActivity extends BasicAppCompatActivity {

	/**
	 * 标志
	 */
	private static final String TAG = "Splash_Activity";

	/**
	 * 设置自动跳转主界面的时间（默认3秒）
	 */
	private static final int AUTO_START_MAIN_ACTIVITY = 1000;

	private final BasicNetworkHandler mHandler = new BasicNetworkHandler(app) {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SystemConfig.SYSTEM_MAIN_ACTIVITY:
					// 进入主activity
					Intent intentMainActivity = new Intent(SplashAppCompatActivity.this,
							MainAppCompatActivity.class);

					myStartActivity(intentMainActivity);
					break;

				case SystemConfig.SYSTEM_VIEW_ACTIVITY:
					// 进入导航页
					Intent intentIntroActivity = new Intent(SplashAppCompatActivity.this,
							IntroAppActivity.class);

					myStartActivity(intentIntroActivity);
					break;

				default:
					// 处理网络连接失败
					super.handleMessage(msg);
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

				// 获取配置
				SharedPreferences sharedPreferences = getSharedPreferences(SystemConfig.CONFIG_NAME, MODE_PRIVATE);
				boolean runned = sharedPreferences.getBoolean("runned", false);
				if (!runned) {
					// 第一次运行应用，进入导航页
					msg.what = SystemConfig.SYSTEM_VIEW_ACTIVITY;

					// 保存
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putBoolean("runned", true);
					editor.commit();
				} else {
					// 进入主页面
					msg.what = SystemConfig.SYSTEM_MAIN_ACTIVITY;
				}

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
	protected void loadToStack() {
		stack.push(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stack.remove(this);
	}
}
