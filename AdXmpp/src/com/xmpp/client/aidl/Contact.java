package com.xmpp.client.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
	private String contactKey;
	private String lastMessage;
	private String date;
	private int newMessage;
	private int dataType;

	public Contact(String contactKey, String lastMessage, String date, int newMessage, int dataType) {
		super();
		this.lastMessage = lastMessage;
		this.contactKey = contactKey;
		this.date = date;
		this.newMessage = newMessage;
		this.dataType = dataType;
	}

	public Contact() {
		super();
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getContactKey() {
		return contactKey;
	}

	public void setContactKey(String contactKey) {
		this.contactKey = contactKey;
	}

	public int getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(int newMessage) {
		this.newMessage = newMessage;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void incrementNewMessage() {
		this.newMessage++;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(contactKey);
		dest.writeString(lastMessage);
		dest.writeString(date);
		dest.writeInt(newMessage);
		dest.writeInt(dataType);
	}

	public static final Parcelable.Creator<Contact> CREATOR = new Creator<Contact>() {

		@Override
		public Contact createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Contact(source.readString(), source.readString(), source.readString(), source.readInt(),
					source.readInt());
		}

		@Override
		public Contact[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Contact[size];
		}
	};

}
