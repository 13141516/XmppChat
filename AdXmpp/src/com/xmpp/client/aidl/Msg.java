package com.xmpp.client.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Msg implements Parcelable {
	String keyCom; // the jid of myself
	String keyTo; // the jid that i am chatting with
	String msg; // 发送的消息内容
	long date; // 发送的时间
	int direction; // receive or send
	int dataType;

	public Msg(String keyCom, String keyTo, String msg, long date, int direction, int dataType) {
		this.keyCom = keyCom;
		this.keyTo = keyTo;
		this.msg = msg;
		this.date = date;
		this.direction = direction;
		this.dataType = dataType;
	}

	public Msg() {
		super();
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getKeyCom() {
		return keyCom;
	}

	public void setKeyCom(String keyCom) {
		this.keyCom = keyCom;
	}

	public String getKeyTo() {
		return keyTo;
	}

	public void setKeyTo(String keyTo) {
		this.keyTo = keyTo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(keyCom);
		dest.writeString(keyTo);
		dest.writeString(msg);
		dest.writeLong(date);
		dest.writeInt(direction);
		dest.writeInt(dataType);
	}

	public static final Parcelable.Creator<Msg> CREATOR = new Creator<Msg>() {

		@Override
		public Msg createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Msg(source.readString(), source.readString(), source.readString(), source.readLong(),
					source.readInt(), source.readInt());
		}

		@Override
		public Msg[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Msg[size];
		}
	};

}
