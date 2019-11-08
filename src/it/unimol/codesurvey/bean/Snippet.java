package it.unimol.codesurvey.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Snippet implements Serializable, Comparable<Snippet>{

	private static final long serialVersionUID = -6920954405189674032L;

	private int id;
	private String textToShow;
	private String title;
	private String systemName;
	private ArrayList<Assessment> assessments;
	private String relatedResources;
	
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public ArrayList<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(ArrayList<Assessment> assessments) {
		this.assessments = assessments;
	}
	
	public String getTextToShow() {
		return textToShow;
	}
	public void setTextToShow(String textToShow) {
		this.textToShow = textToShow;
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
	
	@Override
	public int compareTo(Snippet toCompare) {
		Integer thisAssessments = this.assessments.size();
		Integer toCompareAssessments = toCompare.assessments.size();
		
		return thisAssessments.compareTo(toCompareAssessments);
	}
	public String getRelatedResources() {
		return relatedResources;
	}
	public void setRelatedResources(String relatedResources) {
		this.relatedResources = relatedResources;
	}
	
	
	
}
