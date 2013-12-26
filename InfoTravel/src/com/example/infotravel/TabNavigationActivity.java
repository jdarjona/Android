package com.example.infotravel;


import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.Activity;
import android.app.ActionBar.Tab;


public class TabNavigationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_navigation);
		
		ActionBar actionbar=getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab=actionbar.newTab();
		tab.setText(R.string.tab_lista_viajes);
		tab.setIcon(android.R.drawable.ic_menu_directions);
		
		Tab tab2=actionbar.newTab();
		tab2.setText(R.string.tab_about);
		tab2.setIcon(android.R.drawable.ic_dialog_info);
		
		Fragment tab1frag = new ListTravelFragement();
	    Fragment tab2frag = new AboutActivity();
	    
	    
	    
	    tab.setTabListener(new TabsListener(tab1frag));
        tab2.setTabListener(new TabsListener(tab2frag));
        actionbar.addTab(tab);
        actionbar.addTab(tab2);
		
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel_menu, menu);
		
		return true;
	}
	*/
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_navigation, menu);
		return true;
	}*/

	/*
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
		
		return super.onMenuItemSelected(featureId, item);
	}
	*/


}
