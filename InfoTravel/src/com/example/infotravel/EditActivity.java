package com.example.infotravel;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infotravel.Travel;

public class EditActivity extends Activity {

	public static final int REQUEST_CODE_ATTACH_IMAGE = 10;
	private EditText city;
	private EditText country;
	private EditText year;
	private EditText note;
	private Travel travel ;
	private int position;
	private ImageView imgViaje;
	private Uri uri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		city=(EditText) findViewById(R.id.city);
		country=(EditText) findViewById(R.id.pais);
		year=(EditText) findViewById(R.id.year);
		note=(EditText) findViewById(R.id.nota);
		imgViaje=(ImageView) findViewById(R.id.imgViaje);
		
		Intent i =this.getIntent();
		travel=(Travel)i.getParcelableExtra("travel");
		position=i.getIntExtra("position", -1);
		
		
		//Action Bar
		ActionBar actionbar=getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		ActionBar actionbar=getActionBar();
		if (travel!=null)
		{
			if(travel.getCity()!=null)
				city.setText(travel.getCity().toString());
			if(travel.getCountry()!=null)
				country.setText(travel.getCountry().toString());
			year.setText(Integer.toString(travel.getYear()));
			if(travel.getNote()!=null)
				note.setText(travel.getNote().toString());
			if(travel.getImage()!=null)
			{
				uri=Uri.parse(travel.getImage());
				imgViaje.setImageURI(uri);
				
			}
			actionbar.setTitle(R.string.title_editar_viaje);
		}else
			actionbar.setTitle(R.string.title_nuevo_viaje);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		
		int id=item.getItemId();
		
		switch(item.getItemId()){
		
		case R.id.share:
			Intent intent= new Intent(Intent.ACTION_SEND);
			String texto="Datos viaje Ciudad: " + travel.getCity() 
						+"Pais: " + travel.getCountry() +" Año: " + Integer.toString(travel.getYear());
			intent.putExtra(Intent.EXTRA_TEXT, texto);
			intent.setType("text/plain");
			PackageManager pm = getPackageManager();
			if(pm.resolveActivity(intent, 0)!=null)
			{
				startActivity(intent);
			}else Log.d("InfoTravel","No hay ningún activity capaz de resolver el Intent");
			
			break;
			
		case R.id.menu_save:
			Guardar(null);
			
			break;
		case R.id.menu_save_image:
			GuardarImagen(null);
			break;
			
		/*case R.string.title_nuevo_viaje|R.string.title_actionbar_list:
			setResult(RESULT_CANCELED, null);
	        finish();
			break;*/
			
		
			
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		
		int action =me.getAction();
		
		if (action==MotionEvent.ACTION_DOWN)
		{
			//this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			//city.clearFocus();
			//country.clearFocus();
			//note.clearFocus();
			//year.clearFocus();
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(city.getWindowToken(), 0);
			city.clearFocus();
		}
		
	
		
		return true;
	}
	
	public void Guardar(View view) {
		
		//int auxYear=Integer.parseInt(year.getText().toString());
		Intent i = new Intent(this, TravelActivity.class);
		Integer years=null;
		try
		{
			years=Integer.parseInt(year.getText().toString());
		}catch(Exception ex)
		{
			Toast.makeText(year.getContext(), ex.toString(), Toast.LENGTH_LONG).show();
		}
		
		if ((city.getText().toString()!=null) && (!city.getText().toString().equals("")))
		{
			if (travel==null)
			{
			
				travel =new Travel(-1,city.getText().toString(),country.getText().toString(),years, note.getText().toString());
				if (uri!=null)
				{
					travel.setImage(uri.toString());
				}
			
			}else
			{
				//i.putExtra("antTravel", antTravel);
				travel.setCity(city.getText().toString());
				travel.setCountry(country.getText().toString());
				travel.setNote(note.getText().toString());
				travel.setYear(years);
			}
			i.putExtra("travel", travel);
	        i.putExtra("position",position);
	        //startActivity(i);
	        setResult(RESULT_OK, i);
	        finish();
			
		}else
		{
			Toast.makeText(this.city.getContext(),"Al menos debe introducir el nombre de la ciudad", Toast.LENGTH_LONG).show();
		}
        
	}
	
	public void GuardarImagen(View view)
	{
		Intent intent= new Intent (Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		Intent chooserIntent=Intent.createChooser(intent,null);
		
		startActivityForResult(chooserIntent,REQUEST_CODE_ATTACH_IMAGE);
		
	}
	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode==RESULT_OK)
		{
			uri=null;
			
			switch(requestCode){
				
			case REQUEST_CODE_ATTACH_IMAGE:
				
				uri=data.getData();
				if (travel!=null){
					travel.setImage(uri.toString());
				}else
				{
					imgViaje.setImageURI(uri);
				}
				break;
			default:
				throw new IllegalStateException("Invalid request code");
			}
			
			
		}
		
		
	}
}
