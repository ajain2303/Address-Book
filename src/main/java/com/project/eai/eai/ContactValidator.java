package com.project.eai.eai;

import java.util.ArrayList;
import java.util.List;

public class ContactValidator {
	
	private Contact contact;
	private List<String> messages = new ArrayList<String>();
	private boolean valid = true;
	
	public ContactValidator(Contact contact) {
		this.contact = contact;
		validateName();
		validatePhoneNumber();
		validateCity();
		validateState();
		validateStreetAddress();
	}
	
	
	public void validateName() {
		if(contact.getName().length()>20) {
			valid = false;
			messages.add("ERROR: Contact Name over 20 characters long");
		}	
		
	}
	
	public void validateState() {
		if(contact.getState().length() >20) {
			valid = false;
			messages.add("ERROR: Invalid State");
		}		
	}
	
	public void validateCity() {
		if(contact.getCity().length() >20) {
			valid = false;
			messages.add("ERROR: Invalid City");
		}		
	}
	
	
	public void validateStreetAddress() {
		if(contact.getStreetAddress().length()>50) {
			valid = false;
			messages.add("ERROR: Invalid Street Address");
		}		
	}
	
	public void validatePhoneNumber() {
		if(contact.getPhoneNumber().length() != 10) {
			valid = false;
			messages.add("ERROR: Invalid Phone Number. Phone Number should be a 10 digit numeric number.");
			return;
		}
		
		try {
			Integer.parseInt(contact.getPhoneNumber());
		} catch(NumberFormatException e) {
			valid = false;
			messages.add("ERROR: Invalid Phone Number. Phone Number should be a 10 digit numeric number.");
		}
	}
	


	public List<String> getMessages() {
		return messages;
	}



	public boolean isValid() {
		return valid;
	}




	
}
