package com.cielicki.dominik.allergyapprestapi;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.repository.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK, classes=AllergyAppRestApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application.properties")
@TestMethodOrder(OrderAnnotation.class)
class AllergyAppRestApiApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	@Order(1)
	public void addNewUser() throws Exception {
		User user = new User();
		user.setEmail("test2@email.com");
		user.setName("Test2");
		user.setUsername("test2");
		user.setLastName("Test2");
		user.setPassword("Password2");
		user.setSalt("Salt2");
		
	    String requestJson = objectToString(user);
		
		mvc.perform(MockMvcRequestBuilders.post("/user/addUser").contentType(MediaType.APPLICATION_JSON).content(requestJson))
		.andExpect(status().isOk());
	}
	
	@Test
	@Order(2)
	public void checkIfUserExists() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/user/getUsers").contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userList[0].name", is("Test2")));
	}
	
	private static String objectToString(final Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
		    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		    
		    return ow.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
