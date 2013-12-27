package com.example.infotravel;



import com.example.infotravel.data.TravelsProvider;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;



public class ListTravelFragement extends ListFragment implements
LoaderManager.LoaderCallbacks<Cursor>{

	
	
TravelCursorAdapter mAdapter;
	
	static final int NUEVO_VIAJE = 1;
	static final int MODIFICAR_VIAJE=2;
	
	private static final String[] PROJECTION = {TravelsProvider.Travels._ID, TravelsProvider.Travels.CITY,
		TravelsProvider.Travels.COUNTRY,TravelsProvider.Travels.YEAR,TravelsProvider.Travels.NOTE,TravelsProvider.Travels.IMAGE_VIAJE 
		};
	
	
	static class ViewHolder {
		TextView text1;
		TextView text2;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);

	}
	
	
	
	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		
		fillData();
		registerForContextMenu(getListView());
		
		ActionBar actionbar=this.getActivity().getActionBar();
		actionbar.setTitle(R.string.title_actionbar_list);
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_list_travel_fragement, container, false);
	}
	
	
	
	@Override

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	
	   inflater.inflate(R.menu.travel_menu, menu);
	
	}

	@Override
	
	public boolean onOptionsItemSelected(MenuItem item) {
	   // handle item selection
	
		AdapterContextMenuInfo info =(AdapterContextMenuInfo) item.getMenuInfo();
		
		Travel travel;
		
		switch(item.getItemId()){
		
			case R.id.menu_new_travel:
				Intent intent= new Intent(this.getActivity(),EditActivity.class);
				startActivityForResult(intent,NUEVO_VIAJE);
				break;
			
			case R.id.menu_edit_travel:
				
				Cursor c=(Cursor) mAdapter.getItem(info.position);
				travel=getTravelFromCursor(c);
				Intent intentedit=new Intent(this.getActivity(),EditActivity.class);

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
				this.getActivity().getContentResolver().delete(uri, where, arg);
				
				break;
		
		}
	 
	
		
		
		
		return super.onOptionsItemSelected(item);
	
	
	}

	 @Override
     public boolean onContextItemSelected(MenuItem item) {
		     AdapterContextMenuInfo info =(AdapterContextMenuInfo) item.getMenuInfo();
			
			Travel travel;
			
			switch(item.getItemId()){
			
				case R.id.menu_new_travel:
					Intent intent= new Intent(this.getActivity(),EditActivity.class);
					startActivityForResult(intent,NUEVO_VIAJE);
					break;
				
				case R.id.menu_edit_travel:
					
					Cursor c=(Cursor) mAdapter.getItem(info.position);
					travel=getTravelFromCursor(c);
					Intent intentedit=new Intent(this.getActivity(),EditActivity.class);

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
					this.getActivity().getContentResolver().delete(uri, where, arg);
					getLoaderManager().restartLoader(0, null, this);
					break;
			
			}
		 
	
		
         return super.onContextItemSelected(item);
     }
	
	
	 private void fillData() {
 
			getLoaderManager().initLoader(0, null, this);
			mAdapter= new TravelCursorAdapter(this.getActivity(),null);
			setListAdapter(mAdapter);
		    
		    
}
	 @Override
	 public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	    //String[] projection = { TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY };
		 
		 
	    CursorLoader cursorLoader = new CursorLoader(this.getActivity(),
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

	
	
	//Menu al mantener pulsado
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

			  super.onCreateContextMenu(menu, v, menuInfo);
			  this.getActivity().getMenuInflater().inflate(R.menu.travel_context,menu);
			  
			  menu.setHeaderTitle("Menu");
	}
	
	
	
	
	@Override
	public void onListItemClick(ListView l,View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		
		Intent intent=new Intent(this.getActivity(),EditActivity.class);
		Cursor c=(Cursor) mAdapter.getItem(position);
		Travel travel=getTravelFromCursor(c);
		if (travel!=null)
		{
			intent.putExtra("travel",travel);
			intent.putExtra("position", position);
			startActivityForResult(intent,MODIFICAR_VIAJE);
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // TODO Auto-generated method stub
		 ContentValues values = new ContentValues();
		 
		//this.getActivity();
		if  (resultCode == Activity.RESULT_OK){
			Travel travel =(Travel)data.getParcelableExtra("travel");	
			if(travel!=null){
				if(requestCode==NUEVO_VIAJE)
				{
					Uri uri= Uri.parse(TravelsProvider.CONTENT_URI.toString());
					values=this.getContentValues(travel,true);
					this.getActivity().getContentResolver().insert(uri, values);
					
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
							values=this.getContentValues(travel,false);
							String[] arg=new String[]{travel.getIdTravel().toString()};
							String where= TravelsProvider.Travels._ID+"= ? ";
							this.getActivity().getContentResolver().update(uri, values, where,arg);
							
						}
					}
					
				}
				
				
		}
		
		getLoaderManager().restartLoader(0, null, this);
		//fillData();
	    
	}
	protected ContentValues getContentValues(Travel travel,boolean insert  ){
		ContentValues values = new ContentValues();
		
		if (!insert) 
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
