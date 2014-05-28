package com.trial;

import java.util.Date;

public class Person {
	
	private long id;
	private String firstName;
	private String lastName;
	private String mobile;
	private String email;
	private String city;
	
	public Person(){
		id = new Date().getTime();
		firstName = "Ganesh";
		lastName = "Ghag";
		mobile = "9384838393";
		email = "this@email.com";
		city = "Thane";
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", mobile=" + mobile + ", email=" + email
				+ ", city=" + city + "]";
	}
	
	
	

}
