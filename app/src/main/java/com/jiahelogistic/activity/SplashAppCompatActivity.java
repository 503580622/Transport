package com.jiahelogistic.activity;

import android.content.Intent;
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
 * @author Li Huanling
 * 2016/07/12 23:15
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
	private static final int AUTO_START_MAIN_ACTIVITY = 3000;

	private final BasicNetworkHandler mHandler = new BasicNetworkHandler(app) {
		/**
		 * 设置是否在主界面提示升级界面
		 */
		private boolean isNeedUpgrade = false;

		/**
		 * 升级信息，在isNeedUpgrade为true时有用
		 */
		private UpgradeBean bean;

		@Override
		public void handleMessage(Message msg) {
			// 处理网络连接失败
			super.handleMessage(msg);

			switch (msg.what) {
				case SystemConfig.SYSTEM_MAIN_ACTIVITY:
					// 进入主activity
					Intent intent = new Intent(SplashAppCompatActivity.this,
							MainAppCompatActivity.class);

					// 是否需要升级
					if (isNeedUpgrade) {
						Bundle bundle = new Bundle();
						bundle.putBoolean("isNeedUpgrade", isNeedUpgrade);
						bundle.putParcelable("upgrade", bean);
						intent.putExtras(bundle);
					}

					startActivity(intent);
					// 不加入activity栈中，防止在主界面后退回到此界面
					SplashAppCompatActivity.this.finish();
					break;

				case SystemConfig.SYSTEM_CHECK_UPGRADE:
					UpgradeBean bean = (UpgradeBean)msg.obj;
					String versionName = Utils.getAppVersionName(this.app);
					if (!TextUtils.equals(versionName, bean.getVersionName())) {
						// TODO 有更新可用，判断是否在主页面，是就弹出升级提醒，否则就等待进入主页面再提醒
						Log.e(TAG, stack.firstElement().getClass().getSimpleName());
						String className = stack.firstElement().getClass().getSimpleName();
						if ("SplashAppCompatActivity".equals(className)) {
							// 处于启动界面，设置在主界面升级
							isNeedUpgrade = true;
							this.bean = bean;
						}
					}
					break;

				default:
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
	protected void loadToStack() {
		stack.push(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stack.pop();
	}
}
