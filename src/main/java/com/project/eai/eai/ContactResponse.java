package com.project.eai.eai;

import java.util.ArrayList;
import java.util.List;

public class ContactResponse {
	private List<String> messages = new ArrayList<String>();

	public ContactResponse(List<String> messages) {
		this.messages = messages;
	}
	public ContactResponse() {
		
	}
	public List<String> getMessages() {
		return messages;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}
	public void setMessage(List<String> messages) {
		this.messages = messages;
	}
 
}
