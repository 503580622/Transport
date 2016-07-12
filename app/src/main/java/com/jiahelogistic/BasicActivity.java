package com.jiahelogistic;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Stack;

public abstract class BasicActivity extends AppCompatActivity {
	protected Stack<Activity> stack = new Stack<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LoadToStack();
	}

	/**
	 * 子类实现，用于activity栈管理
	 */
	protected abstract void LoadToStack();
}
