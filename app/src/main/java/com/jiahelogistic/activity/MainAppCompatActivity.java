package com.jiahelogistic.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jiahelogistic.R;
import com.jiahelogistic.bean.KeyValue;
import com.jiahelogistic.bean.UpgradeBean;
import com.jiahelogistic.config.SystemConfig;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.FileManager;
import com.jiahelogistic.net.ProgressDownloader;
import com.jiahelogistic.net.ProgressResponseBody;
import com.jiahelogistic.utils.Utils;
import com.jiahelogistic.widget.CustomDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Handler;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

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
	 * 基本处理器
	 */
	private BasicNetworkHandler mHandler = new BasicNetworkHandler(app) {
		@Override
		public void handleMessage(Message msg) {
			// 优先交给父类处理
			switch (msg.what) {
				case SystemConfig.UPDATE_DOWNLOAD_PROGRESS:
					String string = String.format(getString(R.string.jh_schedule_progress_content), msg.arg1) + "%";
					textView.setText(string);
					break;
				default:
					super.handleMessage(msg);
					break;
			}
		}
	};

	/**
	 * 设置Tab名
	 */
	private String[] mTabTitles = new String[]{"首页", "我的"};

	/**
	 * 设置Tab图片资源
	 */
	private int[] mTabViewIds = new int[]{R.drawable.ic_home_black_18dp, R.drawable.ic_person_black_24dp};

	/**
	 * 标签布局
	 */
	private TabLayout tabLayout;

	/**
	 * 断点记录
	 */
	private long breakPoints;

	/**
	 * 下载辅助类
	 */
	private ProgressDownloader downloader;

	/**
	 * 下载的文件
	 */
	private File file;

	/**
	 * 已下载文件长度
	 */
	private long totalBytes;

	/**
	 * 文件总长度
	 */
	private long contentLength;

	/**
	 * 进度条
	 */
	private ProgressBar progressBar;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.jh_main_activity_toolbar);
		setSupportActionBar(toolbar);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.jh_main_activity_container);

		tabLayout = (TabLayout) findViewById(R.id.jh_main_activity_tabs);

		// 设置数据适配器
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
			changeSelectedTab(view, i, i == 0 ? true : false);
			tab.setCustomView(view);
		}

		// 设置tab点击事件
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
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

		// 处理升级信息
		Bundle bundle = getIntent().getExtras();
		boolean isNeedUpgrade = bundle.getBoolean("isNeedUpgrade");
		if (isNeedUpgrade) {
			final UpgradeBean upgradeBean = bundle.getParcelable("upgrade");
			assert upgradeBean != null;
			Log.e(TAG, upgradeBean.toString());
			// 升级提示
			CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setTitle("有可用的升级！");
			builder.setMessage(upgradeBean.getDescription());
			builder.setPositiveButton("马上升级", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					// 开启下载新版本
					dialogInterface.dismiss();
					startDownloadFile(upgradeBean.getUrl());
				}
			});
			builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					dialogInterface.dismiss();
					Toast.makeText(MainAppCompatActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
				}
			});
			builder.create().show();
		}
		//uploadFile();
	}

	/**
	 * 开始下载文件
	 *
	 * @param url 新文件下载地址
	 */
	private void startDownloadFile(String url) {
		assert url == null;
		// 创建新的对话框
		Dialog dialog = CustomDialog.createCommonScheduleProgressDialog(this, "已下载");

		// 进度条
		progressBar = (ProgressBar) dialog.findViewById(R.id.jh_dialog_schedule_progressBar);

		// 文字信息
		textView = (TextView) dialog.findViewById(R.id.jh_dialog_schedule_content);

		// 开始下载，清空下载记录
		breakPoints = 0L;
		file = new File(getFilesDir().getAbsolutePath() + "/newVersion.apk");
		downloader = new ProgressDownloader(url, file, new ProgressResponseBody.ProgressListener() {

			/**
			 * 在开始下载前调用，设置文件的长度，只调用一次
			 *
			 * @param contentLength 文件长度
			 */
			@Override
			public void onPreExecute(long contentLength) {
				MainAppCompatActivity.this.contentLength = contentLength;
				Log.e(TAG, String.valueOf(contentLength));
				progressBar.setMax((int) (contentLength / 1024));
			}

			/**
			 * 更新文件下载进度
			 *
			 * @param totalBytes 已下载的长度
			 * @param done       是否完成
			 */
			@Override
			public void update(long totalBytes, boolean done) {
				totalBytes = totalBytes + breakPoints;
				MainAppCompatActivity.this.totalBytes = totalBytes;
				progressBar.setProgress((int) (totalBytes / 1024));

				// 通知主线程更新文本进度
				Message msg = Message.obtain();
				msg.what = SystemConfig.UPDATE_DOWNLOAD_PROGRESS;
				msg.arg1 = (int) Math.floor(totalBytes * 100 / contentLength);
				mHandler.sendMessage(msg);
				Log.e(TAG, String.valueOf(totalBytes));

				// 下载完成
				if (done) {
					// 切换到主线程
					rx.Observable.empty().observeOn(AndroidSchedulers.mainThread())
							.doOnCompleted(new Action0() {
								@Override
								public void call() {
									Utils.showToast(MainAppCompatActivity.this, "完成下载", Toast.LENGTH_SHORT);
								}
							}).subscribe();
				}

			}
		});
		downloader.download(0L);
		dialog.show();
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
