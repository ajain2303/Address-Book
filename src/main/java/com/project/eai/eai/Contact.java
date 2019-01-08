package com.project.eai.eai;

public class Contact {
	
	private String name; 
	private String phoneNumber;
	private String city;
	private String state;
	private String streetAddress;
	
	public Contact(String name, String phoneNumber, String city, String state,
			String streetAddress) {
		super();
		this.name = name;	
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.state = state;
		this.streetAddress = streetAddress;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	
	
	
}
