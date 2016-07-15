package com.xmpp.client.activities;

import com.xmpp.client.R;
import com.xmpp.client.aidl.Instant;
import com.xmpp.client.config.SelfInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashactivity);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SelfInfo.baseMain.bindMyService();
				Intent intent = new Intent(SplashActivity.this, FormLogin.class);
				startActivity(intent);
				finish();
			}
		}, 3000);
		SelfInfo.baseMain = new BaseMain(getApplicationContext());
		startService(new Intent(SplashActivity.this, Instant.class));
	}
}
