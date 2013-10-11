package com.example.infotravel;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.infotravel.Travel;


public class TravelActivity extends  ListActivity {

	
	ArrayList<Travel> values = new ArrayList<Travel>();
	TravelAdapter adapter;
	static final int NUEVO_VIAJE = 1;
	static final int MODIFICAR_VIAJE=2;
	
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
			int year=info.getYear();
			holder.text1.setText(info.getCity() + " (" + info.getCountry() + ")");
			holder.text2.setText( Integer.toString(year));
			
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
		//setContentView(R.layout);
		
		 values = getData();
		 
		 adapter = new TravelAdapter(this,
			        values);
		 //final ListView listview = (ListView) findViewById(R.id.listView1);
		 
		 adapter.notifyDataSetChanged();
		 setListAdapter(adapter);

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		
		switch(item.getItemId()){
		
		case R.id.menu_new_travel:
			Intent intent= new Intent(this,EditActivity.class);
			startActivityForResult(intent,NUEVO_VIAJE);
			break;
		
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // TODO Auto-generated method stub
	    
		if  (resultCode == RESULT_OK){
			Travel travel =(Travel)data.getParcelableExtra("travel");	
			if(travel!=null){
				if(requestCode==NUEVO_VIAJE)
				{
					this.values.add(travel);
					
				}
				}if(requestCode==MODIFICAR_VIAJE)
				{
					Travel antTravel ;
					int position=data.getIntExtra("position",-1);
					if(position!=-1){
						antTravel=adapter.getItem(position);
						if(antTravel!=null){
							antTravel.setCity(travel.getCity());
							antTravel.setCountry(travel.getCountry());
							antTravel.setNote(travel.getNote());
							antTravel.setYear(travel.getYear());
						}
					}
					
				}
				adapter.notifyDataSetChanged();
				
		}
	    
	}
	
	
	@Override
	protected void onListItemClick(ListView l,View v, int position, long id)
	{
		//super.onListItemClick(l, v, position, id);
		Intent intent=new Intent(this,EditActivity.class);

		Travel travel=adapter.getItem(position);
		if (travel!=null)
		{
			intent.putExtra("travel",travel);
			intent.putExtra("position", position);
			startActivityForResult(intent,MODIFICAR_VIAJE);
		}
		
	}
	
	 private ArrayList<Travel> getData(){
	    	ArrayList<Travel> travels = new ArrayList<Travel>();

	    	Travel info = new Travel("Londres", "UK", 2012, "¡Juegos Olimpicos!");
	    	Travel info2 = new Travel("Paris", "Francia", 2007);
	    	Travel info3 = new Travel("Gotham City", "EEUU", 2011, "¡¡Batman!!");
	    	Travel info4 = new Travel("Hamburgo", "Alemania", 2009);
	    	Travel info5 = new Travel("Pekin", "China", 2011);

	        travels.add(info);
	        travels.add(info2);
	        travels.add(info3);
	        travels.add(info4);
	        travels.add(info5);
	        
	        return travels;
	    }
 
}
