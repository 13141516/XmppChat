/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\javawork\\AdXmpp\\src\\com\\xmpp\\client\\aidl\\ServiceInstant.aidl
 */
package com.xmpp.client.aidl;
public interface ServiceInstant extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.xmpp.client.aidl.ServiceInstant
{
private static final java.lang.String DESCRIPTOR = "com.xmpp.client.aidl.ServiceInstant";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.xmpp.client.aidl.ServiceInstant interface,
 * generating a proxy if needed.
 */
public static com.xmpp.client.aidl.ServiceInstant asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.xmpp.client.aidl.ServiceInstant))) {
return ((com.xmpp.client.aidl.ServiceInstant)iin);
}
return new com.xmpp.client.aidl.ServiceInstant.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getSetValue:
{
data.enforceInterface(DESCRIPTOR);
com.xmpp.client.aidl.SetValue _result = this.getSetValue();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_setSelfState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setSelfState(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_logFunc:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
int _result = this.logFunc(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_outFunc:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.outFunc();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_insertMsg:
{
data.enforceInterface(DESCRIPTOR);
com.xmpp.client.aidl.Msg _arg0;
if ((0!=data.readInt())) {
_arg0 = com.xmpp.client.aidl.Msg.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.insertMsg(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getMsg:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
java.util.List<com.xmpp.client.aidl.Msg> _result = this.getMsg(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getContact:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.xmpp.client.aidl.Contact> _result = this.getContact();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getAccount:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.xmpp.client.aidl.Account> _result = this.getAccount();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_sendMsg:
{
data.enforceInterface(DESCRIPTOR);
com.xmpp.client.aidl.Msg _arg0;
if ((0!=data.readInt())) {
_arg0 = com.xmpp.client.aidl.Msg.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.sendMsg(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_receiveMsg:
{
data.enforceInterface(DESCRIPTOR);
this.receiveMsg();
reply.writeNoException();
return true;
}
case TRANSACTION_removeRecord:
{
data.enforceInterface(DESCRIPTOR);
this.removeRecord();
reply.writeNoException();
return true;
}
case TRANSACTION_registerOneUser:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.registerOneUser(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addFriend:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.addFriend(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_agreeOrReject:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.agreeOrReject(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_modifyNick:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.modifyNick(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_deleteFriend:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.deleteFriend(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.xmpp.client.aidl.ServiceInstant
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public com.xmpp.client.aidl.SetValue getSetValue() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.xmpp.client.aidl.SetValue _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSetValue, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.xmpp.client.aidl.SetValue.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//设置项的值

@Override public void setSelfState(int state) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(state);
mRemote.transact(Stub.TRANSACTION_setSelfState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//

@Override public int logFunc(java.lang.String name, java.lang.String password, int state) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeString(password);
_data.writeInt(state);
mRemote.transact(Stub.TRANSACTION_logFunc, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//登录验证

@Override public int outFunc() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_outFunc, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//退出操作

@Override public void insertMsg(com.xmpp.client.aidl.Msg msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((msg!=null)) {
_data.writeInt(1);
msg.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_insertMsg, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//插入聊天记录

@Override public java.util.List<com.xmpp.client.aidl.Msg> getMsg(java.lang.String who, int start, int limit) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.xmpp.client.aidl.Msg> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(who);
_data.writeInt(start);
_data.writeInt(limit);
mRemote.transact(Stub.TRANSACTION_getMsg, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.xmpp.client.aidl.Msg.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获得某人聊天记录

@Override public java.util.List<com.xmpp.client.aidl.Contact> getContact() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.xmpp.client.aidl.Contact> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getContact, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.xmpp.client.aidl.Contact.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获得最近联系人列表

@Override public java.util.List<com.xmpp.client.aidl.Account> getAccount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.xmpp.client.aidl.Account> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAccount, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.xmpp.client.aidl.Account.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获得好友列表

@Override public int sendMsg(com.xmpp.client.aidl.Msg msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((msg!=null)) {
_data.writeInt(1);
msg.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_sendMsg, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//发送消息

@Override public void receiveMsg() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_receiveMsg, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeRecord() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_removeRecord, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//clear chat record

@Override public int registerOneUser(java.lang.String userName, java.lang.String userPwd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(userName);
_data.writeString(userPwd);
mRemote.transact(Stub.TRANSACTION_registerOneUser, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean addFriend(java.lang.String userID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(userID);
mRemote.transact(Stub.TRANSACTION_addFriend, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void agreeOrReject(java.lang.String uID, boolean agree) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(uID);
_data.writeInt(((agree)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_agreeOrReject, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void modifyNick(java.lang.String uID, java.lang.String newNick) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(uID);
_data.writeString(newNick);
mRemote.transact(Stub.TRANSACTION_modifyNick, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean deleteFriend(java.lang.String uID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(uID);
mRemote.transact(Stub.TRANSACTION_deleteFriend, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getSetValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setSelfState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_logFunc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_outFunc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_insertMsg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getMsg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getAccount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_sendMsg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_receiveMsg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_removeRecord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_registerOneUser = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_addFriend = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_agreeOrReject = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_modifyNick = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_deleteFriend = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
}
public com.xmpp.client.aidl.SetValue getSetValue() throws android.os.RemoteException;
//设置项的值

public void setSelfState(int state) throws android.os.RemoteException;
//

public int logFunc(java.lang.String name, java.lang.String password, int state) throws android.os.RemoteException;
//登录验证

public int outFunc() throws android.os.RemoteException;
//退出操作

public void insertMsg(com.xmpp.client.aidl.Msg msg) throws android.os.RemoteException;
//插入聊天记录

public java.util.List<com.xmpp.client.aidl.Msg> getMsg(java.lang.String who, int start, int limit) throws android.os.RemoteException;
//获得某人聊天记录

public java.util.List<com.xmpp.client.aidl.Contact> getContact() throws android.os.RemoteException;
//获得最近联系人列表

public java.util.List<com.xmpp.client.aidl.Account> getAccount() throws android.os.RemoteException;
//获得好友列表

public int sendMsg(com.xmpp.client.aidl.Msg msg) throws android.os.RemoteException;
//发送消息

public void receiveMsg() throws android.os.RemoteException;
public void removeRecord() throws android.os.RemoteException;
//clear chat record

public int registerOneUser(java.lang.String userName, java.lang.String userPwd) throws android.os.RemoteException;
public boolean addFriend(java.lang.String userID) throws android.os.RemoteException;
public void agreeOrReject(java.lang.String uID, boolean agree) throws android.os.RemoteException;
public void modifyNick(java.lang.String uID, java.lang.String newNick) throws android.os.RemoteException;
public boolean deleteFriend(java.lang.String uID) throws android.os.RemoteException;
}
