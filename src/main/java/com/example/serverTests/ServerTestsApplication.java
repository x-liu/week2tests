package com.example.serverTests;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.Map;


@SpringBootApplication
public class ServerTestsApplication {
	private Client client;
	private String REST_SERVICE_URL = "http://xtest-leafiest-hubble.cfapps.io/xing/myhi";
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String PASS = "pass";
	private static final String FAIL = "fail";

	private void init(){
		this.client = ClientBuilder.newClient();
	}

	public static void main(String[] args){
		ServerTestsApplication tester = new ServerTestsApplication();
		//initialize the tester
		tester.init();
		//test get all users Web Service Method
		tester.testPost();
	}
	//Test: Get list of all users
	//Test: Check if list is not empty
	private void testPost(){
//		GenericType<List<User>> list = new GenericType<List<User>>() {};
//		List<User> users = client
//				.target(REST_SERVICE_URL)
//				.request(MediaType.APPLICATION_XML)
//				.get(list);
		String result;
		Response response = this.client
				.target(REST_SERVICE_URL)
				.request()
				.get();

		if(response == null){
			result = FAIL;
		} else {
			Map<String, Object> jsonResponse = response.readEntity(Map.class);
			System.out.println("Test case name: getTest, Result: " + jsonResponse);
		}

//		System.out.println("Test case name: getTest, Result: " + result );
	}
}
