package com.jiahelogistic.activity;

import android.os.Bundle;
import android.os.Handler;

import com.jiahelogistic.BasicActivity;
import com.jiahelogistic.R;

/**
 * splash界面，默认展示信息，3秒钟后跳转主界面
 */
public class SplashActivity extends BasicActivity {

	/**
	 * 设置自动跳转主界面的时间（默认3秒）
	 */
	private static final int AUTO_START_MAIN_ACTIVITY = 3000;


	private final Handler mHideHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);


	}
}
