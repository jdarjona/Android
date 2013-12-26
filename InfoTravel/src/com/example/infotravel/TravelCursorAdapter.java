package com.example.infotravel;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.infotravel.TravelActivity.ViewHolder;
import com.example.infotravel.data.TravelsProvider;

final class TravelCursorAdapter extends ResourceCursorAdapter{
	private LayoutInflater mInflater;
	
	
	public TravelCursorAdapter(Context listTravelFragement, Cursor c){
		super(listTravelFragement,android.R.layout.simple_list_item_2,c,0);
	
		mInflater=LayoutInflater.from(listTravelFragement);
		
	}
	
	@Override
	public View newView(Context context,Cursor cursor, ViewGroup parent){
		View view=mInflater.inflate(android.R.layout.simple_list_item_2, parent,false);
		
		ViewHolder holder=new ViewHolder();
		TextView text1=(TextView) view.findViewById(android.R.id.text1);
		TextView text2=(TextView) view.findViewById(android.R.id.text2);
		holder.text1=text1;
		holder.text2=text2;
		view.setTag(holder);
		return view;
	}
	
	@Override
	public void bindView(View v, Context context, Cursor c){
		
		ViewHolder holder=(ViewHolder)v.getTag();
		String country=c.getString(c.getColumnIndex(TravelsProvider.Travels.COUNTRY));
		String city=c.getString(c.getColumnIndex(TravelsProvider.Travels.CITY));
		holder.text1.setText(country);
		holder.text2.setText(city);
	}

}

