package com.jiahelogistic.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jiahelogistic.R;

public class AboutUsActivity extends BasicAppCompatActivity {

	/**
	 * 版权信息
	 */
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 子类实现，用于activity栈管理
	 */
	@Override
	protected void loadToStack() {
		stack.push(this);
	}

	/**
	 * 初始化view
	 */
	@Override
	protected void initContentView() {
		setContentView(R.layout.activity_about_us);
	}

	/**
	 * 初始化控件
	 */
	@Override
	protected void initView() {
		textView = (TextView) findViewById(R.id.jh_preference_about_us_right);
		String right = getString(R.string.jh_preference_copyright);
		textView.setText(String.format(right, getString(R.string.jh_app_name), getString(R.string.jh_site_url)));
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
	protected void onDestroy() {
		super.onDestroy();
		stack.remove(this);
	}
}
