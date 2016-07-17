package com.jiahelogistic.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.jiahelogistic.JiaHeLogistic;
import com.jiahelogistic.config.NetConfig;
import com.jiahelogistic.entity.KeyValue;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Li Huanling on 2016/7/12 0012.
 * 应用工具类
 */
public class Utils {
	/**
	 * 媒体类型
	 */
	private static final MediaType MEDIA_TYPE_MARKDOWN
					= MediaType.parse("text/x-markdown; charset=utf-8");

	/**
	 * OkHttp实例
	 */
	private static final OkHttpClient client = new OkHttpClient();

	public static final void Exit() {
		JiaHeLogistic app = JiaHeLogistic.getInstance();
		for (Activity activity : app.getStack()) {
			if (activity != null) {
				activity.finish();
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	/**
	 * 异步提交数据
	 * @param file 需要上传的文件
	 * @param param 额外参数，键值对数组
	 * @param handler 处理返回结果
	 */
	public static final void asynPost(File file, KeyValue[] param, final Handler handler) {
		MultipartBody.Builder builder =  new MultipartBody.Builder();
		builder.setType(MultipartBody.FORM);
		builder.addFormDataPart("fileData", file.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, file));

		// 如果额外参数不为空
		if (param != null) {
			for (KeyValue kv : param) {
				// 循环添加参数
				builder.addFormDataPart(kv.getKey(), kv.getValue());
			}
		}
		MultipartBody body = builder.build();

		Request request = new Request.Builder()
				.url(NetConfig.CRASH_FILE_UPLOAD_URL)
				.post(body)
				.build();

		client.newCall(request).enqueue(new Callback() {
			/**
			 * 返回的消息
			 */
			private Message msg = Message.obtain();

			@Override
			public void onFailure(Call call, IOException e) {
				// 没有网络
				Log.e("Utils", "没有网络");
				msg.what = NetConfig.NO_INTERNET_CONNECTION;
				handler.sendMessage(msg);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					Log.e("asynPost", "NOT FOUND");
					msg.what = NetConfig.HTTP_NOT_FOUND;
					handler.sendMessage(msg);
					return;
				}
				Headers responseHeaders = response.headers();
				for (int i = 0; i < responseHeaders.size(); i++) {
					Log.e("UploadFileHeader", responseHeaders.name(i) + ": " + responseHeaders.value(i));
				}

				Log.e("UploadFile", response.body().string());
				msg.what = NetConfig.HTTP_OK;
				msg.obj = response.body();
				handler.sendMessage(msg);
			}
		});
	}

	public static final void checkUpdate() {
	}

	/**
	 * 提示用户，如有需要，方便自定义
	 * @param c 上下文
	 * @param msg 提示内容
	 * @param duration 显示持续时间
	 */
	public static final void showToast(Context c, String msg, int duration) {
		Toast.makeText(c, msg, duration).show();
	}

	/**
	 * 检查网络
	 */
	public static final void checkNetword() {

	}
}
