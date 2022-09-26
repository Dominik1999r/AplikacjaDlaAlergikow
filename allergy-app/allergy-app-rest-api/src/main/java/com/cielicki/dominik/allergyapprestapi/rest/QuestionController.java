package com.cielicki.dominik.allergyapprestapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Question;
import com.cielicki.dominik.allergyapprestapi.db.model.QuestionList;
import com.cielicki.dominik.allergyapprestapi.db.repository.QuestionService;

/**
 * Endpoint do zapisywana i pobierania pytań użytkowników.
 */
@Component
@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	/**
	 * Dodaje pytanie do bazy.
	 * 
	 * @param question Pytanie użytkownika.
	 */
	@PostMapping(path="/addQuestion", consumes="application/json", produces="application/json")
	public void addQuestion(@RequestBody Question question) {
		questionService.save(question);
	}
	
	/**
	 * Zwraca listę pytań.
	 * 
	 * @return Zwraca listę pytań.
	 */
	@GetMapping(path="/getQuestions", produces = "application/json")
	public QuestionList getQuestions() {
		return questionService.findAll();
	}
}
