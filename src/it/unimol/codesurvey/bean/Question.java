package it.unimol.codesurvey.bean;

import java.util.ArrayList;
import java.util.Collections;

public class Question {

	private int id;
	private String question;
	private ArrayList<Answer> answers;
	
	
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
		Collections.shuffle(answers);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
