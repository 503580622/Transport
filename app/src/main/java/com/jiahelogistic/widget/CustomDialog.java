package com.jiahelogistic.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jiahelogistic.JiaHeLogistic;
import com.jiahelogistic.R;
import com.jiahelogistic.bean.UpgradeBean;
import com.jiahelogistic.handler.BasicNetworkHandler;
import com.jiahelogistic.net.FileManager;

/**
 * Created by Huanling
 * On 2016/07/19 21:34.
 *
 * 自定义对话框
 */
public class CustomDialog extends Dialog {

	private static JiaHeLogistic app = JiaHeLogistic.getInstance();

	/**
	 * 创建通用的等待对话框
	 *
	 * @param context 上下文
	 * @param title Dialog显示的文字内容
	 * @return 对话框
	 */
	public static Dialog createCommonWaitDialog(final Context context, String title) {
		// 初始化Dialog，同时设置了样式
		final Dialog dialog = new Dialog(context, R.style.dialog);
		// 设置Dialog显示的内容，即布局dialog_layout
		dialog.setContentView(R.layout.waiting_progress_dialog);
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
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.jh_dialog_normal_content);
		if (title == null || title.length() == 0) {
			titleTxtv.setText("正在加载...");
		} else {
			titleTxtv.setText(title);
		}
		return dialog;
	}

	/**
	 * 创建通用的带进度的下载对话框
	 *
	 * @param context 上下文
	 * @param title Dialog显示的文字内容
	 * @return 对话框
	 */
	public static Dialog createCommonScheduleProgressDialog(final Context context, String title) {
		// 初始化Dialog，同时设置了样式
		final Dialog dialog = new Dialog(context, R.style.dialog);
		// 设置Dialog显示的内容，即布局dialog_layout
		dialog.setContentView(R.layout.schedule_progress_dialog);
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
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.jh_dialog_schedule_content);
		if (title == null || title.length() == 0) {
			titleTxtv.setText("正在加载...");
		} else {
			titleTxtv.setText(title);
		}
		return dialog;
	}

	/**
	 * 创建通用对话框
	 *
	 * @param context 上下文
	 */
	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 *
		 * @param message
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 *
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 *
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 *
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
		                                 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
		                                 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
		                                 DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
		                                 DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context);
			View layout = inflater.inflate(R.layout.dialog_normal, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.jh_dialog_normal_title)).setText(title);
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.jh_dialog_btn_ok))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.jh_dialog_btn_ok))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.jh_dialog_btn_cancel).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.jh_dialog_btn_cancel))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.jh_dialog_btn_cancel))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.jh_dialog_btn_cancel).setVisibility(
						View.GONE);
			}
			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.jh_dialog_normal_content)).setText(message);
			} else if (contentView != null) {
				((TextView) layout.findViewById(R.id.jh_dialog_normal_content)).setText("友情提示。。。");
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}

	public static void upgradeProgressDialog(final Context context, final UpgradeBean upgradeBean,
	                                        final BasicNetworkHandler handler) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle("有可用的升级！");
		builder.setMessage(upgradeBean.getDescription());
		builder.setPositiveButton("马上升级", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				// 开启下载新版本
				dialogInterface.dismiss();
				FileManager.downloadFileWithProgressBar(context,
						upgradeBean.getUrl(), app.getUpgradeFilePath(), handler);
			}
		});
		builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
				Toast.makeText(context, "点击了取消", Toast.LENGTH_SHORT).show();
			}
		});
		builder.create().show();
	}
}
