package com.jiahelogistic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Huanling
 * On 2016/07/28 23:23.
 *
 * 数据库辅助类
 */


final class DbOpenHelper extends SQLiteOpenHelper {
	/**
	 * 数据库版本
	 */
	private static final int VERSION = 1;

	private static final String CREATE_LIST = ""
			+ "CREATE TABLE " + ConfigList.TABLE + "("
			+ ConfigList.ID + " INTEGER NOT NULL PRIMARY KEY,"
			+ ConfigList.RUNNED + " INTEGER NOT NULL DEFAULT 0"
			+ ")";

	public DbOpenHelper(Context context) {
		super(context, "jiahelogistic.db", null /* factory */, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_LIST);

		long configId = db.insert(ConfigList.TABLE, null, new ConfigList.Builder()
				.runned(1)
				.build());

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
