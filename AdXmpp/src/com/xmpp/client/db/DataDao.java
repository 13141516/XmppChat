package com.xmpp.client.db;

import java.util.ArrayList;
import java.util.List;

import com.xmpp.client.aidl.Contact;
import com.xmpp.client.aidl.Msg;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataDao {
	private MyHelper myHelper;

	public DataDao(Context context) {
		myHelper = new MyHelper(context);
	}

	// 表acccount的dao
	public boolean insertMsgSql(Msg message) {
		SQLiteDatabase sd = myHelper.getWritableDatabase();
		String sql = MyHelper.getSql("dataproperties", "INSERTSQL");
		Object[] bindArgs = new Object[] { message.getKeyCom(), message.getKeyTo(), message.getMsg(), message.getDate(),
				message.getDirection(), message.getDataType() };
		boolean _flag = true;
		try {
			sd.execSQL(sql, bindArgs);
		} catch (SQLException ex) {
			_flag = false;
			ex.printStackTrace();
		} finally {
			sd.close();
		}
		return _flag;
	}

	public List<Msg> getMsgListPage(String who, int start, int limit) {
		List<Msg> _List = new ArrayList<Msg>();
		SQLiteDatabase sd = myHelper.getWritableDatabase();
		String sql = MyHelper.getSql("dataproperties", "FINDSOMESQL");
		String[] bindArgs = new String[] { who, String.valueOf(start), String.valueOf(limit) };
		Cursor cursor = sd.rawQuery(sql, bindArgs);
		while (cursor.moveToNext()) {
			Msg msg = new Msg(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3),
					cursor.getInt(4), cursor.getInt(5));
			_List.add(msg);
		}
		cursor.close();
		sd.close();
		return _List;
	}

	public void deleteAllMsg() {
		SQLiteDatabase sd = myHelper.getWritableDatabase();
		String sql = MyHelper.getSql("dataproperties", "DELETEALLSQL");
		try {
			sd.execSQL(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			sd.close();
		}
		return;
	}
	
	// 表contact的dao

	public void deleteAllContact() {
		SQLiteDatabase sd = myHelper.getWritableDatabase();
		String sql = MyHelper.getSql("contactproperties", "DELETEALLSQL");
		try {
			sd.execSQL(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			sd.close();
		}
		return;
	}

	public List<Contact> getContactList() {
		List<Contact> _List = new ArrayList<Contact>();
		SQLiteDatabase sd = myHelper.getWritableDatabase();
		String sql = MyHelper.getSql("contactproperties", "FINDALLSQL");
		Cursor cursor = sd.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),
					cursor.getInt(3), cursor.getInt(4));
			_List.add(contact);
		}
		cursor.close();
		sd.close();
		return _List;
	}

	public boolean insertContactSql(List<Contact> _contact) {
		SQLiteDatabase sd = myHelper.getWritableDatabase();
		String sql = MyHelper.getSql("contactproperties", "INSERTSQL");
		boolean _flag = true;
		for (Contact contact : _contact) {
			Object[] bindArgs = new Object[] { contact.getContactKey(), contact.getLastMessage(), contact.getDate(),
					contact.getNewMessage(), contact.getDataType() };
			try {
				sd.execSQL(sql, bindArgs);
			} catch (SQLException ex) {
				_flag = false;
				ex.printStackTrace();
				break;
			} finally {
				sd.close();
			}
		}
		return _flag;
	}
}
