package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.Question;

/**
 * Klasa reprezentująca tablicę obiektów typu Question.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class QuestionList {
	private List<Question> questionList;
	
	public QuestionList() {
		questionList = new ArrayList<Question>();
	};
	
	public QuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
}
