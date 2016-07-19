package com.jiahelogistic.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiahelogistic.R;
import com.jiahelogistic.bean.KeyValue;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.FileManager;
import com.jiahelogistic.widget.DialogFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by Li Huanling
 * On 2016/07/17 16:23
 *
 * 主界面
 */
public class MainAppCompatActivity extends BasicAppCompatActivity {

	private static final String TAG = "MainAppCompatActivity";

	private BasicNetworkHandler mHandler = new BasicNetworkHandler(app) {
		@Override
		public void handleMessage(Message msg) {
			// 优先交给父类处理
			super.handleMessage(msg);
		}
	};

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		// 处理升级信息
		Bundle bundle = getIntent().getExtras();
		boolean isNeedUpgrade = bundle.getBoolean("isNeedUpgrade");
		if (isNeedUpgrade) {
			Log.e(TAG, bundle.getParcelable("upgrade").toString());
			// 升级提示
			DialogFactory.createCommonRequestDialog(this, "我的宝贝乖老婆").show();
		}
		//uploadFile();
	}

	/**
	 * 子类实现，用于activity栈管理
	 */
	@Override
	protected void loadToStack() {
		stack.push(this);
	}

	private void uploadFile() {
		try {
			File file = getFilesDir();
			if (!file.exists()) {
				boolean result = file.createNewFile();
				if (!result) {
					Log.e(TAG, "create file failure");
				}
			} else {
				File uploadFile = new File(file.getAbsolutePath() + "/dump.md");
				if (!uploadFile.exists()) {
					boolean result = uploadFile.createNewFile();
					if (result) {
						beginUpload(uploadFile);
					}
				} else {
					// 开始上传
					if (uploadFile.isFile()) {
						beginUpload(uploadFile);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始上传
	 * @param file 需要上传的文件
	 */
	private void beginUpload(File file) throws Exception {
		FileManager.asynPost(file, new KeyValue[] {new KeyValue("haha", "123"), new KeyValue("Test", "myTest")}, mHandler);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "SECTION 1";
				case 1:
					return "SECTION 2";
				case 2:
					return "SECTION 3";
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";


		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			textView.setText(getString(R.string.jh_section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}
