package com.jiahelogistic.activity;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jiahelogistic.R;
import com.jiahelogistic.fragment.SettingsFragment;

/**
 * Created by Li Huanling
 * On 2016/07/18 22:59
 *
 * 设置界面
 */
public class SettingsActivity extends BasicAppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.e("SettingActivity", stack.toString());
	}

	/**
	 * 初始化view
	 */
	@Override
	protected void initContentView() {
		setContentView(R.layout.activity_settings);
	}

	/**
	 * 初始化控件
	 */
	@Override
	protected void initView() {
		// 初始化Toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.jh_common_activity_toolbar);
		setSupportActionBar(toolbar);

		// 设置界面
		getFragmentManager().beginTransaction()
				.replace(R.id.jh_ll_settings_content, new SettingsFragment())
				.commit();
	}

	/**
	 * 设置监听器
	 */
	@Override
	protected void setListener() {

	}

	/**
	 * 自定义工具栏
	 */
	@Override
	protected void setToolBar() {
		
	}

	@Override
	protected void loadToStack() {
		Log.e("SettingActivity", "Pushed");
		stack.push(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stack.remove(this);
	}
}
