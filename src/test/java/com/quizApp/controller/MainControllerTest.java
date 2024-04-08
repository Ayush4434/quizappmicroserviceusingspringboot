package com.quizApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quizApp.Question.Question;
import com.quizApp.repository.QuestionRepository;
import com.quizApp.service.QuestionService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class MainControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private QuestionService service;
	@MockBean
	private QuestionRepository repos;
	Question q,q1,q2,q3,q4;
	List<Question> questionList = new ArrayList<>();
	 @BeforeEach
	  void setUp() {
	      q = new Question(1,"Java","Easy","Object-Oriented Programming","Object-Oriented Protocol","Object-Oriented Processing","Object-Oriented Procedure","What does OOP stand for in Java?","Object-Oriented Programming");
	      q1 = new Question(2, "Java", "Medium", "final", "const", "static", "let", "Which keyword is used to define a constant in Java?", "final");
	      q2 = new Question(3, "Python", "Easy", "Mutable", "Immutable", "Sequential", "Linear", "What is a Python list?", "Mutable");
	      q3 = new Question(4, "Python", "Medium", "The current object instance", "The parent class", "The child class", "The global namespace", "In Python, what does the 'self' keyword represent in a class?", "The current object instance");
	      q4 = new Question(5, "Python", "Medium", "Lists", "Tuples", "Dictionary", "Class", "Which of the following is not a core data type in Python?", "Class");
	      questionList.add(q);
	      questionList.add(q1);
	      questionList.add(q2);
	      questionList.add(q3);
	      questionList.add(q4);
	      
	  }
	 @AfterEach
	    void tearDown() {
	    }
	 @Test
	 void testAddQuestion() throws Exception{
		 ObjectMapper mapper = new ObjectMapper();
	        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	        ObjectWriter ow =  mapper.writer().withDefaultPrettyPrinter();
	        String requestJson = ow.writeValueAsString(q); 

	        // Mock response from service
	        ResponseEntity<String> successResponse = ResponseEntity.ok("Success");
	        when(service.addquestion(q)).thenReturn(successResponse);

	        // Perform the test
	        this.mockMvc.perform(post("/question/add")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(requestJson))
	                .andDo(print())
	                .andExpect(status().isOk());
	 }
	 @Test
	 void testGetAllQuestion() throws Exception
	 {
		 List<Question> questionList = new ArrayList<>();
	        ResponseEntity<List<Question>> responseEntity = new ResponseEntity<>(questionList, HttpStatus.OK);
	        when(service.getAllQuestion()).thenReturn(responseEntity);
		 this.mockMvc.perform(get("/question/allquestions")).andDo(print()).andExpect(status().isOk());
	 }
	 @Test
	    void updateCloudVendorDetails() throws Exception {
		 ObjectMapper mapper = new ObjectMapper();
	        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

	        String requestJson = ow.writeValueAsString(q1); 

	        when(repos.findById(1)).thenReturn(Optional.of(q));
	        when(repos.save(q)).thenReturn(q1);

	        mockMvc.perform(put("/question/update/1") 
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(requestJson))
	                .andDo(print())
	                .andExpect(status().isOk());
	    }

	    @Test
	    void deleteCloudVendorDetails() throws Exception {
	    	ResponseEntity<String> successResponse = ResponseEntity.ok("Successfully Deleted");
	        when(service.DeleteQuestion(1))
	                .thenReturn(successResponse);
	        this.mockMvc.perform(delete("/question/delete/" + "1"))
	                .andDo(print()).andExpect(status().isOk());

	    }
}
