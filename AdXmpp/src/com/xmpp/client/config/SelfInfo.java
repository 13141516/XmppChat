package com.xmpp.client.config;

import java.util.List;

import com.xmpp.client.activities.BaseMain;
import com.xmpp.client.adapter.AccountListAdapter;
import com.xmpp.client.adapter.ContactListAdapter;
import com.xmpp.client.adapter.FriendAdapter;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.aidl.Msg;
import com.xmpp.client.aidl.SetValue;

public class SelfInfo {
	public static Account From;
	public static Account To;
	public static boolean isChat = false;

	public static List<Account> _list;
	public static List<Contact> _contact;
	public static List<Msg> _addFrieds;

	public static ContactListAdapter contactAdapter;
	public static AccountListAdapter accountAdapter;
	public static FriendAdapter friendAdapter;
	
	public static BaseMain baseMain;
	
	public static SetValue setValue;
	
}
