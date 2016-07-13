package com.jiahelogistic.config;

/**
 * Created by Li Huanling on 2016/7/11 0011.
 * 网络配置，单例模式
 */
public class NetConfig {
	/**
	 * 连接根地址
	 */
	public static final String URLROOT = "http://10.0.0.2";

	/**
	 * 异常文件上传地址
	 */
	public static final String CRASH_FILE_UPLOAD_URL = "http://10.0.0.2/upload.php";


//	/**
//	 * 单个实例
//	 */
//	private static NetConfig config = null;
//
//	private NetConfig() {
//	}
//
//	/**
//	 * 获取网络配置实例
//	 * @return 配置对象
//	 */
//	public static NetConfig getInstance() {
//		if (config == null) {
//			config = new NetConfig();
//		}
//		return config;
//	}

}
