package com.jiahelogistic.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiahelogistic.R;

/**
 * Created by Huanling
 * On 2016/07/19 21:34.
 *
 * 自定义对话框
 */
public class DialogFactory {

	/**
	 * @param context 上下文
	 * @param title Dialog显示的文字内容
	 * @return 对话框
	 *
	 * 创建通用的等待对话框
	 */
	public static Dialog createCommonRequestDialog(final Context context, String title) {
		// 初始化Dialog，同时设置了样式
		final Dialog dialog = new Dialog(context, R.style.dialog);
		// 设置Dialog显示的内容，即布局dialog_layout
		dialog.setContentView(R.layout.dialog_layout);
		// 利用Window和WindowManager设置Dialog的宽度
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		// 获取屏幕宽度
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();// 获取屏幕宽度
		// 设置dialog宽度为屏幕官渡的0.6
		lp.width = (int) (0.6 * width);
		// 获取TextView并设置显示内容
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.jh_common_dialog_txt);
		if (title == null || title.length() == 0) {
			titleTxtv.setText("正在加载...");
		} else {
			titleTxtv.setText(title);
		}
		return dialog;
	}
}