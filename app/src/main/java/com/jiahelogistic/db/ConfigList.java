package com.jiahelogistic.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Func1;
/**
 * Created by Huanling
 * On 2016/07/28 23:29.
 *
 * 配置表
 */

// Note: normally I wouldn't prefix table classes but I didn't want 'List' to be overloaded.
@AutoValue
public abstract class ConfigList implements Parcelable {
	/**
	 * 配置表名
	 */
	public static final String TABLE = "jh_config";

	public static final String ID = "_id";

	/**
	 * 是否运行过
	 */
	public static final String RUNNED = "runned";

	public abstract long id();
	public abstract int runned();

	public static Func1<Cursor, List<ConfigList>> MAP = new Func1<Cursor, List<ConfigList>>() {
		@Override public List<ConfigList> call(final Cursor cursor) {
			try {
				List<ConfigList> values = new ArrayList<>(cursor.getCount());

				while (cursor.moveToNext()) {
					long id = Db.getLong(cursor, ID);
					int runned = Db.getInt(cursor, RUNNED);
					values.add(new AutoValue_ConfigList(id, runned));
				}
				return values;
			} finally {
				cursor.close();
			}
		}
	};

	public static final class Builder {
		private final ContentValues values = new ContentValues();

		public Builder id(long id) {
			values.put(ID, id);
			return this;
		}

		public Builder runned(int runned) {
			values.put(RUNNED, runned);
			return this;
		}

		public ContentValues build() {
			return values; // TODO defensive copy?
		}
	}
}