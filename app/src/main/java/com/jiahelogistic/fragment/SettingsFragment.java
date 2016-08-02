package com.jiahelogistic.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jiahelogistic.R;
import com.jiahelogistic.preference.NormalPreference;
import com.jiahelogistic.utils.DataCleanManager;
import com.jiahelogistic.utils.Utils;

/**
 * Created by Huanling
 * On 2016/07/31 23:37.
 *
 * 设置片段
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

	/**
	 * 自定义设置
	 */
	private NormalPreference preference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

		preference = (NormalPreference) getPreferenceManager().findPreference("clean_cache");
		preference.setOnClickListener(new NormalPreference.OnClickListener() {
			@Override
			public void click(NormalPreference normalPreference) {
				DataCleanManager.clearAllCache(getActivity());
			}
		});

		preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object o) {
				return false;
			}
		});
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
		if ("clean_cache".equals(s)) {
			DataCleanManager.clearAllCache(getActivity());
			preference.calCacheValue();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();

		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
}
