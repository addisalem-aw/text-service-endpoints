package com.galvanize.textservice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TextServiceController.class)
 public class RequestsResponsesCheckpointApplicationTests{
	@Autowired
	MockMvc mvc;

	//////Endpoint 1 - Camelize
	@Test
	public void testStringNameConvertFromSnakeToCamelInitalCapFalse() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/camelize?original=this_is_a_thing");
		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string("thisIsAThing"));
	}
	@Test
	public void testStringNameConvertFromSnakeToCamelInitalCapTrue() throws Exception {
		RequestBuilder request=MockMvcRequestBuilders.get("/camelize?original=this_is_a_thing&initialCap=true");
		this.mvc.perform(request).andExpect(status().isOk())
				.andExpect(content().string("ThisIsAThing"));
	}
	////Endpoint 2 - Redact
	@Test
	public void testReplaceBadWordsWithAsterisk() throws Exception {
		RequestBuilder request=MockMvcRequestBuilders.get("/redact?original=A little of this and a little of that&badWord=little&badWord=this");
		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string("A ****** of **** and a ****** of that"));

	}


}
