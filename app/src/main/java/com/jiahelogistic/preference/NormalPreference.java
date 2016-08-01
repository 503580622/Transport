package com.jiahelogistic.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jiahelogistic.R;

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
	 * 子标题
	 */
	private String subTitle;

	/**
	 * 引用ID
	 */
	private int id;

	public NormalPreference(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
	}

	public NormalPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setLayoutResource(R.layout.normal_preference);

		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.NormalPreference);

		subTitle = typedArray.getString(R.styleable.NormalPreference_subTitle);
		id = typedArray.getInt(R.styleable.NormalPreference_id, 0);

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

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		TextView subTitleView = (TextView) view.findViewById(R.id.jh_normal_preference_subtitle);
		subTitleView.setText(subTitle);
	}
}
