package com.trial;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

	public static String URL = "http://localhost:8080/ACTS-WebApps/restapp/hello";
	public static void main(String[] args) {

		RestTemplate restTemplate = new RestTemplate();
		String ret = "";
		
		//Test GET with simple string input and output
		ret = restTemplate.getForObject("http://localhost:8080/ACTS-WebApps/restapp/hello", String.class);
		System.out.println("output="+ret);
		
		//jackson libs for serializing back forth java-json
		restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(list);

		//Test GET with java object as output
		Person p = restTemplate.getForObject("http://localhost:8080/ACTS-WebApps/restapp/person/1", Person.class);
		System.out.println("output after get with Person="+p);

		//Test PUT with java object as input
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Person> entity = new HttpEntity<Person>(new Person(),headers);
		
		restTemplate.put("http://localhost:8080/ACTS-WebApps/restapp/person",entity);
		System.out.println("after PUT success");
		
		//restTemplate.delete("http://localhost:8080/ACTS-WebApps/restapp/person");
		//String str1 = "{\"firstName\":\"Ganesh Ghag 007\",\"lastName\":\"Ghag007\",\"email\":\"jamesbond@email.com\"}";
		//restTemplate.exchange("http://localhost:8080/ACTS-WebApps/restapp/person",HttpMethod.DELETE,new HttpEntity<String>(str1),Person.class);
		System.out.println("restTemplate delete() method does not support request body");
		System.out.println("after DELETE success");
		
		p = restTemplate.postForObject("http://localhost:8080/ACTS-WebApps/restapp/person",entity, Person.class);
		System.out.println("after POST success with ret = "+p);
		
		
		//Test GET for query with query params and with java object as output
		p = restTemplate.getForObject("http://localhost:8080/ACTS-WebApps/restapp/personsQuery?firstName=GanyaGhagDude", Person.class);
		System.out.println("after GET QUERY success with Person="+p);

		

	}

}
