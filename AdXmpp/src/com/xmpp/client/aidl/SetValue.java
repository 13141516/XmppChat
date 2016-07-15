package com.xmpp.client.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class SetValue implements Parcelable {
	private int account;
	private int online;
	private int notify;
	private String useid;
	private String userpwd;

	public SetValue(int account, int online, int notify, String useid, String userpwd) {
		super();
		this.account = account;
		this.online = online;
		this.notify = notify;
		this.useid = useid;
		this.userpwd = userpwd;
	}

	public String getUseid() {
		return useid;
	}

	public void setUseid(String useid) {
		this.useid = useid;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public SetValue() {
		super();
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getNotify() {
		return notify;
	}

	public void setNotify(int notify) {
		this.notify = notify;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(account);
		dest.writeInt(online);
		dest.writeInt(notify);
		dest.writeString(useid);
		dest.writeString(userpwd);
	}
	
	public static final Parcelable.Creator<SetValue> CREATOR = new Creator<SetValue>() {

		@Override
		public SetValue createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new SetValue(source.readInt(), source.readInt(), source.readInt(), source.readString(), source.readString());
		}

		@Override
		public SetValue[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SetValue[size];
		}
	};

}
