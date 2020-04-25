package com.hs.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.hs.util.PropertyReader;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CRUDPETS {  
	
  

@BeforeMethod
  public void setup() throws IOException  
  {  
	 PropertyReader prop = new PropertyReader();
	 baseURI = prop.getProperty("url_api");
  }	
  
  @Test
  public void taskTestPOST(ITestContext context)
  {   
	  //Make the API Request
	  RequestSpecification request = RestAssured.given();
	  String myJson ="{\r\n  \"id\": 0,\r\n  \"category\": {\r\n    \"id\": 0,\r\n    \"name\": \"string\"\r\n  },\r\n  \"name\": \"doggie\",\r\n  \"photoUrls\": [\r\n    \"string\"\r\n  ],\r\n  \"tags\": [\r\n    {\r\n      \"id\": 0,\r\n      \"name\": \"string\"\r\n    }\r\n  ],\r\n  \"status\": \"available\"\r\n}";
	  Response r = given()
		    	.contentType("application/json").
		    	body(myJson).
		        when().
		        post("");
                
		    	String body = r.getBody().asString();
		    	System.out.println("response body for post" + body);
		    	int statusCode = r.getStatusCode();
		    	
		    	// Check status code and response content
		    	 Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
		    	
		    	 Assert.assertEquals(body.contains("doggie") /*Expected value*/, true /*Actual Value*/, "Response body contains doggie");
		    	
		    	 // Get the ID
		    	 JsonPath jsonPathEvaluator = r.jsonPath();
		    	 String idPost = jsonPathEvaluator.get("id").toString();
		    	 // Store it into a variable to use in Update operation
		    	 context.setAttribute("idPostOut", idPost);
		    	
		    	 
		    
  }
	
  
  @Test(dependsOnMethods = "taskTestPOST")
  public void taskTestUpdate(ITestContext context) 
  {   
	  String prevResult = (String) context.getAttribute("idPostOut");
	
	  
	  // Use the id from previous post operation
	  RequestSpecification request = RestAssured.given();
	  String myJson ="{\r\n  \"id\":"+ prevResult+",\r\n  \"category\": {\r\n    \"id\": 0,\r\n    \"name\": \"string\"\r\n  },\r\n  \"name\": \"pigeon\",\r\n  \"photoUrls\": [\r\n    \"string\"\r\n  ],\r\n  \"tags\": [\r\n    {\r\n      \"id\": 0,\r\n      \"name\": \"string\"\r\n    }\r\n  ],\r\n  \"status\": \"available\"\r\n}";
	  Response r = given()
		    	.contentType("application/json").
		    	body(myJson).
		        when().
		        put("");

		    	String body = r.getBody().asString();
		    	int statusCode = r.getStatusCode();
		    	//Check the status code
		    	Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
		    	
		    	System.out.println("response body for Update" + body);
		    	// Check the updated value namely pigeon in the response
		    	Assert.assertEquals(body.contains("pigeon") /*Expected value*/, true /*Actual Value*/, "Updated response body contains pigeon");
		        JsonPath jsonPathEvaluator = r.jsonPath();
		    	String idPutOutR = jsonPathEvaluator.get("id").toString();
		    	context.setAttribute("idPutOut", idPutOutR);
	           
  }
  @Test(dependsOnMethods = "taskTestUpdate")
  public void taskTest(ITestContext context) 
  {   
	  // Use the id for the updated hit
	  String UpdateResult = (String) context.getAttribute("idPutOut");
	  
	  RequestSpecification httpRequest = RestAssured.given();
	  Response response = httpRequest.get(UpdateResult);
	  String responseBody = response.getBody().asString();
	  System.out.println("Response Body for GET is =>  " + responseBody);
	  int statusCode = response.getStatusCode();
  	  // Check the status code
  	  Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
  	  // Check the body contains the id
  	 Assert.assertEquals(responseBody.contains("pigeon") /*Expected value*/, true /*Actual Value*/, "Response body contains pigeon");
	
  }
  
  @Test(dependsOnMethods = "taskTestUpdate")
  public void taskTestDelete(ITestContext context) 
  {   
	  //Make the API Request
	  //Make the API Request
	  String UpdateResult = (String) context.getAttribute("idPutOut");
	  RequestSpecification httpRequest = RestAssured.given();
	  Response response = httpRequest.request(Method.DELETE,UpdateResult);
	  String responseBody = response.getBody().asString();
	  System.out.println("Response Body for Delete is =>  " + responseBody);
	int statusCode = response.getStatusCode();
  	
  	Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
  	Assert.assertEquals(responseBody.contains(UpdateResult) /*Expected value*/, true /*Actual Value*/, "Response body contains deleted id");
	 
  }
  
  @AfterMethod
  public void clean()
  {
	 reset(); 
  }
  
}
