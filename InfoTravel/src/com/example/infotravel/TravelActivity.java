package com.example.infotravel;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.infotravel.Travel;
import com.example.infotravel.data.TravelsDatabaseHelper;
import com.example.infotravel.data.TravelsProvider;


public class TravelActivity extends  ListActivity implements
LoaderManager.LoaderCallbacks<Cursor> {

	
	TravelCursorAdapter mAdapter;
	
	static final int NUEVO_VIAJE = 1;
	static final int MODIFICAR_VIAJE=2;
	
	private static final String[] PROJECTION = {TravelsProvider.Travels._ID, TravelsProvider.Travels.CITY,
		TravelsProvider.Travels.COUNTRY,TravelsProvider.Travels.YEAR,TravelsProvider.Travels.NOTE,TravelsProvider.Travels.IMAGE_VIAJE 
		};
	private static TravelsDatabaseHelper dbHelper;
	private static TravelsProvider travelsProvider=new TravelsProvider();
	
	
	
	private class TravelAdapter extends ArrayAdapter<Travel>
	{
		private Context context;
		private ArrayList<Travel> travels;
		private static final int RESOURCE= android.R.layout.simple_list_item_2;
	
		
		
		
		public TravelAdapter(Context context, ArrayList<Travel> travels){
			super(context,RESOURCE,travels);
			
			this.context=context;
			this.travels=travels;
		}
		
		@Override
		public View getView(int position,View converView, ViewGroup parent)
		{
			LinearLayout view;
			ViewHolder holder;
			if(converView==null)
			{
				view= new LinearLayout(context);
				
				LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				inflater.inflate(RESOURCE, view,true);
				
				holder= new ViewHolder();
				holder.text1=(TextView) view.findViewById(android.R.id.text1);
				holder.text2=(TextView) view.findViewById(android.R.id.text2);
				
				view.setTag(holder);
			}else{
				
				view=(LinearLayout) converView;
				holder=(ViewHolder) view.getTag();
				
			}
			
			
			Travel info= travels.get(position);
			Integer year=info.getYear();
			holder.text1.setText(info.getCity() + " (" + info.getCountry() + ")");
			holder.text2.setText( year==null?"":Integer.toString(year));
			
			return view;
		}
		
	}
	
	static class ViewHolder {
		TextView text1;
		TextView text2;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		fillData();
		registerForContextMenu(getListView());
		
		ActionBar actionbar=getActionBar();
		actionbar.setTitle(R.string.title_actionbar_list);
		
		
		
	}
	
	 private void fillData() {

			getLoaderManager().initLoader(0, null, this);
			mAdapter= new TravelCursorAdapter(this,null);
			setListAdapter(mAdapter);
		    
		    
}
	 @Override
	 public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	    //String[] projection = { TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY };
		 
		 
	    CursorLoader cursorLoader = new CursorLoader(this,
	    		TravelsProvider.CONTENT_URI, PROJECTION, "", null,  TravelsProvider.Travels.COUNTRY);
	    return cursorLoader;
	  }

	  @Override
	  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
	    mAdapter.swapCursor(data);
	  }

	  @Override
	  public void onLoaderReset(Loader<Cursor> loader) {
	    // data is not available anymore, delete reference
	    mAdapter.swapCursor(null);
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel_menu, menu);
		
		return true;
	}
	
	
	//Menu al mantener pulsado
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v,

			ContextMenuInfo menuInfo) {

			  super.onCreateContextMenu(menu, v, menuInfo);
			  getMenuInflater().inflate(R.menu.travel_context,menu);
			  
			  menu.setHeaderTitle("Menu");
			}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		
		AdapterContextMenuInfo info =(AdapterContextMenuInfo) item.getMenuInfo();
	
		Travel travel;
		
		switch(item.getItemId()){
		
			case R.id.menu_new_travel:
				Intent intent= new Intent(this,EditActivity.class);
				startActivityForResult(intent,NUEVO_VIAJE);
				break;
			
			case R.id.menu_edit_travel:
				
				Cursor c=(Cursor) mAdapter.getItem(info.position);
				travel=getTravelFromCursor(c);
				Intent intentedit=new Intent(this,EditActivity.class);

				if (travel!=null)
				{
					intentedit.putExtra("travel",travel);
					intentedit.putExtra("position", info.position);
					startActivityForResult(intentedit,MODIFICAR_VIAJE);
				}
				break;
			case R.id.menu_delete_travel:
		
				Cursor c2=(Cursor) mAdapter.getItem(info.position);
				travel=getTravelFromCursor(c2);
				Uri uri= Uri.parse(TravelsProvider.CONTENT_URI.toString()+"/"+travel.getIdTravel());
				
				String[] arg=new String[]{travel.getIdTravel().toString()};
				String where= TravelsProvider.Travels._ID+"= ? ";
				getContentResolver().delete(uri, where, arg);
				
				break;
		
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // TODO Auto-generated method stub
		 ContentValues values = new ContentValues();
		 
		if  (resultCode == RESULT_OK){
			Travel travel =(Travel)data.getParcelableExtra("travel");	
			if(travel!=null){
				if(requestCode==NUEVO_VIAJE)
				{
					Uri uri= Uri.parse(TravelsProvider.CONTENT_URI.toString());
					values=this.getContentValues(travel);
					getContentResolver().insert(uri, values);
					
				}
				}if(requestCode==MODIFICAR_VIAJE)
				{
					Travel antTravel ;
					int position=data.getIntExtra("position",-1);
					if(position!=-1){
						
						Cursor c=(Cursor) mAdapter.getItem(position);
						antTravel=getTravelFromCursor(c);
						if(antTravel!=null){
							
							Uri uri= Uri.parse(TravelsProvider.CONTENT_URI.toString()+"/"+travel.getIdTravel());
							values=this.getContentValues(travel);
							String[] arg=new String[]{travel.getIdTravel().toString()};
							String where= TravelsProvider.Travels._ID+"= ? ";
							getContentResolver().update(uri, values, where,arg);
							
						}
					}
					
				}
				
				
		}
		
		getLoaderManager().restartLoader(0, null, this);
		//fillData();
	    
	}
	
	
	@Override
	protected void onListItemClick(ListView l,View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		
		Intent intent=new Intent(this,EditActivity.class);
		Cursor c=(Cursor) mAdapter.getItem(position);
		Travel travel=getTravelFromCursor(c);
		if (travel!=null)
		{
			intent.putExtra("travel",travel);
			intent.putExtra("position", position);
			startActivityForResult(intent,MODIFICAR_VIAJE);
		}
		
	}
	
	protected ContentValues getContentValues(Travel travel){
		ContentValues values = new ContentValues();
		
		values.put(TravelsProvider.Travels._ID, travel.getIdTravel());
		values.put(TravelsProvider.Travels.COUNTRY, travel.getCountry());
		values.put(TravelsProvider.Travels.CITY,travel.getCity());
		values.put(TravelsProvider.Travels.YEAR,travel.getYear());
		values.put(TravelsProvider.Travels.NOTE, travel.getNote());
		values.put(TravelsProvider.Travels.IMAGE_VIAJE,	 travel.getImage());
		
		return values;
	}
	
	protected Travel getTravelFromCursor(Cursor c){
		
		Travel travel= new Travel();
		travel.setIdTravel(c.getInt(c.getColumnIndex(TravelsProvider.Travels._ID)));
		travel.setCity(c.getString(c.getColumnIndex(TravelsProvider.Travels.CITY)));
		travel.setCountry(c.getString(c.getColumnIndex(TravelsProvider.Travels.COUNTRY)));
		travel.setYear(c.getInt(c.getColumnIndex(TravelsProvider.Travels.YEAR)));
		travel.setNote(c.getString(c.getColumnIndex(TravelsProvider.Travels.NOTE)));
		travel.setImage(c.getString(c.getColumnIndex(TravelsProvider.Travels.IMAGE_VIAJE)));
		
		return travel;
	}
	

	


	
 
}
