package com.example.infotravel;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.infotravel.Travel;


public class TravelActivity extends Activity {

	
	ArrayList<Travel> values = new ArrayList<Travel>();
	TravelAdapter adapter;
	
	
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
		setContentView(R.layout.activity_travel);
		
		 values = getData();
		 
		 adapter = new TravelAdapter(this,
			        values);
		 final ListView listview = (ListView) findViewById(R.id.listView1);
		 
		 adapter.notifyDataSetChanged();
		 listview.setAdapter(adapter);

	}
	public void lanzar(View view) {
        Intent i = new Intent(this, EditActivity.class);
        //startActivity(i);
        startActivityForResult(i, 1);//REQUEST_CODE);
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
			Intent intent= new Intent(this,com.example.infotravel.EditActivity.class);
			startActivityForResult(intent,1);
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
				this.values.add(travel);
				adapter.notifyDataSetChanged();
			}
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
