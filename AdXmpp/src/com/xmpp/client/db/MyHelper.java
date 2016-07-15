package com.xmpp.client.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
	private static int VERSION = 1;
	private static String DB_NAME = "instant_db";
	public MyHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}
	public static String getSql(String properties,String str){
		InputStream in = MyHelper.class.getResourceAsStream(properties);
		Properties pp = new Properties();
		String sql=null;
		try {
			pp.load(in);
			sql = (String) pp.get(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sql;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(getSql("properties","CREATETABLEDATA"));
		db.execSQL(getSql("properties","CREATETABLECONTACT"));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(getSql("properties","DROPDATA"));
		db.execSQL(getSql("properties","DROPCONTACT"));
		db.execSQL(getSql("properties","CREATETABLEDATA"));
		db.execSQL(getSql("properties","CREATETABLECONTACT"));
	}
}
