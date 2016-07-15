package com.xmpp.client.aidl;

import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.aidl.SetValue;
import com.xmpp.client.aidl.Msg;

interface ServiceInstant {
	SetValue getSetValue();//�������ֵ
	void setSelfState(int state);//
	int logFunc(String name, String password, int state);//��¼��֤
	int outFunc();//�˳�����
	void insertMsg(in Msg msg);//���������¼
	List<Msg> getMsg(String who, int start, int limit);//���ĳ�������¼
	List<Contact> getContact();//��������ϵ���б�
	List<Account> getAccount();//��ú����б�
	int sendMsg(in Msg msg);//������Ϣ
	void receiveMsg();
	void removeRecord();//clear chat record
	int registerOneUser(String userName, String userPwd);
	boolean addFriend(String userID);
	void agreeOrReject(String uID, boolean agree);
	void modifyNick(String uID, String newNick);
	boolean deleteFriend(String uID);
}