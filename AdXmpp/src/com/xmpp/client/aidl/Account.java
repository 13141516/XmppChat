package com.xmpp.client.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
	private String key;
	private String nick;
	private int online;//1 表示在线，0表示不在线

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Account(String key, String nick, int online) {
		super();
		this.key = key;
		this.nick = nick;
		this.online = online;
	}

	public Account() {
		super();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(key);
		dest.writeString(nick);
		dest.writeInt(online);
	}

	public static final Parcelable.Creator<Account> CREATOR = new Creator<Account>() {

		@Override
		public Account createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Account(source.readString(), source.readString(), source.readInt());
		}

		@Override
		public Account[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Account[size];
		}
	};

}
