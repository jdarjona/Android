package com.example.infotravel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.infotravel.Travel;

public class EditActivity extends Activity {

	EditText city;
	EditText country;
	EditText year;
	EditText note;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		city=(EditText) findViewById(R.id.city);
		country=(EditText) findViewById(R.id.pais);
		year=(EditText) findViewById(R.id.year);
		note=(EditText) findViewById(R.id.nota);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	
	public void Guardar(View view) {
		
		//int auxYear=Integer.parseInt(year.getText().toString());
		
		Travel travel =new Travel(city.getText().toString(),country.getText().toString(), 2013 , note.getText().toString());
		
		//Travel travel= new Travel() ;
		
        Intent i = new Intent(this, TravelActivity.class);
      
        
        i.putExtra("travel", travel);
        //startActivity(i);
        setResult(RESULT_OK, i);
        finish();
  }
}
