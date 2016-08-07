package com.jiahelogistic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jiahelogistic.JiaHeLogistic;
import com.umeng.analytics.MobclickAgent;

import java.util.Stack;

/**
 * Created by Li Huanling
 * On 2016/07/09 16:42.
 *
 * 普通界面公共父类
 */
public abstract class BasicAppCompatActivity extends AppCompatActivity {

	private String TAG = "BasicAppCompatActivity";

	/**
	 * 全局应用
	 */
	protected JiaHeLogistic app = JiaHeLogistic.getInstance();

	/**
	 * activity栈管理
	 */
	protected Stack<Activity> stack = app.getStack();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadToStack();

		initContentView();
		initView();
		setToolBar();
		setListener();
	}

	/**
	 * 子类实现，用于activity栈管理
	 */
	protected abstract void loadToStack();

	/**
	 * 初始化view
	 */
	protected abstract void initContentView();

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 设置监听器
	 */
	protected abstract void setListener();

	/**
	 * 自定义工具栏
	 */
	protected abstract void setToolBar();

	/**
	 * 友盟+Session统计
	 */
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	/**
	 * 友盟+Session统计
	 */
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
