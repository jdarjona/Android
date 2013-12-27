package com.example.infotravel.data;

import java.util.ArrayList;

import com.example.infotravel.Travel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TravelsDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG="TravelsDatabaseHelper";
	
	private static final int DATABASE_VERSION=4;
	
	private static final String DATABASE_NAME="travels.db";
	
	public static final String TABLE_NAME=TravelsConstants.TRAVELS_TABLE_NAME;
	
	public TravelsDatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
				TravelsConstants._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TravelsConstants.CITY +" TEXT NOT NULL, " +
				TravelsConstants.COUNTRY +" TEXT NOT NULL, " +
				TravelsConstants.YEAR + " INTEGER NOT NULL ," +
				TravelsConstants.NOTE + " TEXT, " +
				TravelsConstants.IMAGE_VIAJE + " TEXT);" );
		
		initialData(db);
	}

	private void initialData(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		insertTravel(db, "Londres", "UK", 2012, "¡Juegos Olimpicos!",null);
		insertTravel(db, "Paris", "Francia", 2007, null,null);
		insertTravel(db, "Gotham City", "EEUU", 2011, "¡¡Batman!!",null);
		insertTravel(db, "Hamburgo", "Alemania", 2009, null,null);
		insertTravel(db, "Pekin", "China", 2011, null,null);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onUpgrade");
		if(oldVersion<newVersion)
		{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME +";");
			onCreate(db);
		}
	}

public void updateTravel(Travel travel)
{
	SQLiteDatabase db= getReadableDatabase();
	
	ContentValues values= new ContentValues();
	values.put(TravelsConstants.CITY, travel.getCity());
	values.put(TravelsConstants.COUNTRY, travel.getCountry());
	values.put(TravelsConstants.YEAR, travel.getYear());
	values.put(TravelsConstants.NOTE,travel.getNote());
	values.put(TravelsConstants.IMAGE_VIAJE, travel.getImage());
	
	db.update(TABLE_NAME, values, TravelsConstants._ID+"="+travel.getIdTravel(), null);
}
public void insertTravel(Travel travel)
{
	SQLiteDatabase db= getReadableDatabase();
	
	ContentValues values= new ContentValues();
	values.put(TravelsConstants.CITY, travel.getCity());
	values.put(TravelsConstants.COUNTRY, travel.getCountry());
	values.put(TravelsConstants.YEAR, travel.getYear());
	values.put(TravelsConstants.NOTE,travel.getNote());
	values.put(TravelsConstants.IMAGE_VIAJE, travel.getImage());
	
	db.insert(TABLE_NAME, null, values);
	
}
public void insertTravel(SQLiteDatabase db, String city, String country, Integer year, String note, String imageViaje)
{
	ContentValues values= new ContentValues();
	values.put(TravelsConstants.CITY, city);
	values.put(TravelsConstants.COUNTRY, country);
	values.put(TravelsConstants.YEAR, year);
	values.put(TravelsConstants.NOTE,note);
	values.put(TravelsConstants.IMAGE_VIAJE, imageViaje);
	
	db.insert(TABLE_NAME, null, values);

}

public void deleteTravelById(Integer idTravel)
{
	SQLiteDatabase db= getReadableDatabase();
	
	
	db.delete(TABLE_NAME, TravelsConstants._ID+"="+ idTravel, null);
}


public ArrayList<Travel> getTravelList(){
	
	ArrayList<Travel> travels= new ArrayList<Travel>();
	
	SQLiteDatabase db= getReadableDatabase();
	
	Cursor c= db.query(TravelsConstants.TRAVELS_TABLE_NAME,	  null, null, null, null, null, null);
	
	if(c.moveToFirst()){
		
		int idTravelIndex=c.getColumnIndex(TravelsConstants._ID);
		int cityIndex=c.getColumnIndex(TravelsConstants.CITY);
		int countryIndex=c.getColumnIndex(TravelsConstants.COUNTRY);
		int yearIndex=c.getColumnIndex(TravelsConstants.YEAR);
		int noteIndex=c.getColumnIndex(TravelsConstants.NOTE);
		int imageViajeIndex=c.getColumnIndex(TravelsConstants.IMAGE_VIAJE);
		
		do{
			Integer idTravel=c.getInt(idTravelIndex);
			String city=c.getString(cityIndex);
			String country=c.getString(countryIndex);
			Integer year=c.getInt(yearIndex);
			String note=c.getString(noteIndex);
			String imageViaje=c.getString(imageViajeIndex);
			
			Travel travel = new Travel(idTravel,city,country,year,note);
			
			if (imageViaje!=null){
				travel.setImage(imageViaje);
			}
			
			travels.add(travel);
			
		}while(c.moveToNext());
		
		c.close();
	}
	return travels;
	
}

}
