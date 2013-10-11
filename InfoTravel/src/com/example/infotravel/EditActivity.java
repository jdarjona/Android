package com.example.infotravel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.infotravel.Travel;

public class EditActivity extends Activity {

	EditText city;
	EditText country;
	EditText year;
	EditText note;
	Travel travel ;
	int position;
	//Travel antTravel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		city=(EditText) findViewById(R.id.city);
		country=(EditText) findViewById(R.id.pais);
		year=(EditText) findViewById(R.id.year);
		note=(EditText) findViewById(R.id.nota);
		
		Intent i =this.getIntent();
		//antTravel=(Travel)i.getParcelableExtra("travel");
		travel=(Travel)i.getParcelableExtra("travel");
		position=i.getIntExtra("position", -1);
		
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		
		if (travel!=null)
		{
			if(travel.getCity()!=null)
				city.setText(travel.getCity().toString());
			if(travel.getCountry()!=null)
				country.setText(travel.getCountry().toString());
			year.setText(Integer.toString(travel.getYear()));
			if(travel.getNote()!=null)
				note.setText(travel.getNote().toString());
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
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
		
		if (travel==null)
		{
			travel =new Travel(city.getText().toString(),country.getText().toString(), Integer.parseInt(year.getText().toString()) , note.getText().toString());
		}else
		{
			//i.putExtra("antTravel", antTravel);
			travel.setCity(city.getText().toString());
			travel.setCountry(country.getText().toString());
			travel.setNote(note.getText().toString());
			travel.setYear(Integer.parseInt(year.getText().toString()));
		}
		
        i.putExtra("travel", travel);
        i.putExtra("position",position);
        //startActivity(i);
        setResult(RESULT_OK, i);
        finish();
  }
}
