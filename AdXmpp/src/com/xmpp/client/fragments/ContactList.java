package com.xmpp.client.fragments;

import com.xmpp.client.R;
import com.xmpp.client.activities.FormClient;
import com.xmpp.client.adapter.ContactListAdapter;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.config.SelfInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class ContactList extends Fragment implements OnItemClickListener {
	private ListView listView;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		MenuInflater menuInflate = getActivity().getMenuInflater();
		menuInflate.inflate(R.menu.contactmenu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item_delete:
			AdapterView.AdapterContextMenuInfo te = (AdapterContextMenuInfo) item.getMenuInfo();
			SelfInfo._contact.remove(te.position);
			SelfInfo.contactAdapter.notifyDataSetChanged();
			break;
		case R.id.item_cancel:
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.contactlist, null);
		listView = (ListView) view.findViewById(R.id.lv_contactlist);
		listView.setOnItemClickListener(this);
		// ≥ı ºªØlistview
		SelfInfo.contactAdapter = new ContactListAdapter(SelfInfo._contact, getActivity());
		listView.setAdapter(SelfInfo.contactAdapter);
		// register contextview for listview
		registerForContextMenu(listView);
		return view;
	}

	private ContactList() {
	}

	private static ContactList contactList = null;

	public static ContactList getContactList() {
		if (contactList == null) {
			contactList = new ContactList();
		}
		return contactList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		SelfInfo._contact.get(position).setNewMessage(0);
		String str = SelfInfo._contact.get(position).getContactKey();
		int index = 0;
		for (Account account : SelfInfo._list) {
			if (account.getKey().equals(str)) {
				break;
			}
			index++;
		}
		Intent intent = new Intent(getActivity(), FormClient.class);
		intent.putExtra("position", index);
		startActivity(intent);
	}
}
