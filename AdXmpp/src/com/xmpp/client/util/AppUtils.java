package com.xmpp.client.util;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class AppUtils {
	// 关闭软键盘
	public static void shutInput(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
	public static boolean isConnect(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static int getIndex(String str, char key) {
		int i = 0;
		for (i = str.length(); i >= 0; i--) {
			if (key == str.charAt(i - 1)) {
				break;
			}
		}
		return i;
	}
	
	
	public static boolean hasSDCard() {
		boolean b = false;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			b = true;
		}
		return b;
	}

	/**
	 * 得到sdcard路径
	 * 
	 * @return
	 */
	public static String getExtPath() {
		String path = "";
		if (hasSDCard()) {
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}
	
	public final   static   float [] BT_SELECTED =  new   float [] {         
			0.308f,  0.609f,  0.082f,  0 ,  0 ,       
			0.308f,  0.609f,  0.082f,  0 ,  0 ,  
			0.308f,  0.609f,  0.082f,  0 ,  0 ,  
			0 ,  0 ,  0 ,  1 ,  0   
	};  
	
	public static Drawable clearDrawble(Context context, int _id) {
		Drawable drawable = context.getResources().getDrawable(_id);
		drawable.mutate();
		drawable.clearColorFilter();
		drawable.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
		return drawable;
	}
}
