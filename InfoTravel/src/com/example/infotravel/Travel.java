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
	
	public Travel()
	{
			
	}
	private Travel(Parcel in){
		this.city=in.readString();
		this.country=in.readString();
		this.year=in.readInt();
		this.note=in.readString();
		
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
	
	@Override
    public int describeContents() {
        return 0;
    }
  
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	 dest.writeString(this.city);
    	 dest.writeString(this.country);
    	 dest.writeInt(this.year);
    	 dest.writeString(this.note);
        
    }
      
    public static final Travel.Creator<Travel> CREATOR = new Travel.Creator<Travel>() {
        public Travel createFromParcel(Parcel in) {
            return new Travel(in);
        }
  
        public Travel[] newArray(int size) {
            return new Travel[size];
        }
    };  
   
	
}
