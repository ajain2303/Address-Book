package com.project.eai.eai;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.put;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import javax.lang.model.element.Element;

import org.eclipse.jetty.io.ssl.ALPNProcessor.Client;

import spark.Spark;

public class ContactService 
{
	
	public static Properties loadProperty() {
		Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("host"));
            System.out.println(prop.getProperty("port"));
            

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
	}
	
    public static void main( String[] args )
    {
    	Properties prop=loadProperty();
   	
    	try {
			ContactController.initClient(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));
		} catch (UnknownHostException e) {
			System.out.println("ERROR: Unable to start the Elastic Search");
			e.printStackTrace();
		}
    	
    	
    	
   	 	get("/contact/:size/:page", (request, response)->{
   	 		return JsonTransformer.toJson(ContactController.getContactsPerPage(request.params(":size"), request.params(":page")));
   	 	});
    	
    	get("/contact", (request, response) -> {
        	return JsonTransformer.toJson( ContactController.getAllContacts());
        });
    	
        get("/contact/:name", (request, response) -> {
        	return JsonTransformer.toJson(ContactController.getContact(request.params(":name")));
        });		   
        
        post("/contact", (request, response)->{
        	String body = request.body();
        	Contact contact = JsonTransformer.fromJson(body, Contact.class);
        	return JsonTransformer.toJson(ContactController.addContact(contact));   	
        	
        });
        
        //not fully functioning
        put("/contact/:name", (request, response)->{
        	String body = request.body();
        	return JsonTransformer.toJson(ContactController.changeContactInfo(request.params(":name"), body));
        });
    	
        delete( "/contact/:name", (request, response) -> {
    		return JsonTransformer.toJson(ContactController.deleteContact(request.params(":name")));
   		 
   	 	});
    }
}

