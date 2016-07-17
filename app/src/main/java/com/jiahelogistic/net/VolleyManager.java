package com.jiahelogistic.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jiahelogistic.JiaHeLogistic;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by Li Huanling on 2016/07/11
 * volley连接管理
 */
public class VolleyManager {
	private static VolleyManager mVolleyManager = null;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static class VolleyManagerHolder {
		private static final VolleyManager INSTANCE = new VolleyManager(JiaHeLogistic.getContext());
	}

	/**
	 * @param context 应用上下文
	 */
	private VolleyManager(Context context) {

		mRequestQueue = Volley.newRequestQueue(context, new OkHttp3Stack(new OkHttpClient()));

		mImageLoader = new ImageLoader(mRequestQueue,
				new LruBitmapCache(context));
	}

	/**
	 * 单例模式（静态内部类）
	 *
	 * @return VolleyManager instance
	 */
	public static VolleyManager newInstance() {
		return VolleyManagerHolder.INSTANCE;
	}

	private <T> Request<T> add(Request<T> request) {
		return mRequestQueue.add(request);//添加请求到队列
	}

	/**
	 * @param tag 标识
	 * @param url 连接地址
	 * @param listener 成功访问监听器
	 * @param errorListener 失败访问监听器
	 * @return 返回结果
	 */
	public StringRequest StrRequest(Object tag, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		StringRequest request = new StringRequest(url, listener, errorListener);
		request.setTag(tag);
		add(request);
		return request;
	}

	/**
	 * @param tag 标志
	 * @param method 访问方式 {@link com.android.volley.Request.Method}
	 * @param url 连接地址
	 * @param listener 成功访问监听器
	 * @param errorListener 失败访问监听器
	 * @return 返回结果
	 */
	public StringRequest StrRequest(Object tag, int method, String url, Response.Listener<String> listener,
	                                Response.ErrorListener errorListener) {
		StringRequest request = new StringRequest(method, url, listener, errorListener);
		request.setTag(tag);
		add(request);
		return request;
	}

	/**
	 * ImageRequest
	 *
	 * @param tag 标志
	 * @param url 连接地址
	 * @param listener 成功访问监听器
	 * @param maxWidth 图片最大宽度
	 * @param maxHeight 图片最大高度
	 * @param decodeConfig 位图配置
	 * @param errorListener 失败访问监听器
	 * @return 返回结果
	 */
	public ImageRequest ImageRequest(Object tag, String url, Response.Listener<Bitmap> listener,
	                                 int maxWidth, int maxHeight, Bitmap.Config decodeConfig,
	                                 Response.ErrorListener errorListener) {
		ImageRequest request = new ImageRequest(url, listener, maxWidth, maxHeight, decodeConfig, errorListener);
		request.setTag(tag);
		add(request);
		return request;
	}

	/**
	 * ImageLoader 图片默认大小
	 *
	 * @param imageView 图像控件
	 * @param imgViewUrl 图像地址
	 * @param defaultImageResId 默认的图像资源ID
	 * @param errorImageResId 网络访问错误显示图片
	 */
	public void ImageLoaderRequest(ImageView imageView, String imgViewUrl, int defaultImageResId,
	                               int errorImageResId) {
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId,
				errorImageResId);
		mImageLoader.get(imgViewUrl, listener);
	}


	/**
	 * ImageLoader 指定图片大小
	 *
	 * @param imageView 图像控件
	 * @param imgViewUrl 图像地址
	 * @param defaultImageResId 默认的图像资源ID
	 * @param errorImageResId 网络访问错误显示图片
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 */
	public void ImageLoaderRequest(ImageView imageView, String imgViewUrl, int defaultImageResId,
	                               int errorImageResId, int maxWidth, int maxHeight) {
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId,
				errorImageResId);
		mImageLoader.get(imgViewUrl, listener, maxWidth, maxHeight);
	}

	/**
	 * Get方法
	 *
	 * @param tag 标志
	 * @param url 连接地址
	 * @param clazz 类
	 * @param listener 访问成功监听器
	 * @param errorListener 访问失败监听器
	 * @param <T> 泛型
	 * @return
	 */
	public <T> GsonRequest<T> GsonGetRequest(Object tag, String url, Class<T> clazz, Response.Listener<T> listener,
	                                         Response.ErrorListener errorListener) {
		GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener, errorListener);
		request.setTag(tag);
		add(request);
		return request;
	}

	/**
	 * Post方式1：Map参数
	 *
	 * @param tag 标志
	 * @param params 参数
	 * @param url 连接地址
	 * @param clazz 类
	 * @param listener 访问成功监听器
	 * @param errorListener 访问失败监听器
	 * @param <T> 泛型
	 * @return
	 */
	public <T> GsonRequest<T> GsonPostRequest(Object tag, Map<String, String> params, String url,
	                                          Class<T> clazz, Response.Listener<T> listener,
	                                          Response.ErrorListener errorListener) {
		GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
		request.setTag(tag);
		add(request);
		return request;
	}

	/**
	 * Post方式2：json字符串
	 *
	 * @param url 连接地址
	 * @param jsonObject json字符串
	 * @param listener 访问成功监听器
	 * @param errorListener 访问失败监听器
	 */
	public void PostjsonRequest(Object tag, String url, JSONObject jsonObject, Response.Listener<JSONObject> listener,
	                            Response.ErrorListener errorListener) {
		JsonObjectRequest jsonObjectRequest;
		jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
				listener, errorListener);
		jsonObjectRequest.setTag(tag);
		add(jsonObjectRequest);

	}

	/**
	 * 取消请求
	 * @param tag 标志
	 */
	public void cancel(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
}
