package com.xmpp.client.aidl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;

import com.xmpp.client.R;
import com.xmpp.client.config.AppRun;
import com.xmpp.client.config.InfoConfig;
import com.xmpp.client.util.AppUtils;
import com.xmpp.client.util.FileUtil;
import com.xmpp.client.util.TimeRender;
import com.xmpp.client.util.XmppTool;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class Instant extends Service {
	private Account from;
	private Map<String, Chat> _map;
	private InstantBinder instantBinder;
	private Intent intent = new Intent("com.message.transferto.servicewith");
	private Intent pintent = new Intent("com.message.transferto.servicepresence");
	private Intent fintent = new Intent("com.message.transferto.serviceadd");
	private DeathReceiver dReceiver = new DeathReceiver();
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent lintent) {
			// TODO Auto-generated method stub
			String action = lintent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = connectivityManager.getActiveNetworkInfo();
				if (info == null || !info.isAvailable()) {
					Toast.makeText(Instant.this, R.string.net_state, Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	public void onDestroy() {
		unregisterReceiver(mReceiver);
		unregisterReceiver(dReceiver);
		super.onDestroy();
		System.exit(0);
	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		_map = new HashMap<String, Chat>();
		instantBinder = new InstantBinder();
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
		IntentFilter dFilter = new IntentFilter();
		dFilter.addAction("com.message.transferto.deathnofifacation");
		registerReceiver(dReceiver, dFilter);
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return instantBinder;
	}

	public String encryptString(String textEnc) {
		String result = "";
		try {
			KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) AppRun.keyStore
					.getEntry(InfoConfig.PAIR_KEY_ALIAS, null);
			RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();
			Cipher input = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
			input.init(Cipher.ENCRYPT_MODE, publicKey);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, input);
			cipherOutputStream.write(textEnc.getBytes("UTF-8"));
			cipherOutputStream.close();
			byte[] vals = outputStream.toByteArray();
			result = Base64.encodeToString(vals, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String decryptString(String textDec) {
		String finalText = "";
		try {
			KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) AppRun.keyStore
					.getEntry(InfoConfig.PAIR_KEY_ALIAS, null);
			RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
			Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
			output.init(Cipher.DECRYPT_MODE, privateKey);
			CipherInputStream cipherInputStream = new CipherInputStream(
					new ByteArrayInputStream(Base64.decode(textDec, Base64.DEFAULT)), output);
			ArrayList<Byte> values = new ArrayList<Byte>();
			int nextByte;
			while ((nextByte = cipherInputStream.read()) != -1) {
				values.add((byte) nextByte);
			}
			byte[] bytes = new byte[values.size()];
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = values.get(i).byteValue();
			}
			finalText = new String(bytes, 0, bytes.length, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalText;
	}

	public class InstantBinder extends ServiceInstant.Stub {

		@Override
		public int logFunc(String name, String password, int state) throws RemoteException {
			// 用户登录模块,1表示登录成功，2表示登录失败,3表示网络问题
			int flag = 1;
			try {
				XMPPConnection connection = XmppTool.getConnection();
				connection.login(name, password, InfoConfig.XMPP_RESOURCES);
				Presence presence;
				if (state == 1) {
					presence = new Presence(Presence.Type.available);
				} else {
					presence = new Presence(Presence.Type.unavailable);
				}
				connection.sendPacket(presence);
				from = new Account(name + "@" + InfoConfig.SERVICE_NAME, name, 1);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				flag = 2;
				e.printStackTrace();
			} catch (IllegalStateException e1) {
				flag = 3;
				e1.printStackTrace();
			}
			return flag;
		}

		@Override
		public int outFunc() throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			int flag = 1;
			try {
				XmppTool.getConnection().getAccountManager().deleteAccount();
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				flag = 0;
				e.printStackTrace();
			}
			return flag;
		}

		@Override
		public void insertMsg(Msg msg) throws RemoteException {
			// TODO Auto-generated method stub
			AppRun.dataDao.insertMsgSql(msg);
		}

		@Override
		public List<Msg> getMsg(String who, int start, int limit) throws RemoteException {
			// TODO Auto-generated method stub
			List<Msg> _list = AppRun.dataDao.getMsgListPage(who, start, limit);
			return _list;
		}

		@Override
		public List<Contact> getContact() throws RemoteException {
			// TODO Auto-generated method stub
			List<Contact> _contact = AppRun.dataDao.getContactList();
			return _contact;
		}

		@Override
		public List<Account> getAccount() throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			List<Account> _list = new ArrayList<Account>();
			int isonline = 0;
			final ChatManager cm = XmppTool.getConnection().getChatManager();
			Roster roster = XmppTool.getConnection().getRoster();
			roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
			Collection<RosterEntry> rosterEntry = roster.getEntries();
			Iterator<RosterEntry> i = rosterEntry.iterator();
			while (i.hasNext()) {
				RosterEntry entry = i.next();
				String key = entry.getUser().substring(0, AppUtils.getIndex(entry.getUser(), '@'))
						+ InfoConfig.SERVICE_NAME;
				if (roster.getPresence(key).getType() == Presence.Type.available) {
					isonline = 1;
				} else {
					isonline = 0;
				}
				Account account = new Account(key, entry.getName(), isonline);
				_list.add(account);
				Chat chat = cm.createChat(key, null);
				_map.put(key, chat);
			}
			PacketFilter filter = new AndFilter(new PacketTypeFilter(Presence.class));
			PacketListener listener = new PacketListener() {
				@Override
				public void processPacket(Packet packet) {
					// TODO Auto-generated method stub
					if (packet instanceof Presence) {
						Presence presence = (Presence) packet;
						String from = presence.getFrom();// 发送方
						String to = presence.getTo();// 接收方
						if (presence.getType().equals(Presence.Type.subscribe)) {
							// receive request
							Msg message = new Msg(from, to, "subscribe", 0, 1, 0);
							fintent.putExtra("value", message);
							sendBroadcast(fintent);
						} else if (presence.getType().equals(Presence.Type.subscribed)) {
							// two people were friends
							Msg message = new Msg(from, to, "subscribed", 0, 2, 0);
							Chat chat = cm.createChat(from, null);
							_map.put(from, chat);
							fintent.putExtra("value", message);
							sendBroadcast(fintent);
						} else if (presence.getType().equals(Presence.Type.unsubscribe)) {
							// somebody want to reject to add you as friend
							Msg message = new Msg(from, to, "subscribed", 0, 3, 0);
							_map.remove(message.getKeyCom());
							fintent.putExtra("value", message);
							sendBroadcast(fintent);
						} else if (presence.getType().equals(Presence.Type.unsubscribed)) {
							// notify that somebody and you are not friends
							Msg message = new Msg(from, to, "unsubscribed", 0, 4, 0);
							fintent.putExtra("value", message);
							sendBroadcast(fintent);
						} else if (presence.getType().equals(Presence.Type.unavailable)) {
							String key = from.substring(0, AppUtils.getIndex(from, '@')) + InfoConfig.SERVICE_NAME;
							pintent.putExtra("key", key);
							pintent.putExtra("value", 0);
							sendBroadcast(pintent);
						} else if (presence.getType().equals(Presence.Type.available)) {
							String key = from.substring(0, AppUtils.getIndex(from, '@')) + InfoConfig.SERVICE_NAME;
							pintent.putExtra("key", key);
							pintent.putExtra("value", 1);
							sendBroadcast(pintent);
						}
					}
				}
			};
			XmppTool.getConnection().addPacketListener(listener, filter);
			return _list;

		}

		@Override
		public int sendMsg(Msg msg) throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			Log.i("test", "instant " + msg.getMsg());
			int flag = 1;
			try {
				Chat chat = _map.get(msg.keyTo);
				if (chat == null) {
					flag = 0;
				} else {
					switch (msg.getDataType()) {
					case InfoConfig.TYPE_TEXT:
						Message message = new Message();
						message.setBody(msg.getMsg());
						message.setProperty("dataType", msg.getDataType());
						chat.sendMessage(message);
						break;
					case InfoConfig.TYPE_SOUND:
						Message msge = new Message();
						msge.setBody(FileUtil.getBase64StringFromFile(msg.getMsg()));
						msge.setProperty("dataType", msg.getDataType());
						msge.setProperty("fileName", FileUtil.getFileName(msg.getMsg()));
						Log.i("test", FileUtil.getFileName(msg.getMsg()));
						chat.sendMessage(msge);
						break;
					default:
						break;
					}
					insertMsg(msg);
				}
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				flag = 0;
				e.printStackTrace();
			}
			return flag;
		}

		@Override
		public void receiveMsg() throws RemoteException {
			// TODO Auto-generated method stub
			ChatManager cm = XmppTool.getConnection().getChatManager();
			cm.addChatListener(new ChatManagerListener() {

				@Override
				public void chatCreated(Chat chat, boolean arg1) {
					// TODO Auto-generated method stub
					chat.addMessageListener(new MessageListener() {

						@Override
						public void processMessage(Chat arg0, Message arg1) {
							// TODO Auto-generated method stub
							try {
								int dataType = (Integer) arg1.getProperty("dataType");
								String other = arg1.getFrom().substring(0, AppUtils.getIndex(arg1.getFrom(), '/') - 1);
								switch (dataType) {
								case InfoConfig.TYPE_TEXT:
									Msg msg = new Msg(from.getKey(), other, arg1.getBody(), TimeRender.getCurrentTime(),
											InfoConfig.MESSAGE_RECEIVE, dataType);
									insertMsg(msg);
									intent.putExtra("newMessage", msg);
									sendBroadcast(intent);
									break;
								case InfoConfig.TYPE_SOUND:
									String fileName = (String) arg1.getProperty("fileName");
									String filePath = InfoConfig.SAVE_SOUND_PATH + "/" + fileName;
									Log.i("test", "i have " + filePath);
									FileUtil.saveFileByBase64(arg1.getBody(), filePath);
									Msg smsg = new Msg(from.getKey(), other, filePath, TimeRender.getCurrentTime(),
											InfoConfig.MESSAGE_RECEIVE, dataType);
									insertMsg(smsg);
									intent.putExtra("newMessage", smsg);
									sendBroadcast(intent);
									break;
								default:
									break;
								}
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}
					});
				};

			});
		}

		@Override
		public SetValue getSetValue() throws RemoteException {
			// TODO Auto-generated method stub
			SharedPreferences sps = AppRun.sp;
			int xaccount = sps.getInt("account", 1);
			int xonline = sps.getInt("online", 1);
			int xnotify = sps.getInt("notify", 1);
			String xuseid = sps.getString("useid", "");
			String xusepwd = sps.getString("usepwd", "");
			if ((xaccount != 1) || xuseid == null || xusepwd == null) {
				return new SetValue(xaccount, xonline, xnotify, "", "");
			} else {
				return new SetValue(xaccount, xonline, xnotify, xuseid, decryptString(xusepwd));
			}
		}

		@Override
		public void setSelfState(int state) throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			XMPPConnection connection = XmppTool.getConnection();
			Presence presence;
			if (state == 1) {
				presence = new Presence(Presence.Type.available);
			} else {
				presence = new Presence(Presence.Type.unavailable);
			}
			connection.sendPacket(presence);
		}

		@Override
		public void removeRecord() throws RemoteException {
			// TODO Auto-generated method stub
			AppRun.dataDao.deleteAllMsg();
			FileUtil.deleteDirectory(InfoConfig.SAVE_SOUND_PATH);
		}

		@Override
		public int registerOneUser(String userName, String userPwd) throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			XMPPConnection connection = XmppTool.getConnection();
			Registration reg = new Registration();
			reg.setType(IQ.Type.SET);
			reg.setTo(connection.getServiceName());
			reg.setUsername(userName);// 注意这里createAccount注册时
			reg.setPassword(userPwd);
			reg.addAttribute("android", InfoConfig.XMPP_RESOURCES);// 设置注册时使用的手机
			PacketFilter filter = new AndFilter(new PacketIDFilter(reg.getPacketID()), new PacketTypeFilter(IQ.class));
			PacketCollector collector = connection.createPacketCollector(filter);
			connection.sendPacket(reg);
			IQ result = (IQ) collector.nextResult(SmackConfiguration.getPacketReplyTimeout());
			collector.cancel();// 停止请求results（是否成功的结果）
			if (result == null) {
				/* server no response */
				return 0;
			} else if (result.getType() == IQ.Type.RESULT) {
				/* register succeed */
				return 1;
			} else {
				if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
					/* account has existent */
					return 2;
				} else {
					/* register error */
					return 3;
				}
			}
		}

		@Override
		public boolean addFriend(String userID) throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			boolean flag = false;
			XMPPConnection connection = XmppTool.getConnection();
			Roster roster = connection.getRoster();
			try {
				roster.createEntry(userID + "@" + InfoConfig.SERVICE_NAME, userID, null);
				flag = true;
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				flag = false;
				e.printStackTrace();
			} 
			return flag;
		}

		@Override
		public void agreeOrReject(String uID, boolean agree) throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			XMPPConnection con = XmppTool.getConnection();
			Presence presence;
			if (agree) {
				presence = new Presence(Presence.Type.subscribed);
			} else {
				presence = new Presence(Presence.Type.unsubscribe);
			}
			presence.setTo(uID);
			con.sendPacket(presence);
			if (agree) {
				addFriend(uID.substring(0, uID.indexOf('@')));
			}
		}

		@Override
		public void modifyNick(String uID, String newNick) throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			XMPPConnection con = XmppTool.getConnection();
			Roster roster = con.getRoster();
			roster.getEntry(uID).setName(newNick);
		}

		@Override
		public boolean deleteFriend(String uID) throws RemoteException, IllegalStateException {
			// TODO Auto-generated method stub
			boolean flag = true;
			XMPPConnection con = XmppTool.getConnection();
			Roster roster = con.getRoster();
			RosterEntry entry = roster.getEntry(uID);
			try {
				roster.removeEntry(entry);
				_map.remove(uID);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
			return flag;
		}
	}

	public class DeathReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			List<Contact> _contact = intent.getParcelableArrayListExtra("lastcontact");
			SetValue setValue = intent.getParcelableExtra("lastsp");
			AppRun.dataDao.deleteAllContact();
			AppRun.dataDao.insertContactSql(_contact);
			SharedPreferences.Editor editor = AppRun.sp.edit();
			editor.putInt("account", setValue.getAccount());
			editor.putInt("online", setValue.getOnline());
			editor.putInt("notify", setValue.getNotify());
			if (setValue.getAccount() == 1) {
				editor.putString("useid", setValue.getUseid());
				editor.putString("usepwd", encryptString(setValue.getUserpwd()));
			} else {
				editor.putString("useid", "");
				editor.putString("usepwd", "");
			}
			editor.commit();
			stopSelf();
		}
	}

}
