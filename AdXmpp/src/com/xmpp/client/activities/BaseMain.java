package com.xmpp.client.activities;

import com.xmpp.client.aidl.Instant;
import com.xmpp.client.aidl.ServiceInstant;
 
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class BaseMain{
	public Boolean isBound = false;
	public ServiceInstant instant = null;
	private Context context;
	public BaseMain(Context mContext) {
		// TODO Auto-generated constructor stub
		context = mContext;
	}
	private ServiceConnection myConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			instant = null;
			isBound = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			instant = ServiceInstant.Stub.asInterface(service);
			isBound = true;
		}
	};

	public void bindMyService() {
		if (!isBound) {
			Intent intent = new Intent(context, Instant.class);
			context.bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
			isBound = true;
		}
	}

	public void unBindMyService() {
		if (isBound) {
			context.unbindService(myConnection);
			isBound = false;
		}
	}
}
