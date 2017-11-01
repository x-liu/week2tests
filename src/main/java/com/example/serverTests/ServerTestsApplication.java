package com.example.serverTests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootApplication
public class ServerTestsApplication {
	private Client client;
	private String REST_SERVICE_URL = "http://xtest-leafiest-hubble.cfapps.io/xing/myhi";
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String PASS = "pass";
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
	}
	//Test: Get list of all users
	//Test: Check if list is not empty
	private void testGet(){
		Response response = this.client
				.target(REST_SERVICE_URL)
				.request()
				.get();

		if(response == null){
			log.error("Test case name: getTest, Result: " + FAIL);
		} else {
			Map<String, Object> jsonResponse = response.readEntity(Map.class);
//			System.out.println("Keys:");
//			for (String key : jsonResponse.keySet())
//				System.out.println(key);
//			System.out.println("Values");
//			for (Object o : jsonResponse.values()) {
//				System.out.println(o);
//			}
//			System.out.println(jsonResponse.keySet());
			log.info("Test case name: getTest, Result: " + jsonResponse);
			assertEquals(jsonResponse.get("firstName"), "Xing");
			assertEquals(jsonResponse.get("lastName"), "Liu");
		}
	}
}
