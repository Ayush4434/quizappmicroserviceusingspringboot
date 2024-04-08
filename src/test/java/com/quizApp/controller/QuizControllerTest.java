package com.quizApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizApp.Question.QuestionWrapper;
import com.quizApp.Question.Response;
import com.quizApp.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class QuizControllerTest {

    private MockMvc mockMvc;

    @Test
    public void testCreateQuiz() throws Exception {
        // Mock QuizService
        QuizService quizServiceMock = mock(QuizService.class);
        when(quizServiceMock.createQuiz(anyString(), anyInt(), anyString())).thenReturn(new ResponseEntity<>("Success", HttpStatus.CREATED));

        // Initialize QuizController with mocked QuizService
        QuizController quizController = new QuizController();
        quizController.q = quizServiceMock;

        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();

        // Perform the test
        mockMvc.perform(post("/quiz/create")
                        .param("category", "TestCategory")
                        .param("numQ", "5")
                        .param("title", "TestTitle"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetQuizQuestions() throws Exception {
        // Mock QuizService
        QuizService quizServiceMock = mock(QuizService.class);
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        when(quizServiceMock.getQuizService(anyInt())).thenReturn(new ResponseEntity<>(questionWrappers, HttpStatus.OK));

        // Initialize QuizController with mocked QuizService
        QuizController quizController = new QuizController();
        quizController.q = quizServiceMock;

        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();

        // Perform the test
        mockMvc.perform(get("/quiz/getquiz/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSubmitQuiz() throws Exception {
        // Mock QuizService
        QuizService quizServiceMock = mock(QuizService.class);
        when(quizServiceMock.calculateResult(anyInt(), anyList())).thenReturn(new ResponseEntity<>(5, HttpStatus.OK));

        // Initialize QuizController with mocked QuizService
        QuizController quizController = new QuizController();
        quizController.q = quizServiceMock;

        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();

        // Prepare the request body
        List<Response> responses = new ArrayList<>();
        // Add sample responses here if needed

        // Perform the test
        mockMvc.perform(post("/quiz/submit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(responses)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

