package com.jiahelogistic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Stack;

public abstract class BasicActivity extends AppCompatActivity {
	/**
	 * activity栈管理
	 */
	protected Stack<Activity> stack;

	private String TAG = "BASIC_ACTIVITY";

	/**
	 * 全局应用
	 */
	protected JiaHeLogistic app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 获取APP
		app = JiaHeLogistic.getInstance();

		// 获取stack
		stack = app.getStack();

		LoadToStack();
	}

	/**
	 * 子类实现，用于activity栈管理
	 */
	protected abstract void LoadToStack();

	/**
	 * 友盟+Session统计
	 */
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(stack.firstElement());
	}

	/**
	 * 友盟+Session统计
	 */
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(stack.firstElement());
	}
}
