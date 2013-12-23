package com.example.infotravel;

import android.os.Parcel;
import android.os.Parcelable;



public class Travel implements Parcelable{ 

	private Integer idTravel;
	private String city;
	private String country;
	private Integer year;
	private String note;
	private String image;
	
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
	public Integer getYear() {
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
		this.idTravel=(Integer)in.readValue(null);
		this.city=in.readString();
		this.country=in.readString();
		this.year=(Integer)in.readValue(null);//in.readInt();
		this.note=in.readString();
		this.image=in.readString();
		
		
  }

	
	public Travel(Integer idTravel,String city, String country, Integer year)
	{
		this.idTravel=idTravel;
		this.city=city;
		this.country=country;
		this.year=year;
		
			
	}
	
	public Travel(Integer idTravel,String city,String country, Integer year, String note)
	{
		this(idTravel,city,country,year);
		
		this.note=note;
		
	}
	
	@Override
    public int describeContents() {
        return 0;
    }
  
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	 dest.writeValue(this.idTravel);
    	 dest.writeString(this.city);
    	 dest.writeString(this.country);
    	 dest.writeValue(this.year);
    	 dest.writeString(this.note);
    	 dest.writeString(this.image);
        
    }
      
    public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public Integer getIdTravel() {
		return idTravel;
	}
	public void setIdTravel(Integer idTravel) {
		this.idTravel = idTravel;
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
