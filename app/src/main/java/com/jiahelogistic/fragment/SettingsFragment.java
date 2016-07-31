package com.jiahelogistic.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.jiahelogistic.R;

/**
 * Created by Huanling
 * On 2016/07/31 23:37.
 *
 * 设置片段
 */
public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}
}
