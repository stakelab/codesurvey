package it.unimol.codesurvey.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Participant implements Serializable {

	private static final long serialVersionUID = -1649719759212378207L;
	
	private int id;
	private String name;
	private String email;
	
	private String position;
	private int javaExperience;
	private int programmingExperience;
	private Map<String, String> skills;
	
	private String username;
	private String password;
	private ArrayList<Assessment> assessments;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getJavaExperience() {
		return javaExperience;
	}
	public void setJavaExperience(int javaExperience) {
		this.javaExperience = javaExperience;
	}
	public int getProgrammingExperience() {
		return programmingExperience;
	}
	public void setProgrammingExperience(int programmingExperience) {
		this.programmingExperience = programmingExperience;
	}
	private String numberOfEvaluatedSnippets;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumberOfEvaluatedSnippets() {
		return numberOfEvaluatedSnippets;
	}
	public void setNumberOfEvaluatedSnippets(String numberOfEvaluatedSnippets) {
		this.numberOfEvaluatedSnippets = numberOfEvaluatedSnippets;
	}
	public ArrayList<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(ArrayList<Assessment> assessments) {
		this.assessments = assessments;
	}
	public Map<String, String> getSkills() {
		return skills;
	}
	public void setSkills(Map<String, String> skills) {
		this.skills = skills;
	}
}
