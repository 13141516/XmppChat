package com.xmpp.client.config;

import java.util.ArrayList;
import java.util.List;

import com.xmpp.client.R;
import com.xmpp.client.util.AppUtils;

public class InfoConfig {
	public static final String XMPP_RESOURCES = android.os.Build.MODEL;
	public static final String SERVICE_NAME = "instantmessage";

	public static final int ERROR_POSITION = -1;

	public static final int MSG_TEXT_RECEIVED_SUCCESS = 1;
	public static final int MSG_PROGRESSBAR_VISIBLE = 2;
	public static final int MSG_PROGRESSBAR_UPDATING = 3;
	public static final int MSG_PROGRESSBAR_GONE = 4;
	public static final int MSG_FILE_RECEIVED_SUCCESS = 5;

	public static final int MESSAGE_SEND = 0;
	public static final int MESSAGE_RECEIVE = 1;

	public static final boolean ON_CHAT = true;
	public static final boolean NOT_CHAT = false;

	public static final int pageSize = 5;

	public static final String APP_SET = "app_user_set";

	public static final String KEY_STORE = "AndroidKeyStore";
	public static final String PAIR_KEY_ALIAS = "comxmppclient";

	public static final String PATH = AppUtils.getExtPath() + "/xmpp";
	public static final String SAVE_SOUND_PATH = PATH + "/sounds";// 设置声音文件的路径

	public static final int TYPE_TEXT = 0;
	public static final int TYPE_SOUND = 1;

	public static final List<List<Integer>> emotion_list = new ArrayList<List<Integer>>() {
		private static final long serialVersionUID = 550898639495564949L;

		{
			add(new ArrayList<Integer>() {
				private static final long serialVersionUID = 5236747614694838999L;

				{
					add(R.drawable.f000);
					add(R.drawable.f001);
					add(R.drawable.f002);
					add(R.drawable.f003);
					add(R.drawable.f004);
					add(R.drawable.f005);
					add(R.drawable.f006);
					add(R.drawable.f007);
					add(R.drawable.f008);
					add(R.drawable.f009);
					add(R.drawable.f010);
					add(R.drawable.f011);
					add(R.drawable.f012);
					add(R.drawable.f013);
					add(R.drawable.f014);
					add(R.drawable.f015);
					add(R.drawable.f016);
					add(R.drawable.f017);
					add(R.drawable.f018);
					add(R.drawable.f019);
					add(R.drawable.f020);
					add(R.drawable.f021);
					add(R.drawable.f022);
					add(R.drawable.f023);
					add(R.drawable.f024);
					add(R.drawable.f025);
					add(R.drawable.f026);
					add(R.drawable.f027);
					add(R.drawable.f028);
					add(R.drawable.f029);
				}
			});

			add(new ArrayList<Integer>() {
				private static final long serialVersionUID = -8658479003875421463L;

				{
					add(R.drawable.f030);
					add(R.drawable.f031);
					add(R.drawable.f032);
					add(R.drawable.f033);
					add(R.drawable.f034);
					add(R.drawable.f035);
					add(R.drawable.f036);
					add(R.drawable.f037);
					add(R.drawable.f038);
					add(R.drawable.f039);
					add(R.drawable.f040);
					add(R.drawable.f041);
					add(R.drawable.f042);
					add(R.drawable.f043);
					add(R.drawable.f044);
					add(R.drawable.f045);
					add(R.drawable.f046);
					add(R.drawable.f047);
					add(R.drawable.f048);
					add(R.drawable.f049);
					add(R.drawable.f050);
					add(R.drawable.f051);
					add(R.drawable.f052);
					add(R.drawable.f053);
					add(R.drawable.f054);
					add(R.drawable.f055);
					add(R.drawable.f056);
					add(R.drawable.f057);
					add(R.drawable.f058);
					add(R.drawable.f059);
				}
			});

			add(new ArrayList<Integer>() {
				private static final long serialVersionUID = -2519532440610660817L;

				{
					add(R.drawable.f060);
					add(R.drawable.f061);
					add(R.drawable.f062);
					add(R.drawable.f063);
					add(R.drawable.f064);
					add(R.drawable.f065);
					add(R.drawable.f066);
					add(R.drawable.f067);
					add(R.drawable.f068);
					add(R.drawable.f069);
					add(R.drawable.f070);
					add(R.drawable.f071);
				}
			});
		}
	};
	public static final String[] textEmotions = { "[smile]", "[laugh]", "[shrill]", "[thirsty]", "[embrassed]",
			"[shine]", "[faint]", "[shy]", "[trikey]", "[calm]", "[love]", "[cool]", "[despise]", "[curisoty]",
			"[hehe]", "[suspect]", "[han]", "[unhealty]", "[fight]", "[uh]", "[tease]", "[reflect]", "[touge]", "[eye]",
			"[bigtouge]", "[unhappay]", "[yamaiday]", "[fuck]", "[fire]", "[loser]", "[angry]", "[daiby]", "[ou]",
			"[ah]", "[blue]", "[cry]", "[cold]", "[bigcry]", "[happycry]", "[shock]", "[smallshock]", "[GG]",
			"[bigshock]", "[dizzy]", "[bigshy]", "[sleep]", "[shutup]", "[air]", "[lovecat]", "[shockcat]", "[what]",
			"[shit]", "[pig]", "[tiger]", "[dog]", "[panda]", "[purpleheart]", "[blueheart]", "[yellowheart]", "[kiss]",
			"[bikini]", "[congratulation]", "[bless]", "[okay]", "[good]", "[garbage]", "[flower]", "[sumflower]",
			"[rain]", "[zzz]", "[basketball]", "[beer]" };
}
