package com.xmpp.client.adapter;

import java.util.List;

import com.xmpp.client.R;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.config.InfoConfig;
import com.xmpp.client.config.SelfInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactListAdapter extends AdapterBase {

	public ContactListAdapter(List<?> pList, Context pContext) {
		super(pList, pContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemHolder itemHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(getmContext()).inflate(R.layout.contactlistitem, null);
			itemHolder = new ItemHolder();
			itemHolder.user_icon = (TextView) convertView.findViewById(R.id.iv_personimage);
			itemHolder.message_number = (TextView) convertView.findViewById(R.id.tv_messagenumber);
			itemHolder.message_short = (TextView) convertView.findViewById(R.id.tv_shortmessage);
			itemHolder.message_time = (TextView) convertView.findViewById(R.id.tv_messagetime);
			convertView.setTag(itemHolder);
		}
		itemHolder = (ItemHolder) convertView.getTag();
		Contact contact = (Contact) getmList().get(position);
		String str = "SomeBody";
		for(Account account:SelfInfo._list) {
			if(account.getKey().equals(contact.getContactKey())) {
				str = account.getNick();
			}
		}
		itemHolder.user_icon.setText(str);
		switch(contact.getDataType()) {
		case InfoConfig.TYPE_TEXT:
			itemHolder.message_short.setText(contact.getLastMessage());
			break;
		case InfoConfig.TYPE_SOUND:
			itemHolder.message_short.setText("[”Ô“Ùœ˚œ¢]");
		default:
			break;
		}  
		itemHolder.message_time.setText(contact.getDate());
		itemHolder.message_number.setText(contact.getNewMessage()+"");
		return convertView;
	}

	static class ItemHolder {
		TextView user_icon;
		TextView message_number;
		TextView message_short;
		TextView message_time;
	}

}
