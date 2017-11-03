package com.example.serverTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerTestsApplication.class)
public class ServerTestsApplicationTests {

	private Client client;
	private String REST_SERVICE_URL = "http://xtest-leafiest-hubble.cfapps.io/";
	private static final String FAIL = "fail";
	static Logger log = LoggerFactory.getLogger(ServerTestsApplication.class.getName());

	@Before
	public void setup(){
		log.info("Initializing tester");
	}

	@Test
	public void testGet(){
		this.client = ClientBuilder.newClient();
		log.info("Testing get request");
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
		client.close();
	}

	@Test
	public void testPost() {
		this.client = ClientBuilder.newClient();
		log.info("Testing post request");
//		Form form = new Form();
//		String firstName = "Mayur";
//		String lastName = "Santani";
//		form.param("firstName", firstName);
//		form.param("lastName", lastName);
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
		client.close();
	}

}
