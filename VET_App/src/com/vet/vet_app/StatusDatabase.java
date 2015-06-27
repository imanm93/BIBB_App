package com.vet.vet_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class StatusDatabase {

	public static final String KEY_ROWID = BaseColumns._ID;
	public static final String KEY_OCCUPATION = "DB_OCCUPATION";
	public static final String KEY_SELECTED = "DB_SELECTED";
	public static final String KEY_LIKES = "DB_LIKES";
	public static final String KEY_DETAILVIEWED = "DB_DETAILVIEWED";
	
	DBHelper dbHelper;
	Context ourContext;

	public StatusDatabase(Context context) {
		ourContext = context;
		dbHelper = new DBHelper();
	}

	public void update(Integer id) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		String sql = " SELECT * FROM " + DBHelper.DB_TABLE + " WHERE "
				+ KEY_OCCUPATION + " LIKE '" + VideoActivity.chosenOccupation
				+ "'";

		Cursor c = db.rawQuery(sql, null);

		c.moveToFirst();
		
		switch (id) {
		case 1:			
			int increment = c.getInt(c.getColumnIndex(KEY_SELECTED)) + 1;
			String sql1 = "UPDATE " + DBHelper.DB_TABLE + " SET "
					+ KEY_SELECTED + " =" + increment + " WHERE "
					+ KEY_OCCUPATION + " LIKE '"
					+ VideoActivity.chosenOccupation + "'";
			db.execSQL(sql1);
			db.close();
			break;
		case 2:
			int increment1 = c.getInt(c.getColumnIndex(KEY_LIKES)) + 1;
			String sql2 = "UPDATE " + DBHelper.DB_TABLE + " SET " + KEY_LIKES
					+ " =" + increment1 + " WHERE " + KEY_OCCUPATION
					+ " like '" + VideoActivity.chosenOccupation + "'";
			db.execSQL(sql2);
			db.close();
			break;
		case 3:
			int increment2 = c.getInt(c.getColumnIndex(KEY_DETAILVIEWED)) + 1;
			String sql3 = "UPDATE " + DBHelper.DB_TABLE + " SET "
					+ KEY_DETAILVIEWED + " =" + increment2 + " WHERE "
					+ KEY_OCCUPATION + " like '"
					+ VideoActivity.chosenOccupation + "'";
			db.execSQL(sql3);
			db.close();
			break;
		}

	}

	public Cursor query() {

		SQLiteDatabase db = dbHelper.getReadableDatabase();

		return db.query(DBHelper.DB_TABLE, null, null, null, null, null, null);

	}
	
	public Cursor likeQuery() {
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		String sql = " SELECT * FROM " + DBHelper.DB_TABLE + " WHERE "
				+ KEY_OCCUPATION + " LIKE '" + VideoActivity.chosenOccupation
				+ "'";
		
		Cursor result = db.rawQuery(sql, null);
				
		return result;
		
	}

	public void delete() {

		// opening database

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Deletes ALL the data

		db.delete(DBHelper.DB_TABLE, null, null);

		// closing database

		db.close();

	}

	private class DBHelper extends SQLiteOpenHelper {

		private static final String DB_NAME = "OccupationStats.db";
		private static final String DB_TABLE = "OccupationTable";
		public static final int DB_VERSION = 4;

		public DBHelper() {
			super(ourContext, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			// Creating table

			String sql = String
					.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER)",
							DB_TABLE, KEY_ROWID, KEY_OCCUPATION, KEY_SELECTED,
							KEY_LIKES, KEY_DETAILVIEWED);

			db.execSQL(sql);

			first(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			this.onCreate(db);
		}

		public void first(SQLiteDatabase db) {

			String occupation = "";

			for (int x = 0; x < 2; x++) {

				if (x == 0) {
					occupation = "Electronic Technician";
				} else {
					occupation = "Mechanic";
				}

				ContentValues values = new ContentValues();

				values.put(KEY_ROWID, x);
				values.put(KEY_OCCUPATION, occupation);
				values.put(KEY_SELECTED, 0);
				values.put(KEY_LIKES, 0);
				values.put(KEY_DETAILVIEWED, 0);

				db.insert(DB_TABLE, null, values);

			}

			Log.d("DBHelper", "DBClose after Created");

		}

	}

}
