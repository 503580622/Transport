package com.jiahelogistic.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiahelogistic.R;
import com.jiahelogistic.fragment.SettingsFragment;
import com.jiahelogistic.utils.Utils;

/**
 * Created by Li Huanling on 2016/07/18 22:59
 *
 * 设置界面
 */
public class SettingsActivity extends BasicAppCompatActivity {

	/**
	 * 工具栏
	 */
	private Toolbar toolbar;

	/**
	 * 退出按钮
	 */
	private Button exitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		toolbar = (Toolbar) findViewById(R.id.jh_common_activity_toolbar);
		exitButton = (Button) findViewById(R.id.jh_settings_activity_exit);
		exitButton.setText(String.format(getString(R.string.jh_btn_settings_exit),
				getString(R.string.jh_app_name)));

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
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Utils.Exit();
			}
		});
	}

	/**
	 * 自定义工具栏
	 */
	@Override
	protected void setToolBar() {
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
		setSupportActionBar(toolbar);
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
