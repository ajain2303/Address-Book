package com.project.eai.eai;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.*;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.google.gson.Gson;


public class ContactController {

	private static ArrayList<Contact> contacts = new ArrayList<Contact>();;
	private static Client client;
	
	

	public static void initClient(String host, int port) throws UnknownHostException {	
		client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));                          	
	}

	public static List<Contact> getContactsPerPage(String size, String from) {
		List<Contact> results = new ArrayList<Contact>();
		int size_page = Integer.parseInt(size);
		int offSet = (Integer.parseInt(from) -1)*size_page;
		String searchStr = "{index: \'contact\', from:"+offSet+", size:"+size_page+", body:{\"query\" { \"match_all\":{}}} }";
		SearchRequest searchRequest = new SearchRequest(searchStr);
		SearchResponse response = client.search(searchRequest).actionGet();
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		searchHits.forEach(hit -> results.add(JsonTransformer.fromJson(hit.getSourceAsString(), Contact.class)));
		
		return results;
	}
	
	public static ContactResponse addContact(Contact contact) {
		ContactResponse resp = new ContactResponse();
		
		if(getContact(contact.getName()).size()>0) {
			resp.addMessage(contact.getName()+ " already exists in Contacts.");
			return resp;
		}
		
		ContactValidator validator = new ContactValidator(contact);
		if(!validator.isValid()) {
			 resp.setMessage(validator.getMessages());
			 return resp;
		}
		
		String jsonObject = JsonTransformer.toJson(contact);		
		IndexResponse response = client.prepareIndex("contact", "_doc")
			      .setSource(jsonObject, XContentType.JSON).get();
		
		resp.addMessage(contact.getName() + " added.");
		
		return resp;
	}
	
	public static List<Contact> getContact(String name) {
		List<Contact> results = new ArrayList<Contact>();
		
		QueryBuilder qb = QueryBuilders.matchQuery("name", name);
		SearchResponse response = client.prepareSearch()
				.setPostFilter(qb).execute().actionGet();
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		searchHits.forEach(hit -> results.add(JsonTransformer.fromJson(hit.getSourceAsString(), Contact.class)));
		
		return results;
	}
	
	public static List<Contact> getAllContacts() {
		SearchResponse response = client.prepareSearch().execute().actionGet();
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		List<Contact> results = new ArrayList<Contact>();
		searchHits.forEach(hit -> results.add(JsonTransformer.fromJson(hit.getSourceAsString(), Contact.class)));	
		return results;
		
	}
	
	public static ContactResponse deleteContact(String name) {
		ContactResponse respStr = new ContactResponse();
		if(getContact(name).size() == 0) {
			respStr.addMessage("ERROR: Name does not exist in Contacts");
			return respStr; 
		}
		
		QueryBuilder qb = QueryBuilders.matchQuery("name", name);
		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).filter(qb).source("contact").get();
		
		respStr.addMessage("Contact Deleted");
		return respStr;
		
	}
	
	public static ContactResponse changeContactInfo(String name, String json) throws InterruptedException, ExecutionException {
		ContactResponse respStr = new ContactResponse();
		if(getContact(name).size() ==1) {
			respStr.addMessage("ERROR: Name does not exist in contacts");
			return respStr;
		}
		Contact contact = getContact(name).get(0);
		
		return respStr;
		
	
	}
	
}
