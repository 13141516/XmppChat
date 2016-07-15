package com.xmpp.client.aidl;

import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.aidl.SetValue;
import com.xmpp.client.aidl.Msg;

interface ServiceInstant {
	SetValue getSetValue();//设置项的值
	void setSelfState(int state);//
	int logFunc(String name, String password, int state);//登录验证
	int outFunc();//退出操作
	void insertMsg(in Msg msg);//插入聊天记录
	List<Msg> getMsg(String who, int start, int limit);//获得某人聊天记录
	List<Contact> getContact();//获得最近联系人列表
	List<Account> getAccount();//获得好友列表
	int sendMsg(in Msg msg);//发送消息
	void receiveMsg();
	void removeRecord();//clear chat record
	int registerOneUser(String userName, String userPwd);
	boolean addFriend(String userID);
	void agreeOrReject(String uID, boolean agree);
	void modifyNick(String uID, String newNick);
	boolean deleteFriend(String uID);
}