package com.jiahelogistic.volleymanager.volley;

import android.util.Log;

import com.jiahelogistic.config.NetConfig;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Li Huanling on 2016/7/11 0011.
 * 使用okhttp3上传文件
 */
public class UploadFile {
	public static final MediaType MEDIA_TYPE_MARKDOWN
			= MediaType.parse("text/x-markdown; charset=utf-8");
	private static final String TAG = "UploadFile";

	private final OkHttpClient client = new OkHttpClient();

	/**
	 * 上传文件名
	 */
	private String mFilename;

	public UploadFile(String filename) {
		mFilename = filename;
	}

	public void run() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				File file = new File(mFilename);

				Request request = new Request.Builder()
						.url(NetConfig.CRASH_FILE_UPLOAD_URL)
						.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
						.build();

				try {
					Response response = client.newCall(request).execute();
					if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

					Log.e(TAG, response.body().string());
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}.start();
	}
}
