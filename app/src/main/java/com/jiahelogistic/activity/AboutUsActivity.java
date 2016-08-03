package com.jiahelogistic.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jiahelogistic.R;

public class AboutUsActivity extends AppCompatActivity {

	/**
	 * 版权信息
	 */
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);

		textView = (TextView) findViewById(R.id.jh_preference_about_us_right);
		String right = getString(R.string.jh_preference_copyright);
		textView.setText(String.format(right, getString(R.string.jh_app_name), getString(R.string.jh_site_url)));
	}
}
