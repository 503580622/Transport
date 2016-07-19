package com.jiahelogistic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jiahelogistic.JiaHeLogistic;
import com.umeng.analytics.MobclickAgent;

import java.util.Stack;

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
	}

	/**
	 * 子类实现，用于activity栈管理
	 */
	protected abstract void loadToStack();

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
