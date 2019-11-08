package it.unimol.codesurvey.bean;

import java.io.Serializable;

public class Class implements Serializable {

	private static final long serialVersionUID = -6920954405189674032L;

	private int id;
	private String textToShow;
	private String systemName;
	private String relatedResources;
	
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
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
	public String getRelatedResources() {
		return relatedResources;
	}
	public void setRelatedResources(String relatedResources) {
		this.relatedResources = relatedResources;
	}
}
