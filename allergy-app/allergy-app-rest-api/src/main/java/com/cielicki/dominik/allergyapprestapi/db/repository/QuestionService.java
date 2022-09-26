package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Question;
import com.cielicki.dominik.allergyapprestapi.db.model.QuestionList;

/**
 * Serwis służący do komunikacji z tabelą question w bazie danych.
 */
@Service
public class QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	/**
	 * Zapisuje pytanie do bazy danych.
	 * 
	 * @param question Obiekt pytania.
	 */
	public void save(Question question) {
		if (question != null) {
			questionRepository.save(question);
		}
	}
	
	/**
	 * Zwraca listę pytań.
	 * 
	 * @return Zwraca listę pytań.
	 */
	public QuestionList findAll() {
		return new QuestionList(questionRepository.findAll());
	}
}
