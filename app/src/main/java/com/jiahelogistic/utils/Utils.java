package com.jiahelogistic.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jiahelogistic.JiaHeLogistic;
import com.jiahelogistic.config.NetConfig;

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

	public static final void UploadFile(final Activity activity, File file) {
		MultipartBody.Builder builder =  new MultipartBody.Builder();
		builder.setType(MultipartBody.FORM);
		builder.addFormDataPart("fileData", file.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
		MultipartBody body = builder.build();
		Request request = new Request.Builder()
				.url(NetConfig.CRASH_FILE_UPLOAD_URL)
				.post(body)
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.e("UploadFile", call.toString());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {

				}
				Headers responseHeaders = response.headers();
				for (int i = 0; i < responseHeaders.size(); i++) {
					Log.e("UploadFileHeader", responseHeaders.name(i) + ": " + responseHeaders.value(i));
				}

				Log.e("UploadFile", response.body().string());
			}
		});
	}
}
