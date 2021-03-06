package com.jiahelogistic.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.jiahelogistic.JiaHeLogistic;
import com.jiahelogistic.R;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.NetUtils;
import com.jiahelogistic.widget.NormalPreference;
import com.jiahelogistic.utils.DataCleanManager;
import com.jiahelogistic.widget.CustomDialog;

/**
 * Created by Huanling on 2016/07/31 23:37.
 *
 * 设置片段
 */
public class SettingsFragment extends PreferenceFragment {

	/**
	 * 自定义设置
	 */
	private NormalPreference preference;

	/**
	 * 检查新版本
	 */
	private Preference upgrade;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

		preference = (NormalPreference) getPreferenceManager().findPreference("clean_cache");
		upgrade = getPreferenceManager().findPreference("upgrade");

		preference.setOnClickListener(new NormalPreference.OnClickListener() {
			@Override
			public void click(NormalPreference normalPreference) {
				DataCleanManager.clearAllCache(getActivity());
				normalPreference.calCacheValue();
			}
		});

		upgrade.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// 开启查询界面
				Dialog dialog = CustomDialog.createCommonWaitDialog(getActivity(), getActivity().getString(R.string.jh_preference_upgrading));
				dialog.show();

				BasicNetworkHandler handler = new BasicNetworkHandler(JiaHeLogistic.getInstance());
				handler.setDialog(dialog);

				// 检查更新
				NetUtils.checkUpdate(handler);
				return false;
			}
		});
	}

}
