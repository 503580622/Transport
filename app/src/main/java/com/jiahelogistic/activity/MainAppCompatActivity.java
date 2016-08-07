package com.jiahelogistic.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.jiahelogistic.R;
import com.jiahelogistic.bean.KeyValue;
import com.jiahelogistic.bean.UpgradeBean;
import com.jiahelogistic.config.SystemConfig;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.FileManager;
import com.jiahelogistic.utils.Utils;
import com.jiahelogistic.widget.CustomDialog;

import java.io.File;

/**
 * Created by Li Huanling
 * On 2016/07/17 16:23
 * <p/>
 * 主界面
 */
public class MainAppCompatActivity extends BasicAppCompatActivity {

	/**
	 * 设置标识
	 */
	private static final String TAG = "MainAppCompatActivity";

	/**
	 * 3秒内按2次后退退出应用
	 */
	private static final int EXIT_APP_TIMEOUT = 3000;

	/**
	 * 退出标志
	 */
	private boolean exitFlag = false;

	/**
	 * 基本处理器
	 */
	private BasicNetworkHandler mHandler = new BasicNetworkHandler(app) {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case SystemConfig.SYSTEM_RESET_EXIT_FLAG:
					exitFlag = false;
					break;

				default:
					// 父类处理
					super.handleMessage(msg);
					break;
			}
		}
	};

	/**
	 * 设置Tab名资源数组
	 */
	private static String[] mTabTitles = new String[]{"首页", "我的"};

	/**
	 * 设置Tab图片资源数组
	 */
	private static int[] mTabViewIds = new int[]{R.drawable.ic_home_black_18dp, R.drawable.ic_person_black_24dp};

	/**
	 * 设置布局资源数组
	 */
	private static int[] mLayoutIds = new int[]{R.layout.fragment_main, R.layout.fragment_ucenter};

	/**
	 * 标签布局
	 */
	private TabLayout tabLayout;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	/**
	 * 工具栏
	 */
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// 处理升级信息
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			boolean isNeedUpgrade = bundle.getBoolean("isNeedUpgrade");
			if (isNeedUpgrade) {
				final UpgradeBean upgradeBean = bundle.getParcelable("upgrade");
				assert upgradeBean != null;
				Log.e(TAG, upgradeBean.toString());
				// 升级提示
				CustomDialog.upgradeProgressDialog(this, upgradeBean, mHandler);
			}
		}
		//uploadFile();
	}

	/**
	 * 初始化view
	 */
	@Override
	protected void initContentView() {
		setContentView(R.layout.activity_main);
	}

	/**
	 * 初始化控件
	 */
	@Override
	protected void initView() {
		toolbar = (Toolbar) findViewById(R.id.jh_common_activity_toolbar);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.jh_main_activity_container);

		tabLayout = (TabLayout) findViewById(R.id.jh_main_activity_tabs);

		// 设置数据适配器
		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return PlaceholderFragment.newInstance(MainAppCompatActivity.this, position);
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
			changeSelectedTab(view, i, i == 0);
			tab.setCustomView(view);
		}
	}

	/**
	 * 设置监听器
	 */
	@Override
	protected void setListener() {
		// 设置tab点击事件
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				// 设置工具栏
				toolbar.setTitle(mTabTitles[tab.getPosition()]);

				View view = tab.getCustomView();
				changeSelectedTab(view, tab.getPosition(), true);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				View view = tab.getCustomView();
				changeSelectedTab(view, tab.getPosition(), false);
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}

	/**
	 * 自定义工具栏
	 */
	@Override
	protected void setToolBar() {
		// 初始化设置工具栏
		toolbar.setTitle(mTabTitles[0]);


		setSupportActionBar(toolbar);
	}

	/**
	 * 修改tab样式
	 *
	 * @param view       自定义的tab界面
	 * @param position   位置
	 * @param isSelected 是否选择
	 */
	private void changeSelectedTab(View view, int position, boolean isSelected) {

		ImageView imageView = (ImageView) view.findViewById(R.id.jh_tab_item_image_view);
		TextView textView = (TextView) view.findViewById(R.id.jh_tab_item_text_view);

		if (isSelected) {
			// 设置选中tab的图片
			imageView.setImageResource(mTabViewIds[position]);
			// 设置选中tab的文字
			textView.setText(mTabTitles[position]);


			// 设置被选中的样式
			imageView.setBackgroundColor(Color.argb(255, 0, 0, 255));
			textView.setTextColor(Color.RED);

			mViewPager.setCurrentItem(position);
		} else {
			// 设置选中tab的图片
			imageView.setImageResource(mTabViewIds[position]);
			// 设置选中tab的文字
			textView.setText(mTabTitles[position]);

			// 设置没被选中的样式
			imageView.setBackgroundColor(Color.WHITE);
			imageView.setImageResource(mTabViewIds[position]);

			textView.setTextColor(Color.BLACK);
		}
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
	 *
	 * @param file 需要上传的文件
	 */
	private void beginUpload(File file) throws Exception {
		FileManager.asynPost(file, new KeyValue[]{new KeyValue("haha", "123"), new KeyValue("Test", "myTest")}, mHandler);
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
	 * Take care of popping the fragment back stack or finishing the activity
	 * as appropriate.
	 */
	@Override
	public void onBackPressed() {
		if (exitFlag) {
			Utils.Exit();
		} else {
			// 显示提示信息
			String string = getString(R.string.jh_app_exit);
			Utils.showToast(this, String.format(string, getString(R.string.jh_app_name)), Toast.LENGTH_SHORT);

			// 设置退出标志
			exitFlag = true;

			// 如果指定时间内没有再按后退按钮，则清除退出标志
			Message msg = Message.obtain();
			msg.what = SystemConfig.SYSTEM_RESET_EXIT_FLAG;
			mHandler.sendMessageDelayed(msg, EXIT_APP_TIMEOUT);
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
		 * 主界面
		 */
		private static MainAppCompatActivity activity;


		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(MainAppCompatActivity activity, int sectionNumber) {
			PlaceholderFragment.activity = activity;
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
			// 获得参数
			Bundle args = getArguments();
			int sectionNumber = args.getInt(ARG_SECTION_NUMBER);


			View rootView = inflater.inflate(mLayoutIds[sectionNumber],
					container, false);

			if (sectionNumber == 1) {
				ImageView imageView = (ImageView) rootView.findViewById(R.id.jh_btn_ucenter_setting);
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// 启动设置界面
						Intent intent = new Intent(activity, SettingsActivity.class);
						activity.startActivity(intent);
					}
				});
			}
			return rootView;
		}
	}
}
