package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.SpringbootJunitInttestExampleApplication;
import com.example.demo.model.Course;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJunitInttestExampleApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private MockMvc mockMvc;


	@Test
	public void testRetrieveStudentCourse_indifferent() throws Exception{
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students/Student1/courses/Course1").accept(MediaType.APPLICATION_JSON))
		.andReturn();
				
		String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

		assertEquals(expected, mvcResult.getResponse().getContentAsString());

	}
	
	@Test
	public void addCourse_indifferent() throws Exception {
		
		String json = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

		mockMvc.perform(MockMvcRequestBuilders.post("/students/Student1/courses").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				 .andExpect(header().string("location", containsString("/students/Student1/courses/")));
		

	}


	
//	@Before
//	public void before() {
//		headers.add("Authorization", createHttpAuthenticationHeaderValue(
//				"user1", "secret1"));
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	}

	@Test
	public void testRetrieveStudentCourse() throws JSONException{

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/students/Student1/courses/Course1"),
				HttpMethod.GET, entity, String.class);

		String expected = "{\"id\": \"Course1\", \"name\": \"Spring\", \"description\": \"10 Steps\"}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void addCourse() {

		Course course = new Course("Course1", "Spring", "10 Steps", Arrays
				.asList("Learn Maven", "Import Project", "First Example",
						"Second Example"));

		HttpEntity<Course> entity = new HttpEntity<Course>(course, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/students/Student1/courses"),
				HttpMethod.POST, entity, String.class);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

		assertTrue(actual.contains("/students/Student1/courses/"));

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

//	private String createHttpAuthenticationHeaderValue(String userId,
//			String password) {
//
//		String auth = userId + ":" + password;
//
//		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset
//				.forName("US-ASCII")));
//
//		String headerValue = "Basic " + new String(encodedAuth);
//
//		return headerValue;
//	}

}
