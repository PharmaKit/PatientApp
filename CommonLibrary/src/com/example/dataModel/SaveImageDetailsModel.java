package com.example.dataModel;

public class SaveImageDetailsModel {

	private String resourceType;
	private long personId;
	private String recepientName;
	private String recepientAddress;
	private String offerType;
	
	public SaveImageDetailsModel(String resourceType, long personId, String recepientName, String recepientAddress, String offerType){
		this.resourceType = resourceType;
		this.personId = personId;
		this.recepientName = recepientName;
		this.recepientAddress = recepientAddress;
		this.offerType = offerType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public long getPersonId() {
		return personId;
	}

	public String getRecepientName() {
		return recepientName;
	}

	public String getRecepientAddress() {
		return recepientAddress;
	}

	public String getOfferType() {
		return offerType;
	}
}
