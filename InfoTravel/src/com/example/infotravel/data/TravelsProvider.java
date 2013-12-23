package com.example.infotravel.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class TravelsProvider extends ContentProvider {

	private static final String TAG="TravelsProvider";
	
	private static final String AUTHORITY="com.example.infotravel";
	
	public static final Uri CONTENT_URI=Uri.parse("content://"+ AUTHORITY + "/travels");
	
	private static final int URI_TRAVELS=1;
	private static final int URI_TRAVEL_ITEM=2;
	
	private static final UriMatcher mUriMatcher;
	static{
		mUriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, "travels", URI_TRAVELS);
		mUriMatcher.addURI(AUTHORITY, "travels/#", URI_TRAVEL_ITEM);
	}
	
	private TravelsDatabaseHelper mDbHelper;
	
	public TravelsProvider() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db=mDbHelper.getWritableDatabase();
		
		int match=mUriMatcher.match(uri);
		int rowdeleted=0;
		switch(match){
		
		case URI_TRAVELS:
			rowdeleted=db.delete(TravelsDatabaseHelper.TABLE_NAME, selection, selectionArgs);
		
		case URI_TRAVEL_ITEM:
			String id= uri.getPathSegments().get(1);
			String where=Travels._ID+"="+ id +" AND " + selection;
			rowdeleted=db.delete(TravelsDatabaseHelper.TABLE_NAME, where, selectionArgs);
			break;
		default:
			Log.w(TAG,"Uri didn't match " + uri);
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return rowdeleted;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		int match=mUriMatcher.match(uri);
		
		switch(match){
		case URI_TRAVELS:
			return "vnd.android.cursor.dir/vnd.example.travels";
			
		case URI_TRAVEL_ITEM:
			return "vnd.android.cursor.item/vnd.example.travels";
		default:
			Log.w(TAG,"Uri didn't match: " + uri);
			throw new IllegalArgumentException("Unknow URI: " +uri);
		}
		
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long id=db.insert(TravelsDatabaseHelper.TABLE_NAME, null, values);
		Uri result=null;
		
		if (id>=0)
		{
			result=ContentUris.withAppendedId(CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(uri, null);
			
		}
		
		return result;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mDbHelper= new TravelsDatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db=mDbHelper.getReadableDatabase();
		
		int match=mUriMatcher.match(uri);
		
		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
		qBuilder.setTables(TravelsDatabaseHelper.TABLE_NAME);
		
		switch (match){
		
		case URI_TRAVELS:
			//nada
			break;
		case URI_TRAVEL_ITEM:
			String id= uri.getPathSegments().get(1);
			qBuilder.appendWhere(Travels._ID+"="+id );
			break;
		default:
			Log.w(TAG,"Uri didn't match " + uri);
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		
		Cursor c =qBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		
		int match=mUriMatcher.match(uri);
		int rowupdated=0;
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		switch(match){
		
		case URI_TRAVELS:
			rowupdated=db.update(TravelsDatabaseHelper.TABLE_NAME, values, selection,selectionArgs);
			break;
		case URI_TRAVEL_ITEM:
			String id=uri.getPathSegments().get(1);
			String where=Travels._ID+"="+ id +" AND " +selection ;
			rowupdated=db.update(TravelsDatabaseHelper.TABLE_NAME, values, where, selectionArgs);
			break;
			
		default:
			Log.w(TAG,"Uri didn't match " + uri);
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return rowupdated;
	}

	public class Travels implements BaseColumns{
		
		public static final String CITY="city";
		public static final String COUNTRY="country";
		public static final String YEAR="year";
		public static final String NOTE="notes";
		public static final String IMAGE_VIAJE="image_viaje";
		
		
	}
}
