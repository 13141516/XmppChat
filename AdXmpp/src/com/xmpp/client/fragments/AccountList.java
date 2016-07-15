package com.xmpp.client.fragments;

import com.xmpp.client.R;
import com.xmpp.client.activities.FormClient;
import com.xmpp.client.adapter.AccountListAdapter;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.config.SelfInfo;
import com.xmpp.client.util.TimeRender;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

public class AccountList extends Fragment implements OnItemClickListener {
	private ListView listview;
	private LinearLayout layout1, layout2;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		MenuInflater menuInflate = getActivity().getMenuInflater();
		menuInflate.inflate(R.menu.accountmenu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		final AdapterView.AdapterContextMenuInfo te = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.account_delete:
			AlertDialog.Builder notif = new Builder(getActivity());
			notif.setTitle(R.string.delete_friend)
					.setMessage(getActivity().getResources().getString(R.string.is_delete)
							+ SelfInfo._list.get(te.position).getNick())
					.setPositiveButton(R.string.btn_confirm, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							try {
								String keyID = SelfInfo._list.get(te.position).getKey();
								boolean flag = SelfInfo.baseMain.instant.deleteFriend(keyID);
								if (flag) {
									for (Contact contact : SelfInfo._contact) {
										if (contact.getContactKey().equals(keyID)) {
											SelfInfo._contact.remove(contact);
											SelfInfo.accountAdapter.notifyDataSetChanged();
											break;
										}
									}
									for (Account account : SelfInfo._list) {
										if (account.getKey().equals(keyID)) {
											SelfInfo._list.remove(account);
											SelfInfo.accountAdapter.notifyDataSetChanged();
											break;
										}
									}
								}
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalStateException e1) {
								Toast.makeText(getActivity(), R.string.state_error, Toast.LENGTH_SHORT).show();
								e1.printStackTrace();
							}
						}
					}).setNegativeButton(R.string.btn_cancel, null).show();
			break;
		case R.id.nick_modify:
			final EditText et_nick = new EditText(getActivity());
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage(SelfInfo._list.get(te.position).getNick()
					+ getActivity().getResources().getString(R.string.modifyinfo)).setTitle(R.string.nick_modify)
					.setView(et_nick).setPositiveButton(R.string.btn_confirm, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String strNick = et_nick.getText().toString();
							if (strNick.isEmpty()) {
								Toast.makeText(getActivity(), "Cannot be null", Toast.LENGTH_SHORT).show();
								return;
							} else {
								try {
									SelfInfo.baseMain.instant.modifyNick(SelfInfo._list.get(te.position).getKey(),
											strNick);
									SelfInfo._list.get(te.position).setNick(strNick);
									SelfInfo.accountAdapter.notifyDataSetChanged();
									SelfInfo.contactAdapter.notifyDataSetChanged();
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalStateException e1) {
									Toast.makeText(getActivity(), "×´Ì¬ÓÐÎó", Toast.LENGTH_SHORT).show();
									e1.printStackTrace();
								}
							}
						}
					}).setNegativeButton(R.string.btn_cancel, null).show();
			break;
		case R.id.click_cancel:
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.accountlist, null);
		layout1 = (LinearLayout) view.findViewById(R.id.account_load_layout1);
		layout2 = (LinearLayout) view.findViewById(R.id.account_show_layout2);
		listview = (ListView) view.findViewById(R.id.lv_account);
		registerForContextMenu(listview);
		listview.setOnItemClickListener(this);
		loadView();
		return view;
	}

	private void loadView() {
		// TODO Auto-generated method stub
		SelfInfo.accountAdapter = new AccountListAdapter(SelfInfo._list, getActivity());
		listview.setAdapter(SelfInfo.accountAdapter);
		layout1.setVisibility(View.GONE);
		layout2.setVisibility(View.VISIBLE);
	}

	private AccountList() {
	}

	private static AccountList accountList = null;

	public static AccountList getAccountList() {
		if (accountList == null) {
			accountList = new AccountList();
		}
		return accountList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		boolean _flag = true;
		String str = SelfInfo._list.get(position).getKey();
		for (Contact conta : SelfInfo._contact) {
			if (str.equals(conta.getContactKey())) {
				_flag = false;
				conta.setNewMessage(0);
				break;
			}
		}
		if (_flag) {
			Contact contact = new Contact(str, " ", TimeRender.getTimeString(), 0, 0);
			SelfInfo._contact.add(contact);
		}
		Intent intent = new Intent(getActivity(), FormClient.class);
		intent.putExtra("position", position);
		startActivity(intent);
	}

}
