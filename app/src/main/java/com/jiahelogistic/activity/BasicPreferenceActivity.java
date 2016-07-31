package com.jiahelogistic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;

import com.jiahelogistic.JiaHeLogistic;

import java.util.Stack;

/**
 * Created by Li Huanling
 * On 2016/07/16 17:32
 *
 * A {@link android.preference.PreferenceActivity} which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
public abstract class BasicPreferenceActivity extends PreferenceActivity {

	private AppCompatDelegate mDelegate;

	/**
	 * activity栈管理
	 */
	protected Stack<Activity> stack;

	/**
	 * 全局应用
	 */
	protected JiaHeLogistic app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = JiaHeLogistic.getInstance();

		stack = app.getStack();

		loadToStack();
	}

	protected abstract void loadToStack();

	/**
	 * 设置ActionBar
	 * @param toolbar 工具栏
	 */
	public void setSupportActionBar(@Nullable Toolbar toolbar) {
		getDelegate().setSupportActionBar(toolbar);
	}

	/**
	 * @return The {@link AppCompatDelegate} being used by this Activity.
	 */
	@NonNull
	public AppCompatDelegate getDelegate() {
		if (mDelegate == null) {
			mDelegate = AppCompatDelegate.create(this, new AppCompatCallback() {
				@Override
				public void onSupportActionModeStarted(ActionMode mode) {

				}

				@Override
				public void onSupportActionModeFinished(ActionMode mode) {

				}

				@Nullable
				@Override
				public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
					return null;
				}
			});
		}
		return mDelegate;
	}

}
