package com.xmpp.client.adapter;

import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class AdapterBase extends BaseAdapter 
{
	private List<?> mList;
	private Context mContext;
	
	public AdapterBase(List<?> pList,Context pContext)
	{
		mList=pList;
		mContext=pContext;
	}
	
	public void setmContext(Context context) {
		mContext = context;
	}
	
	public Context getmContext() {
		return mContext;
	}
	
	public void setList(List<?> pList)
	{
		mList=pList;
	}
	
	public List<?> getmList(){
		return mList;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
