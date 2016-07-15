package com.xmpp.client.adapter;

import java.util.List;

import com.xmpp.client.R;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.util.AppUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountListAdapter extends AdapterBase{

	public AccountListAdapter(List<?> pList, Context pContext) {
		super(pList, pContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemHolder itemHolder = null;
		if(convertView == null) {
			convertView = LayoutInflater.from(getmContext()).inflate(R.layout.accountlistitem, null);
			itemHolder = new ItemHolder();
			itemHolder.user_icon = (ImageView) convertView.findViewById(R.id.iv_account);
			itemHolder.use_name = (TextView) convertView.findViewById(R.id.tv_username);
			convertView.setTag(itemHolder);
		}
		itemHolder = (ItemHolder) convertView.getTag();
		if(((Account) getmList().get(position)).getOnline() == 1) {
			itemHolder.user_icon.setImageResource(R.drawable.usericon);
		} else {
			itemHolder.user_icon.setImageDrawable(AppUtils.clearDrawble(getmContext(), R.drawable.usericon));
		}
		itemHolder.use_name.setText(((Account) getmList().get(position)).getNick());
		return convertView;
	}
	

	static class ItemHolder {
		ImageView user_icon;
		TextView use_name;
	}
}
