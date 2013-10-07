package com.example.infotravel;

import android.os.Parcel;
import android.os.Parcelable;

public class Travel implements Parcelable{

	private String city;
	private String country;
	private int year;
	private String note;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public Travel(String city, String country, int year)
	{
		this.city=city;
		this.country=country;
		this.year=year;
		
			
	}
	
	public Travel(String city,String country, int year, String note)
	{
		this(city,country,year);
		
		this.note=note;
		
	}
	
	public Travel(Parcel in) {
        readFromParcel(in);
    }
	private void readFromParcel(Parcel in) {
	    city=in.readString();
	    country=in.readString();
	    year=in.readInt();
	    note=in.readString();
		
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(city);
	    dest.writeString(country);
	    dest.writeInt(year);
	    dest.writeString(note);
	    
	}
	
	
}
