package com.xmpp.client.adapter;

import java.util.List;

import com.xmpp.client.R;
import com.xmpp.client.aidl.Msg;
import com.xmpp.client.config.SelfInfo;

import android.content.Context;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FriendAdapter extends AdapterBase {

	public FriendAdapter(List<?> pList, Context pContext) {
		super(pList, pContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemHolder itemHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(getmContext()).inflate(R.layout.frienditem, null);
			itemHolder = new ItemHolder();
			itemHolder.use_name = (TextView) convertView.findViewById(R.id.tv_name_friend);
			itemHolder.btn_add = (Button) convertView.findViewById(R.id.btn_add_friend);
			itemHolder.btn_rej = (Button) convertView.findViewById(R.id.btn_reject_friend);
			convertView.setTag(itemHolder);
		}
		itemHolder = (ItemHolder) convertView.getTag();
		final Msg msg = (Msg) getmList().get(position);
		final String str = msg.getKeyCom();
		itemHolder.btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendAgree(str, true);
				SelfInfo._addFrieds.remove(msg);
				SelfInfo.friendAdapter.notifyDataSetChanged();
			}
			
		});
		itemHolder.btn_rej.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendAgree(str, false);
				SelfInfo._addFrieds.remove(msg);
				SelfInfo.friendAdapter.notifyDataSetChanged();
			}
		});
		String strName = msg.getKeyCom().substring(0, msg.getKeyCom().indexOf('@'));
		switch (msg.getDirection()) {
		case 1:
			itemHolder.use_name.setText(strName+"想加你为好友");
			itemHolder.btn_add.setVisibility(View.VISIBLE);
			itemHolder.btn_rej.setVisibility(View.VISIBLE);
			break;
		case 2:
			itemHolder.use_name.setText(strName + "同意加你为好友");
			itemHolder.btn_add.setVisibility(View.GONE);
			itemHolder.btn_rej.setVisibility(View.GONE);
			break;
		case 3:
			itemHolder.use_name.setText(strName + "拒绝加你为好友");
			itemHolder.btn_add.setVisibility(View.GONE);
			itemHolder.btn_rej.setVisibility(View.GONE);
			break;
		case 4:
			itemHolder.use_name.setText(strName + "删除你作为好友");
			itemHolder.btn_add.setVisibility(View.GONE);
			itemHolder.btn_rej.setVisibility(View.GONE);
		default:
			break;

		}
		return convertView;
	}

	private void sendAgree(String str, boolean isAgree) {
		try {
			SelfInfo.baseMain.instant.agreeOrReject(str, isAgree);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		}
	}
	
	static class ItemHolder {
		TextView use_name;
		Button btn_add;
		Button btn_rej;
	}
}
