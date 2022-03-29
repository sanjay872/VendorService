package com.vendor.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendor.VendorServiceApplication;
import com.vendor.entity.Vendor;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= VendorServiceApplication.class)
class VendorControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	private MockMvc mvc;
	private static long createdVendorId=0; 
	private String uri = "/vendor";
	
	@BeforeEach
	void setUp() {
	      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	 }
	
	@Test
	@Order(1)
	void createVendorTestRequest() throws Exception {
	   Vendor vendor=new Vendor("apple",4,"address","contact number"); 
	   String inputJson = mapToJson(vendor);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	       .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(201, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    Vendor newVendor=mapFromJson(content,Vendor.class);
	    createdVendorId=newVendor.getId();
	}
	
	@Test
	@Order(2)
	void updateVendorTestRequest() throws Exception{
		Vendor vendor=new Vendor(createdVendorId,"apple",4,"address","contact number"); 
	    String inputJson =mapToJson(vendor);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	       .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	}
	
	@Test
	@Order(3)
	void getAllVendorTestRequest() throws Exception {
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"/all")
	       .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    Vendor[] vendorList= mapFromJson(content, Vendor[].class);
	    assertTrue(vendorList.length > 0);
	}
	
	@Test
	@Order(4)
	void deleteVendorTestRequest() throws Exception{
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri+"/"+createdVendorId)).andReturn();
		int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	}
	
	
	
	 protected String mapToJson(Object obj) throws JsonProcessingException {
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.writeValueAsString(obj);
	   }
	  protected <T> T mapFromJson(String json, Class<T> clazz)
	      throws JsonParseException, JsonMappingException, IOException {
	      
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.readValue(json, clazz);
	   }
	
	
	

}
