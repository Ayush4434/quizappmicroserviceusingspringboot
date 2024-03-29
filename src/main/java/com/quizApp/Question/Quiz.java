package com.quizApp.Question;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Quiz {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private int id;
   private String title;
   @ManyToMany
   private List<Question> question;
   
   public Quiz(int id, String title, List<Question> question) {
	super();
	this.id = id;
	this.title = title;
	this.question = question;
}

public Quiz() {
	super();

}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public List<Question> getQuestion() {
	return question;
}

public void setQuestion(List<Question> question) {
	this.question = question;
}


}