package com.xmpp.client.activities;

import java.util.ArrayList;

import com.astuetz.PagerSlidingTabStrip;
import com.xmpp.client.R;
import com.xmpp.client.config.SelfInfo;
import com.xmpp.client.fragments.AccountList;
import com.xmpp.client.fragments.AppSet;
import com.xmpp.client.fragments.ContactList;
import com.xmpp.client.service.ReceiveService;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private long mExitTime;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (SelfInfo.contactAdapter != null) {
			SelfInfo.contactAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, R.string.exit_notification, Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		try {
			// 退出登录
			SelfInfo.baseMain.instant.outFunc();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 告诉Instant自己最近的联系人记录和sp
		Intent deathintent = new Intent("com.message.transferto.deathnofifacation");
		deathintent.putParcelableArrayListExtra("lastcontact", (ArrayList<? extends Parcelable>) SelfInfo._contact);
		deathintent.putExtra("lastsp", SelfInfo.setValue);
		sendBroadcast(deathintent);
		// 终止与Instant的绑定
		SelfInfo.baseMain.unBindMyService();
		// 终止receiveService的运行
		stopService(new Intent(MainActivity.this, ReceiveService.class));
		super.onDestroy();
		System.exit(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			SelfInfo.baseMain.instant.receiveMsg();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_im);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
				getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { getResources().getString(R.string.tab_contact),
				getResources().getString(R.string.tab_list), getResources().getString(R.string.tab_set) };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return ContactList.getContactList();
			case 1:
				return AccountList.getAccountList();
			case 2:
				return AppSet.getAppSet();
			}
			return com.xmpp.client.fragments.SuperAwesomeCardFragment.newInstance(position);
		}

	}

}