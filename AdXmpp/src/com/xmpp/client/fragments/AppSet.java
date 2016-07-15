package com.xmpp.client.fragments;

import com.xmpp.client.R;
import com.xmpp.client.activities.SearchFriend;
import com.xmpp.client.config.SelfInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AppSet extends Fragment implements OnCheckedChangeListener, View.OnClickListener {
	private TextView tv_person, tv_set_search;
	private Switch switch_account, switch_online, switch_notify;
	private Button btn_clear, btn_logout;

	private void loadView() {
		if (SelfInfo.setValue.getAccount() != 0) {
			switch_account.setChecked(true);
		} else {
			switch_account.setChecked(false);
		}
		if (SelfInfo.setValue.getOnline() != 0) {
			switch_online.setChecked(true);
		} else {
			switch_online.setChecked(false);
		}
		if (SelfInfo.setValue.getNotify() != 0) {
			switch_notify.setChecked(true);
		} else {
			switch_notify.setChecked(false);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.setlist, null);
		tv_person = (TextView) view.findViewById(R.id.tv_set_selfinfo);
		tv_set_search = (TextView) view.findViewById(R.id.tv_set_search);
		switch_account = (Switch) view.findViewById(R.id.switch_account);
		switch_online = (Switch) view.findViewById(R.id.switch_online);
		switch_notify = (Switch) view.findViewById(R.id.switch_notify);
		btn_clear = (Button) view.findViewById(R.id.data_clear);
		btn_logout = (Button) view.findViewById(R.id.btn_logout);
		loadView();
		switch_account.setOnCheckedChangeListener(AppSet.this);
		switch_online.setOnCheckedChangeListener(AppSet.this);
		switch_notify.setOnCheckedChangeListener(AppSet.this);
		btn_clear.setOnClickListener(AppSet.this);
		btn_logout.setOnClickListener(AppSet.this);
		tv_set_search.setOnClickListener(AppSet.this);
		return view;
	}

	private AppSet() {
	}

	private static AppSet appset = null;

	public static AppSet getAppSet() {
		if (appset == null) {
			appset = new AppSet();
		}
		return appset;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.switch_account:
			if (isChecked) {
				SelfInfo.setValue.setAccount(1);
			} else {
				SelfInfo.setValue.setAccount(0);
			}
			break;
		case R.id.switch_online:
			try {
				if (isChecked) {
					SelfInfo.setValue.setOnline(1);
				} else {
					SelfInfo.setValue.setOnline(0);
				}
				SelfInfo.baseMain.instant.setSelfState(SelfInfo.setValue.getOnline());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(), R.string.set_notifacation, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} catch(IllegalStateException e1) {
				Toast.makeText(getActivity(), R.string.set_notifacation, Toast.LENGTH_SHORT).show();
				e1.printStackTrace();
			}
			break;
		case R.id.switch_notify:
			if (isChecked) {
				SelfInfo.setValue.setNotify(1);
			} else {
				SelfInfo.setValue.setNotify(0);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.data_clear:
			try {
				SelfInfo._contact.removeAll(SelfInfo._contact);
				SelfInfo.accountAdapter.notifyDataSetChanged();
				SelfInfo.baseMain.instant.removeRecord();
				Toast.makeText(getActivity(), R.string.clear_notifacation, Toast.LENGTH_SHORT).show();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(), R.string.set_notifacation, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} catch(IllegalStateException e1) {
				Toast.makeText(getActivity(), R.string.set_notifacation, Toast.LENGTH_SHORT).show();
				e1.printStackTrace();
			}
			break;
		case R.id.btn_logout:
			getActivity().finish();
			break;
		case R.id.tv_set_search:
			Intent intent = new Intent(getActivity(), SearchFriend.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
