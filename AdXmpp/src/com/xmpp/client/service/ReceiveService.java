package com.xmpp.client.service;

import java.util.ArrayList;
import java.util.List;

import com.xmpp.client.R;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.aidl.Msg;
import com.xmpp.client.config.SelfInfo;
import com.xmpp.client.util.AppUtils;
import com.xmpp.client.util.TimeRender;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;

public class ReceiveService extends Service {

	private MsgReceiver msgReceiver;
	private SoundPool soundPool;
	private List<Integer> _list;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(msgReceiver);
		super.onDestroy();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		soundPool = new SoundPool(10, AudioManager.STREAM_NOTIFICATION, 5);
		_list = new ArrayList<Integer>();
		_list.add(soundPool.load(this, R.raw.notify, 1)); //message
		_list.add(soundPool.load(this, R.raw.system, 1)); //request friend
		_list.add(soundPool.load(this, R.raw.onlinenotify, 1)); //friend online or outline
		
		msgReceiver = new MsgReceiver();
		IntentFilter intentFileter = new IntentFilter();
		intentFileter.addAction("com.message.transferto.servicewith");
		intentFileter.addAction("com.message.transferto.servicepresence");
		intentFileter.addAction("com.message.transferto.serviceadd");
		registerReceiver(msgReceiver, intentFileter);
		super.onCreate();

	}

	class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String actionString = intent.getAction();
			if (actionString.equals("com.message.transferto.servicewith")) {
				if (SelfInfo.setValue.getNotify() == 1) {
					soundPool.play(_list.get(0), 1, 1, 0, 0, 1);
				}
				Msg msg = intent.getParcelableExtra("newMessage");
				boolean _flag = true;
				int index;
				for (index = 0; index < SelfInfo._contact.size(); index++) {
					if (SelfInfo._contact.get(index).getContactKey().equals(msg.getKeyTo())) {
						_flag = false;
						break;
					}
				}
				if (_flag) {
					Contact conta = new Contact(msg.getKeyTo(), msg.getMsg(), TimeRender.getDate(msg.getDate()), 1, msg.getDataType());
					SelfInfo._contact.add(conta);
				} else {
					if (!SelfInfo.isChat || !SelfInfo.To.getKey().equals(msg.getKeyTo())) {
						SelfInfo._contact.get(index).incrementNewMessage();
					}
					SelfInfo._contact.get(index).setDataType(msg.getDataType());
					SelfInfo._contact.get(index).setLastMessage(msg.getMsg());
					SelfInfo._contact.get(index).setDate(TimeRender.getDate(msg.getDate()));
				}
				SelfInfo.contactAdapter.notifyDataSetChanged();
			} else if (actionString.equals("com.message.transferto.servicepresence")) {
				if (SelfInfo.setValue.getNotify() == 1) {
					soundPool.play(_list.get(2), 1, 1, 0, 0, 1);
				}
				String key = intent.getStringExtra("key");
				int value = intent.getIntExtra("value", 1);
				for (Account account : SelfInfo._list) {
					if (account.getKey().equals(key)) {
						account.setOnline(value);
						if (SelfInfo.accountAdapter != null) {
							SelfInfo.accountAdapter.notifyDataSetChanged();
						}
						break;
					}
				}
			} else if (actionString.equals("com.message.transferto.serviceadd")) {
				if (SelfInfo.setValue.getNotify() == 1) {
					soundPool.play(_list.get(1), 1, 1, 0, 0, 1);
				}
				Msg message = intent.getParcelableExtra("value");
				switch (message.getDirection()) {
				case 2:
					SelfInfo._list.add(new Account(message.getKeyCom(),
							message.getKeyCom().substring(0, AppUtils.getIndex(message.getKeyCom(), '@') - 1), 1));
					break;
				case 4:
					for(Contact contact:SelfInfo._contact) {
						if(contact.getContactKey().equals(message.getKeyCom())) {
							SelfInfo._contact.remove(contact);
							SelfInfo.accountAdapter.notifyDataSetChanged();
							break;
						}
					}
					for(Account account:SelfInfo._list) {
						if(account.getKey().equals(message.getKeyCom())) {
							SelfInfo._list.remove(account);
							SelfInfo.accountAdapter.notifyDataSetChanged();
							break;
						}
					}
					break;
				default:
					break;
				}
				SelfInfo._addFrieds.add(message);
				if (SelfInfo.friendAdapter != null) {
					SelfInfo.friendAdapter.notifyDataSetChanged();
				}
			}
		}

	}
}
