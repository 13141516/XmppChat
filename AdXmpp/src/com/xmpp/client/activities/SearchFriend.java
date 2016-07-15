package com.xmpp.client.activities;

import com.xmpp.client.R;
import com.xmpp.client.adapter.FriendAdapter;
import com.xmpp.client.config.SelfInfo;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class SearchFriend extends Activity {
	private SearchView sv_friends;
	private ListView lv_friend;

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
		setContentView(R.layout.searchfriend);
		ActionBar bar = getActionBar();
		bar.setTitle(getResources().getString(R.string.search_friends));
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);
		lv_friend = (ListView) findViewById(R.id.lv_friendlv);
		sv_friends = (SearchView) findViewById(R.id.sv_friends_e);
		sv_friends.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				try {
					if(SelfInfo.baseMain.instant.addFriend(query)) {
						Toast.makeText(SearchFriend.this, R.string.frind_request, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SearchFriend.this, R.string.frind_request_error, Toast.LENGTH_SHORT).show();
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(IllegalStateException e1) {
					Toast.makeText(SearchFriend.this, R.string.state_error, Toast.LENGTH_SHORT).show();
					e1.printStackTrace();
				}
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		loadView();
	}

	private void loadView() {
		// TODO Auto-generated method stub
		SelfInfo.friendAdapter = new FriendAdapter(SelfInfo._addFrieds, SearchFriend.this);
		lv_friend.setAdapter(SelfInfo.friendAdapter);
	}
}
