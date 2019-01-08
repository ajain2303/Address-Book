package com.project.eai.eai;


import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class AddressBookTest 
{
	@Before
	public  void beforeClass() throws UnknownHostException {
		ContactController.initClient("127.0.0.1", 9300);
		
	}
	@Test
	public void testContact() {
		Contact contact = new Contact("Bobby", "1234567890", "New York", "NY", "6743 M St.");
		assertEquals("Bobby", contact.getName());
		assertEquals("1234567890", contact.getPhoneNumber());
		assertEquals("New York", contact.getCity());
		assertEquals("NY", contact.getState());
		assertEquals("6743 M St.", contact.getStreetAddress());
	}
    
    @Test
    public void testAddAndGetContact() {
    	Contact contact = new Contact("Bobby", "1234567890", "New York", "NY", "6743 M St.");
    	ContactController.addContact(contact);
    	List<Contact> list = ContactController.getContact("Bobby");
    	
    	String expected = JsonTransformer.toJson(contact);
    	String actual = JsonTransformer.toJson(list.get(0));
    	assertEquals(expected, actual);
    	
    }
    
    
    @Test
    public void testContactValidator() {
    	Contact contact1 = new Contact("Tom", "123", "New York", "NY", "6743 M St.");
    	//contact is invalid because phone number is too short
    	
    	ContactValidator validator1 = new ContactValidator(contact1);
    	assertEquals(false, validator1.isValid());
    	
    	//Name is over 20 characters
    	Contact contact2 = new Contact("Tommmmmmmmmmmmmmmmmmmmm", "1234567890", "New York", "NY", "6743 M St.");
    	ContactValidator validator2 = new ContactValidator(contact2);
    	assertEquals(false, validator2.isValid());
    	
    	Contact contact3 = new Contact("Tom", "1234567890", "New York", "NY", "6743 M St.");
    	ContactValidator validator3 = new ContactValidator(contact3);
    	assertEquals(true, validator3.isValid());
    	
    	//State over 20 characters long
    	Contact contact4 = new Contact("Tom", "1234567890", "New York", "NYYYYYYYYYYYYYYYYYYYYYYYYYYYY", "6743 M St.");
    	ContactValidator validator4 = new ContactValidator(contact4);
    	assertEquals(false, validator4.isValid());
    	
    	
    	//City over 20 characters long
    	Contact contact5 = new Contact("Tom", "1234567890", "New Yorkkkkkkkkkkkkkkkkkkkkkkkk", "NY", "6743 M St.");
    	ContactValidator validator5 = new ContactValidator(contact5);
    	assertEquals(false, validator5.isValid());
    	
    	//Street Address over 50 characters long
    	Contact contact6 = new Contact("Tom", "1234567890", "New York", "NY", "6743 M St........................................................................................");
    	ContactValidator validator6 = new ContactValidator(contact6);
    	assertEquals(false, validator6.isValid());
    }
   

   
    
}
