package com.jiahelogistic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.jiahelogistic.R;
import com.jiahelogistic.fragment.SampleSlide;

public class IntroAppActivity extends AppIntro {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置提示文字
		setDoneText(getString(R.string.jh_intro_done));
		setSkipText(getString(R.string.jh_intro_skip));

		addSlide(SampleSlide.newInstance(R.layout.intro1));
		addSlide(SampleSlide.newInstance(R.layout.intro2));
		addSlide(SampleSlide.newInstance(R.layout.intro3));
		addSlide(SampleSlide.newInstance(R.layout.intro4));
	}

	private void loadMainActivity(){
		// 获取升级信息
		Bundle bundle = getIntent().getExtras();
		Intent intent = new Intent(this, MainAppCompatActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}

	@Override
	public void onSkipPressed(Fragment currentFragment) {
		super.onSkipPressed(currentFragment);
		loadMainActivity();
	}

	public void getStarted(View v){
		loadMainActivity();
	}

	@Override
	public void onDonePressed(Fragment currentFragment) {
		super.onDonePressed(currentFragment);
		loadMainActivity();
	}

}
