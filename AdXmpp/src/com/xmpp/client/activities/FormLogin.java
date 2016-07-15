package com.xmpp.client.activities;

import java.util.ArrayList;
import java.util.List;

import com.xmpp.client.R;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.aidl.Msg;
import com.xmpp.client.config.InfoConfig;
import com.xmpp.client.config.SelfInfo;
import com.xmpp.client.service.ReceiveService;
import com.xmpp.client.util.AppUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FormLogin extends Activity implements OnClickListener {

	private EditText useridText, pwdText;
	private LinearLayout layout1, layout2;
	private Button btsave, btcancel;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//获得相应的设置
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formlogin);
		this.useridText = (EditText) findViewById(R.id.formlogin_userid);
		this.pwdText = (EditText) findViewById(R.id.formlogin_pwd);

		this.layout1 = (LinearLayout) findViewById(R.id.formlogin_layout1);
		this.layout2 = (LinearLayout) findViewById(R.id.formlogin_layout2);

		btsave = (Button) findViewById(R.id.formlogin_btsubmit);
		btsave.setOnClickListener(this);
		btcancel = (Button) findViewById(R.id.formlogin_btcancel);
		btcancel.setOnClickListener(this);
		try {
			SelfInfo.setValue = SelfInfo.baseMain.instant.getSetValue();
			if(SelfInfo.setValue.getAccount() == 1) {
				this.useridText.setText(SelfInfo.setValue.getUseid());
				this.pwdText.setText(SelfInfo.setValue.getUserpwd());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		AppUtils.shutInput(FormLogin.this, v);
		switch (v.getId()) {
		case R.id.formlogin_btsubmit:
			final String USERID = this.useridText.getText().toString();
			final String PWD = this.pwdText.getText().toString();
			if(USERID == null || PWD == null) {
				return;
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					handler.sendEmptyMessage(1);
					try {
						int flag = SelfInfo.baseMain.instant.logFunc(USERID, PWD, SelfInfo.setValue.getOnline());
						if(flag == 3) {
							handler.sendEmptyMessage(3);
							return;
						}
						SelfInfo._addFrieds = new ArrayList<Msg>();
						SelfInfo.setValue.setUseid(USERID);
						SelfInfo.setValue.setUserpwd(PWD);
						SelfInfo.From = new Account(USERID + "@" + InfoConfig.SERVICE_NAME, USERID, SelfInfo.setValue.getOnline());
						List<Account> _list = SelfInfo.baseMain.instant.getAccount();
						if(_list == null) {
							_list = new ArrayList<Account>();
						} 
						SelfInfo._list = _list;
						List<Contact> _contact = SelfInfo.baseMain.instant.getContact();
						if(_contact == null) {
							_contact = new ArrayList<Contact>();
						}
						SelfInfo._contact = _contact;
						startService(new Intent(FormLogin.this, ReceiveService.class));
						startActivity( new Intent(FormLogin.this, MainActivity.class));
						FormLogin.this.finish();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						handler.sendEmptyMessage(2);
						e.printStackTrace();
					} catch(IllegalStateException e1) {
						handler.sendEmptyMessage(2);
						e1.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.formlogin_btcancel:
			Intent intent = new Intent(FormLogin.this, FormRegister.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
				break;
			case 2:
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
				Toast.makeText(FormLogin.this, R.string.show_login_error, Toast.LENGTH_SHORT).show();
				break;
			case 3:
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
				Toast.makeText(FormLogin.this, R.string.net_state, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
}