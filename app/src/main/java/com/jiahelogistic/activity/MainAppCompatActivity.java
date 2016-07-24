package com.jiahelogistic.activity;

import android.os.Bundle;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.jiahelogistic.R;
import com.jiahelogistic.bean.KeyValue;
import com.jiahelogistic.bean.UpgradeBean;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.FileManager;
import com.jiahelogistic.widget.DialogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li Huanling
 * On 2016/07/17 16:23
 *
 * 主界面
 */
public class MainAppCompatActivity extends BasicAppCompatActivity {

	/**
	 * 设置标识
	 */
	private static final String TAG = "MainAppCompatActivity";

	/**
	 * 基本处理器
	 */
	private BasicNetworkHandler mHandler = new BasicNetworkHandler(app) {
		@Override
		public void handleMessage(Message msg) {
			// 优先交给父类处理
			super.handleMessage(msg);
		}
	};

	/**
	 * 设置Tab名
	 */
	private String[] mTabTitles = new String[] {"首页", "我的"};

	/**
	 * 设置Tab图片资源
	 */
	private int[] mTabViewIds = new int[] {R.drawable.ic_home_black_18dp, R.drawable.ic_person_black_18dp};

	/**
	 * 标签布局
	 */
	private TabLayout tabLayout;

	/**
	 * 标签列
	 */
	private List<TabLayout.Tab> tabList;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabList = new ArrayList<>();

		Toolbar toolbar = (Toolbar) findViewById(R.id.jh_main_activity_toolbar);
		setSupportActionBar(toolbar);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.jh_main_activity_container);

		tabLayout  = (TabLayout) findViewById(R.id.jh_main_activity_tabs);

		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return PlaceholderFragment.newInstance(position);
			}

			@Override
			public int getCount() {
				return mTabTitles.length;
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return mTabTitles[position];
			}
		});

		tabLayout.setupWithViewPager(mViewPager);

		TabLayout.Tab tab;
		for (int i = 0; i < mTabTitles.length; i++) {
			// 新标签
			tab = tabLayout.getTabAt(i);
			View view = LayoutInflater.from(MainAppCompatActivity.this).inflate(R.layout.tab_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.jh_tab_item_image_view);
			imageView.setImageResource(mTabViewIds[i]);

			TextView textView = (TextView) view.findViewById(R.id.jh_tab_item_text_view);
			textView.setText(mTabTitles[i]);
			tab.setCustomView(view);
			tabList.add(tab);
		}

		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				View view = tab.getCustomView();
				ImageView imageView = (ImageView) view.findViewById(R.id.jh_tab_item_image_view);
				imageView.setImageResource(R.mipmap.ic_launcher);
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
//				for (TabLayout.Tab t : tabList) {
//					if (t == tab) {
//						t.setIcon(mTabViewIds[tab.getPosition()]);
//					}
//				}
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		tabLayout.setTabTextColors(R.color.colorAccent, R.color.colorPrimary);
		// 处理升级信息
		Bundle bundle = getIntent().getExtras();
		boolean isNeedUpgrade = bundle.getBoolean("isNeedUpgrade");
		if (isNeedUpgrade) {
			UpgradeBean upgradeBean = bundle.getParcelable("upgrade");
			assert upgradeBean != null;
			Log.e(TAG, upgradeBean.toString());
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
			// 根据标题数量
			return mTabTitles.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabTitles[position];
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
		}
		public PlaceholderFragment() {
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
