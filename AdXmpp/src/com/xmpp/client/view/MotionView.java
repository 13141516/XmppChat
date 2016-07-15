package com.xmpp.client.view;

import java.util.ArrayList;
import java.util.List;


import com.xmpp.client.R;
import com.xmpp.client.adapter.GridAdapter;
import com.xmpp.client.config.InfoConfig;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MotionView implements OnPageChangeListener, OnItemClickListener{
	private Context context;
	private ViewPager viewPager;
	private LinearLayout ll_port;
	private int pageColumn = 6;
	private int currentPage = 0;
	private MyPagerAdapter paAdapter;
	private List<GridView> _listGrid;
	private List<ImageView> _imageView;
	private EditText et;

	public MotionView(Context mContext, ViewPager mViewPager, LinearLayout mll_port, EditText mEt) {
		context = mContext;
		viewPager = mViewPager;
		ll_port = mll_port;
		et = mEt;
		initListGrid();
		paAdapter = new MyPagerAdapter();
		viewPager.setAdapter(paAdapter);
		initPot();
		viewPager.setOnPageChangeListener(MotionView.this);
	}

	private void initPot() {
		_imageView = new ArrayList<ImageView>();
		for (int i = 0; i < InfoConfig.emotion_list.size(); i++) {
			ImageView image = new ImageView(context);
			if (currentPage == i) {
				image.setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				image.setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			_imageView.add(image);
			ll_port.addView(image);
		}
	}

	private void initListGrid() {
		_listGrid = new ArrayList<GridView>();
		Log.i("test size ", InfoConfig.emotion_list.size() + "");
		for (List<Integer> _list : InfoConfig.emotion_list) {
			GridView gv = new GridView(context);
			gv.setNumColumns(pageColumn);
			gv.setBackgroundColor(Color.TRANSPARENT);
			gv.setHorizontalSpacing(1);
			gv.setVerticalSpacing(1);
			gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			gv.setCacheColorHint(0);
			gv.setPadding(5, 0, 5, 0);
			gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
			gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			gv.setGravity(Gravity.CENTER);
			gv.setAdapter(new GridAdapter(_list, context));
			_listGrid.add(gv);
			gv.setOnItemClickListener(this);
		}
	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return InfoConfig.emotion_list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(_listGrid.get(position));
			return _listGrid.get(position);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(_listGrid.get(position));
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		currentPage = arg0;
		for (ImageView image : _imageView) {
			image.setBackgroundResource(R.drawable.page_indicator_unfocused);
		}
		_imageView.get(currentPage).setBackgroundResource(R.drawable.page_indicator_focused);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		int realP = InfoConfig.emotion_list.get(currentPage).get(position);
		int reall = currentPage * 30 + position;
		SpannableString spannableString = new SpannableString(InfoConfig.textEmotions[reall]);
		ImageSpan span = new ImageSpan(context, realP);
		spannableString.setSpan(span, 0, InfoConfig.textEmotions[reall].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		et.append(spannableString);
	}

}
