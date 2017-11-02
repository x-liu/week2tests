package com.example.serverTests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootApplication
public class ServerTestsApplication {
	private Client client;
	private String REST_SERVICE_URL = "http://xtest-leafiest-hubble.cfapps.io/";
	private static final String FAIL = "fail";
	static Logger log = LoggerFactory.getLogger(ServerTestsApplication.class.getName());

	private void init(){
		this.client = ClientBuilder.newClient();
	}

	public static void main(String[] args){
		ServerTestsApplication tester = new ServerTestsApplication();
		log.info("Initializing tester");
		//initialize the tester
		tester.init();
		//test get all users Web Service Method
		log.info("Testing get request");
		tester.testGet();
		log.info("Testing post request");
		tester.testPost();
	}

	private void testGet(){
		Response response = this.client
				.target(REST_SERVICE_URL+"xing/myhi")
				.request()
				.get();
		if ((response == null) || (response.getStatus() != 200)){
			log.error("Test case name: getTest, Result: " + FAIL);
		} else {
			Map<String, Object> jsonResponse = response.readEntity(Map.class);
			assertEquals(jsonResponse.get("firstName"), "Xing");
			assertEquals(jsonResponse.get("lastName"), "Liu");
			log.info("testGet() passed");
		}
	}

	private void testPost() {
		Form form = new Form();
		String firstName = "Mayur";
		String lastName = "Santani";
		form.param("firstName", firstName);
		form.param("lastName", lastName);
		String str = "{\"firstName\":\"Mayur\",\"lastName\":\"Santani\"}";
		WebTarget webResource = client.target(REST_SERVICE_URL);
		Response response = webResource.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(str));
		if ((response == null) || (response.getStatus() != 200)){
			log.error("Test case name: getTest, Result: " + FAIL);
		} else {
			Map<String, Object> jsonResponse = response.readEntity(Map.class);
			assertEquals(jsonResponse.get("name"), "Mayur Santani");
			assertEquals(jsonResponse.get("age"), 22.0);
			log.info("testPost() passed");
		}
	}
}
