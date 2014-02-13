package com.cmpe277.assignment3;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_MSG = "message";
	public static final String KEY_DATE = "date_time";

	private static final String DATABASE_NAME = "Assignment3DB";
	private static final String TABLE_NAME = "messageTable";
	//private static final int VERSION = 1;

	private DBHelper dbHelper;
	private Context context;
	private SQLiteDatabase ourDatabase;

	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, 2);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MSG
					+ " TEXT NOT NULL, "+KEY_DATE +" INTEGER );");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);

		}

	}

	public DB(Context c) {
		context = c;
	}

	public DB open() throws SQLException{
		dbHelper = new DBHelper(context);
		ourDatabase = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}

	public long createEntry(String msg) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_MSG, msg);
		cv.put(KEY_DATE, System.currentTimeMillis());
		return ourDatabase.insert(TABLE_NAME, null, cv);
	}
	
	public String readAllData() {
		String[] columns=  new String[]{KEY_ROWID, KEY_MSG};
		Cursor c = ourDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
		String result="";
		
		int iRowID=c.getColumnIndex(KEY_ROWID);
		int iMsg=c.getColumnIndex(KEY_MSG);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			result +=c.getString(iRowID)+" "+c.getString(iMsg)+"\n";
		}
		return result;
	}
	
	public List<Long> readAllDates() {
		String[] columns=  new String[]{KEY_DATE};
		Cursor c = ourDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
		List<Long> dates = new ArrayList<Long>();
		
		int iDate=c.getColumnIndex(KEY_DATE);
		//int iMsg=c.getColumnIndex(KEY_MSG);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			dates.add((long)c.getInt(iDate));
		}
		return dates;
	}

	

}
