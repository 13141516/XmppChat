package com.xmpp.client.activities;

import java.lang.ref.WeakReference;

import com.xmpp.client.R;
import com.xmpp.client.config.SelfInfo;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FormRegister extends Activity {
	private LinearLayout ll_layout1, ll_layout2;
	private EditText userName, userPwd, userPwdAuth;
	private Button btn_reg;
	private RecevierHandler rHandler;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formregister);
		// update ui
		ActionBar bar = getActionBar();
		bar.setTitle(getResources().getString(R.string.user_register));
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);
		// init handler
		rHandler = new RecevierHandler(FormRegister.this);
		// init control item
		ll_layout1 = (LinearLayout) findViewById(R.id.formregister_layout1);
		ll_layout2 = (LinearLayout) findViewById(R.id.formregister_layout2);
		userName = (EditText) findViewById(R.id.formregister_userid);
		userPwd = (EditText) findViewById(R.id.formregister_pwd);
		userPwdAuth = (EditText) findViewById(R.id.formregister_pwd_auth);
		btn_reg = (Button) findViewById(R.id.formregister_resiter);
		btn_reg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_layout1.setVisibility(View.VISIBLE);
				ll_layout2.setVisibility(View.GONE);
				final String strName = userName.getText().toString();
				final String strPwd = userPwd.getText().toString();
				String strAuth = userPwdAuth.getText().toString();
				if (strName.length() == 0 || strName.length() == 0 || strName.length() == 0
						|| !strPwd.equals(strAuth)) {
					ll_layout1.setVisibility(View.GONE);
					ll_layout2.setVisibility(View.VISIBLE);
					Toast.makeText(FormRegister.this, R.string.registerdata_error, Toast.LENGTH_SHORT).show();
					return;
				}
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						try {
							int flag = SelfInfo.baseMain.instant.registerOneUser(strName, strPwd);
							rHandler.sendEmptyMessage(flag);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch(IllegalStateException e1) {
							rHandler.sendEmptyMessage(3);
							e1.printStackTrace();
						}
					}
				}).start();
			}
		});
	}

	public static class RecevierHandler extends Handler {
		private WeakReference<FormRegister> wrReference;

		public RecevierHandler(FormRegister formRegister) {
			// TODO Auto-generated constructor stub
			wrReference = new WeakReference<FormRegister>(formRegister);
		}

		@Override
		public void handleMessage(Message msg) {
			// 1、注册成功 0、服务器没有返回结果2、这个账号已经存在3、注册失败
			super.handleMessage(msg);
			wrReference.get().ll_layout1.setVisibility(View.GONE);
			wrReference.get().ll_layout2.setVisibility(View.VISIBLE);
			switch (msg.what) {
			case 0:
				Toast.makeText(wrReference.get(), R.string.server_noresponse, Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(wrReference.get(), R.string.register_succeed, Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(wrReference.get(), R.string.account_existent, Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(wrReference.get(), R.string.register_error, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	}

}
