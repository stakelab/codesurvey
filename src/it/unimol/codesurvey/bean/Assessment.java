package it.unimol.codesurvey.bean;

import java.util.ArrayList;

public class Assessment {

	private int id;
	private int participantId;
	private int snippetId;
	private long secondsNeeded;
	private boolean understood;
	private double correctnessOfCheckingQuestions;
	private ArrayList<Question> questions;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public int getSnippetId() {
		return snippetId;
	}
	public void setSnippetId(int snippetId) {
		this.snippetId = snippetId;
	}
	public long getSecondsNeeded() {
		return secondsNeeded;
	}
	public void setSecondsNeeded(long secondsNeeded) {
		this.secondsNeeded = secondsNeeded;
	}
	public boolean isUnderstood() {
		return understood;
	}
	public void setUnderstood(boolean understood) {
		this.understood = understood;
	}
	public double getCorrectnessOfCheckingQuestions() {
		return correctnessOfCheckingQuestions;
	}
	public void setCorrectnessOfCheckingQuestions(double correctnessOfCheckingQuestions) {
		this.correctnessOfCheckingQuestions = correctnessOfCheckingQuestions;
	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
}
