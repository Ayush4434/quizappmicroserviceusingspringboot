package com.quizApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizApp.Question.Question;
import com.quizApp.service.QuestionService;

@RestController
@RequestMapping("/question")
public class MainController {
   @Autowired
   QuestionService service;
   
   @PostMapping("add")
   public ResponseEntity<String> addquestion(@RequestBody Question question)//We get question in json format 
   {
	   return service.addquestion(question);
	 
   }
   @PostMapping("adds")
   public ResponseEntity<String> addquestion(@RequestBody List<Question> question)//We get question in json format 
   {
	   return service.addquestions(question);
	 
   }
   
   @GetMapping("allquestions")
   public ResponseEntity<List<Question>> getAllQuestion()
   {
	   return service.getAllQuestion();
   }
   @GetMapping("category/{cat}")
   public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable("cat") String category)
   {
	   return service.getQuestionByCategory(category);
   }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> DeleteQuestion(@PathVariable int id)
    {
    	return service.DeleteQuestion(id);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question q ,@PathVariable int id)
    {
    	
    	Question updatedQuestion = service.updateQuestion(q, id);
        return ResponseEntity.ok(updatedQuestion);
    	 
    }
}
