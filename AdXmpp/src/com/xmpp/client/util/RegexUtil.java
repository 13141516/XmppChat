package com.xmpp.client.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xmpp.client.config.InfoConfig;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

public class RegexUtil {
	public static SpannableString getEmotionContent(final Context context, String source) {
		SpannableString spannableString = new SpannableString(source);
		Integer keyNumber, page, pageLocation, imgRes;
		String regexEmotion = "\\[([\\w])+\\]";
		Pattern patternEmotion = Pattern.compile(regexEmotion);
		Matcher matcherEmotion = patternEmotion.matcher(spannableString);

		while (matcherEmotion.find()) {
			// ��ȡƥ�䵽�ľ����ַ�
			String key = matcherEmotion.group();
			// ƥ���ַ����Ŀ�ʼλ��
			int start = matcherEmotion.start();
			// ���ñ������ֻ�ȡ����Ӧ��ͼƬ
			for (keyNumber = 0; keyNumber < InfoConfig.textEmotions.length; keyNumber++) {
				if (InfoConfig.textEmotions[keyNumber].equals(key)) {
					break;
				}
			}
			if(keyNumber > InfoConfig.textEmotions.length) {
				break;
			} else {
				page = keyNumber / 30;
				pageLocation = keyNumber % 30;
				imgRes = InfoConfig.emotion_list.get(page).get(pageLocation);
				if (imgRes != null) {
					ImageSpan span = new ImageSpan(context, imgRes);
					spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		}
		return spannableString;
	}
}
