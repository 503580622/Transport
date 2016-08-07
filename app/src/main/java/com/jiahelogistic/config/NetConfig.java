package com.jiahelogistic.config;

/**
 * Created by Li Huanling
 * On 2016/7/11 21:20.
 *
 * 网络配置，单例模式
 */
public class NetConfig {

	/**
	 * 没有网络连接
	 */
	public static final int STATUS_NO_INTERNET_CONNECTION = 0;

	/**
	 * 没有找到该地址
	 */
	public static final int STATUS_HTTP_NOT_FOUND = 404;

	/**
	 * 成功访问地址
	 */
	public static final int STATUS_HTTP_OK = 200;

	public static final int STATUS_HTTP_TIMEOUT = 408;


	/*******************************************
	地址常量
	*******************************************/


	/**
	 * 连接根地址
	 */
	public static final String URL_HOST_ROOT = "http://www.lovejm.com";

	/**
	 * 异常文件上传地址
	 */
	public static final String URL_CRASH_FILE_UPLOAD = URL_HOST_ROOT + "/upload.php";

	/**
	 * 检查更新
	 */
	public static final String URL_UPLOAD_APP = URL_HOST_ROOT + "/checkUpgrade.php";

}
