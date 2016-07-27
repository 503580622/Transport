package com.jiahelogistic.net;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Huanling
 * On 2016/07/27 21:15.
 *
 * 下载进度监听
 */
public class ProgressResponseBody extends ResponseBody {
	/**
	 * 监听器
	 */
	public interface ProgressListener {
		/**
		 * 在开始下载前调用，设置文件的长度，只调用一次
		 * @param contentLength 文件长度
		 */
		void onPreExecute(long contentLength);

		/**
		 * 更新文件下载进度
		 *
		 * @param totalBytes 已下载的长度
		 * @param done 是否完成
		 */
		void update(long totalBytes, boolean done);
	}

	private final ResponseBody responseBody;
	private final ProgressListener progressListener;
	private BufferedSource bufferedSource;

	public ProgressResponseBody(ResponseBody responseBody,
	                            ProgressListener progressListener) {
		this.responseBody = responseBody;
		this.progressListener = progressListener;
		if (progressListener != null) {
			progressListener.onPreExecute(contentLength());
		}
	}

	@Override
	public MediaType contentType() {
		return responseBody.contentType();
	}

	@Override
	public long contentLength() {
		return responseBody.contentLength();
	}

	@Override
	public BufferedSource source() {
		if (bufferedSource == null) {
			bufferedSource = Okio.buffer(source(responseBody.source()));
		}
		return bufferedSource;
	}

	private Source source(Source source) {
		return new ForwardingSource(source) {
			long totalBytes = 0L;

			@Override
			public long read(Buffer sink, long byteCount) throws IOException {
				long bytesRead = super.read(sink, byteCount);
				// read() returns the number of bytes read, or -1 if this source is exhausted.
				totalBytes += bytesRead != -1 ? bytesRead : 0;
				if (null != progressListener) {
					progressListener.update(totalBytes, bytesRead == -1);
				}
				return bytesRead;
			}
		};
	}
}
