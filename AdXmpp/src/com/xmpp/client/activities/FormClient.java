package com.xmpp.client.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xmpp.client.R;
import com.xmpp.client.aidl.Account;
import com.xmpp.client.aidl.Contact;
import com.xmpp.client.aidl.Msg;
import com.xmpp.client.config.InfoConfig;
import com.xmpp.client.config.SelfInfo;
import com.xmpp.client.util.AppUtils;
import com.xmpp.client.util.FileUtil;
import com.xmpp.client.util.RegexUtil;
import com.xmpp.client.util.TimeRender;
import com.xmpp.client.view.MotionView;
import com.xmpp.client.view.RecordButton;
import com.xmpp.client.view.RecordButton.OnFinishedRecordListener;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发送文本和文件的聊天页面
 */
public class FormClient extends Activity {

	private MyAdapter adapter;
	private List<Msg> listMsg;

	private EditText msgText;
	private ProgressBar pb; // 接收文件时的进度条
	private PullToRefreshListView listview;
	private ImageView btsend, btvoice;
	private RecordButton recordButton;

	private MsgReceiver msgReceiver;

	private int page = 1;
	private int recorderNumber = 0;
	private String timeStr;
	private boolean isVoice = false;
	private boolean isSimle = false;
	private SoundPool soundPool;
	private Map<String, Integer> _map;

	private ViewPager vPager;
	private RelativeLayout rl_vp;
	private LinearLayout ll_pot;
	private MotionView mv;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void initView() {
		// 初始化listview
		listview = (PullToRefreshListView) findViewById(R.id.formclient_listview);
		this.adapter = new MyAdapter(FormClient.this);
		listview.setAdapter(adapter);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				// 显示最后更新的时间
				page++;
				try {
					listMsg = SelfInfo.baseMain.instant.getMsg(SelfInfo.To.getKey(), 0, page * InfoConfig.pageSize);
					if (recorderNumber == listMsg.size()) {
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(getResources().getString(R.string.nomoredata));
					} else {
						Collections.reverse(listMsg);
						adapter.notifyDataSetChanged();
						recorderNumber = listMsg.size();
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(timeStr);
					}
					timeStr = getResources().getString(R.string.refreshtime)
							+ TimeRender.getDate(TimeRender.getCurrentTime());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listview.postDelayed(new Runnable() {
					@Override
					public void run() {
						listview.onRefreshComplete();
					}
				}, 1000);
			}
		});
		
		// 初始化相关组件
		this.msgText = (EditText) findViewById(R.id.formclient_text);
		this.pb = (ProgressBar) findViewById(R.id.formclient_pb);
		this.vPager = (ViewPager) findViewById(R.id.viewPager);
		this.ll_pot = (LinearLayout) findViewById(R.id.viewGroup);
		this.rl_vp = (RelativeLayout) findViewById(R.id.rl_emotion);
		mv = new MotionView(FormClient.this, vPager, ll_pot, msgText);
		// 初始化聊天会话
		btvoice = (ImageView) findViewById(R.id.formclient_btnsend_voice);
		btsend = (ImageView) findViewById(R.id.formclient_btsend_txt);
		recordButton = (RecordButton) findViewById(R.id.btn_record);
		msgText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_vp.setVisibility(View.GONE);
				isSimle = false;
				btsend.setImageResource(R.drawable.icon_send_w);
			}
		});

		recordButton.setOnFinishedRecordListener(new OnFinishedRecordListener() {

			@Override
			public void onFinishedRecord(String audioPath, int time) {
				// TODO Auto-generated method stub
				Msg args = new Msg(SelfInfo.From.getKey(), SelfInfo.To.getKey(), audioPath, TimeRender.getCurrentTime(),
						InfoConfig.MESSAGE_SEND, InfoConfig.TYPE_SOUND);
				try {
					if (SelfInfo.baseMain.instant.sendMsg(args) == 1) {
						android.os.Message message = handler.obtainMessage();
						message.what = InfoConfig.MSG_TEXT_RECEIVED_SUCCESS;
						message.obj = args;
						message.sendToTarget();
						for (Contact contact : SelfInfo._contact) {
							if (SelfInfo.To.getKey().equals(contact.getContactKey())) {
								contact.setDate(TimeRender.getDate(args.getDate()));
								contact.setLastMessage(args.getMsg());
								contact.setDataType(InfoConfig.TYPE_SOUND);
							}
						}
					} else {
						Toast.makeText(FormClient.this, R.string.message_succeed, Toast.LENGTH_SHORT).show();
						FileUtil.deleteFile(audioPath);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					FileUtil.deleteFile(audioPath);
					e.printStackTrace();
				} catch (IllegalStateException e1) {
					Toast.makeText(FormClient.this, R.string.message_succeed, Toast.LENGTH_SHORT).show();
					FileUtil.deleteFile(audioPath);
					e1.printStackTrace();
				}
			}
		});
		btvoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isVoice) {
					isVoice = true;
					btvoice.setImageResource(R.drawable.icon_keyboard);
					recordButton.setVisibility(View.VISIBLE);
				} else {
					isVoice = false;
					btvoice.setImageResource(R.drawable.icon_voice);
					recordButton.setVisibility(View.GONE);
				}
			}
		});

		btsend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = msgText.getText().toString();
				if (msg.length() > 0 && !isSimle) {
					Msg args = new Msg(SelfInfo.From.getKey(), SelfInfo.To.getKey(), msg, TimeRender.getCurrentTime(),
							InfoConfig.MESSAGE_SEND, InfoConfig.TYPE_TEXT);
					if (msg.length() > 0) {
						try {
							if (SelfInfo.baseMain.instant.sendMsg(args) == 1) {
								android.os.Message message = handler.obtainMessage();
								message.what = InfoConfig.MSG_TEXT_RECEIVED_SUCCESS;
								message.obj = args;
								message.sendToTarget();
								for (Contact contact : SelfInfo._contact) {
									if (SelfInfo.To.getKey().equals(contact.getContactKey())) {
										contact.setDate(TimeRender.getDate(args.getDate()));
										contact.setLastMessage(args.getMsg());
										contact.setDataType(InfoConfig.TYPE_TEXT);
									}
								}
							} else {
								Toast.makeText(FormClient.this, R.string.message_succeed, Toast.LENGTH_SHORT).show();
							}
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							Toast.makeText(FormClient.this, R.string.message_succeed, Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						} catch (IllegalStateException e1) {
							Toast.makeText(FormClient.this, R.string.message_succeed, Toast.LENGTH_SHORT).show();
							e1.printStackTrace();
						}
					}
					msgText.setText("");
				} else {
					if (isSimle) {
						isSimle = false;
						rl_vp.setVisibility(View.GONE);
						btsend.setImageResource(R.drawable.icon_smile);
					} else {
						isSimle = true;
						rl_vp.setVisibility(View.VISIBLE);
						btsend.setImageResource(R.drawable.icon_send_w);
						AppUtils.shutInput(FormClient.this, v);
					}

				}

			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(msgReceiver);
		SelfInfo.isChat = false;
		soundPool.release();
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formclient);
		Intent intent = getIntent();
		int position = intent.getIntExtra("position", InfoConfig.ERROR_POSITION);
		if (position == InfoConfig.ERROR_POSITION) {
			this.finish();
		}

		soundPool = new SoundPool(15, AudioManager.STREAM_NOTIFICATION, 5);
		_map = new HashMap<String, Integer>();

		SelfInfo.To = new Account(SelfInfo._list.get(position).getKey(), SelfInfo._list.get(position).getNick(),
				SelfInfo._list.get(position).getOnline());
		SelfInfo.isChat = true;
		// 初始化前几条数据
		try {
			listMsg = SelfInfo.baseMain.instant.getMsg(SelfInfo.To.getKey(), 0, page * InfoConfig.pageSize);
			if (listMsg == null) {
				listMsg = new ArrayList<Msg>();
			}
			Collections.reverse(listMsg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 初始化界面
		ActionBar bar = getActionBar();
		bar.setTitle(SelfInfo.To.getNick());
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);
		initView();
		// 注册广播
		msgReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.message.transferto.servicewith");
		registerReceiver(msgReceiver, intentFilter);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case InfoConfig.MSG_TEXT_RECEIVED_SUCCESS: // 文本消息接收成功
				Msg args = (Msg) msg.obj;
				listMsg.add(args);
				adapter.notifyDataSetChanged();
				break;
			case InfoConfig.MSG_PROGRESSBAR_VISIBLE: // 设置进度条可见
				if (pb.getVisibility() == View.GONE) {
					pb.setMax(100);
					pb.setProgress(0);
					pb.setVisibility(View.VISIBLE);
				}
				break;
			case InfoConfig.MSG_PROGRESSBAR_UPDATING: // 更新进度条
				pb.setProgress(msg.arg1);
				break;
			case InfoConfig.MSG_PROGRESSBAR_GONE: // 进度条置为隐藏
				pb.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		};
	};

	class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Msg msg = intent.getParcelableExtra("newMessage");
			if (SelfInfo.To.getKey().equals(msg.getKeyTo())) {
				android.os.Message message = handler.obtainMessage();
				message.what = InfoConfig.MSG_TEXT_RECEIVED_SUCCESS;
				message.obj = msg;
				message.sendToTarget();
			}
		}

	}

	class MyAdapter extends BaseAdapter {

		private Context cxt;
		private LayoutInflater inflater;

		public MyAdapter(FormClient formClient) {
			this.cxt = formClient;
		}

		@Override
		public int getCount() {
			return listMsg.size();
		}

		@Override
		public Object getItem(int position) {
			return listMsg.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.inflater = (LayoutInflater) this.cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final Msg messageg = listMsg.get(position);
			switch (messageg.getDataType()) {
			case InfoConfig.TYPE_TEXT:
				if (messageg.getDirection() == InfoConfig.MESSAGE_RECEIVE) {
					convertView = this.inflater.inflate(R.layout.formclient_chat_in, null);
				} else {
					convertView = this.inflater.inflate(R.layout.formclient_chat_out, null);
				}
				TextView dateView = (TextView) convertView.findViewById(R.id.formclient_row_date);
				TextView msgView = (TextView) convertView.findViewById(R.id.formclient_row_msg);
				dateView.setText(TimeRender.getDate(messageg.getDate()));
				msgView.setText(RegexUtil.getEmotionContent(FormClient.this, messageg.getMsg()));
				break;
			case InfoConfig.TYPE_SOUND:
				if (messageg.getDirection() == InfoConfig.MESSAGE_RECEIVE) {
					convertView = this.inflater.inflate(R.layout.sound_chatin, null);
				} else {
					convertView = this.inflater.inflate(R.layout.sound_chatout, null);
				}
				convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(messageg.getDataType() == InfoConfig.TYPE_SOUND) {
							if(_map.get(messageg.getMsg()) == null) {
								Toast.makeText(cxt, R.string.soundfile_error, Toast.LENGTH_SHORT).show();
							} else {
								soundPool.play(_map.get(messageg.getMsg()), 1, 1, 0, 0, 1);
							}
						}
					}
				});
				TextView tv_soundtime = (TextView) convertView.findViewById(R.id.soundclient_row_date);
				tv_soundtime.setText(TimeRender.getDate(messageg.getDate()));
				int soundID = soundPool.load(messageg.getMsg(), 1);
				_map.put(messageg.getMsg(), soundID);
				break;
			default:
				break;
			}
			return convertView;
		}
	}

}