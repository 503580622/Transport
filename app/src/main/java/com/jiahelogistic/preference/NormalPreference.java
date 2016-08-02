package com.jiahelogistic.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jiahelogistic.R;
import com.jiahelogistic.utils.DataCleanManager;

/**
 * Created by Huanling
 * On 2016/08/01 22:21.
 *
 * 自定义设置按钮，用来设置清除缓存等
 */
public class NormalPreference extends Preference {

	/**
	 * 上下文
	 */
	private final Context mContext;

	/**
	 * 点击监听器
	 */
	private OnClickListener onClickListener;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 缓存控件
	 */
	private TextView cacheTextView;

	public NormalPreference(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		setLayoutResource(R.layout.normal_preference);
	}

	public NormalPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setLayoutResource(R.layout.normal_preference);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalPreference);
		title = typedArray.getString(R.styleable.NormalPreference_android_title);
		typedArray.recycle(); //回收资源
	}

	public NormalPreference(Context context) {
		super(context);
		mContext = context;
		setLayoutResource(R.layout.normal_preference);
	}

	@Override
	public void setTitle(int titleResId) {
		super.setTitle(titleResId);
	}

	@SuppressWarnings({"unused"})
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		cacheTextView = (TextView) view.findViewById(R.id.jh_normal_preference_subtitle);
		TextView titleView = (TextView) view.findViewById(R.id.jh_normal_preference_title);
		titleView.setText(title);

		// 计算缓存
		calCacheValue();

		// 点击事件
		if (onClickListener != null) {
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onClickListener.click(NormalPreference.this);
				}
			});
		}
	}

	public interface OnClickListener {
		void click(NormalPreference normalPreference);
	}

	/**
	 * 计算缓存大小
	 */
	public void calCacheValue() {
		String cache;
		try {
			cache = DataCleanManager.getTotalCacheSize(mContext);
		} catch (Exception e) {
			// 默认0KB
			cache = "0KB";
		}
		cacheTextView.setText(cache);
	}


}
