package com.suravi.springdata.miniproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="doc_id")
	private int docID;
	@Column(name="doc_name")
	private String docName;
	@Column(name="doc_mail")
	private String docMail;
	@Column(name="doc_phone")
	private String phone;
	@Column(name="doc_clinic_name")
	private String docClinicName;
	@Column(name="doc_specialization")
	private String docSpecialization;
	@Column(name="doc_fees")
	private float docFees;

	// getters and setters 
	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocMail() {
		return docMail;
	}

	public void setDocMail(String docMail) {
		this.docMail = docMail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDocClinicName() {
		return docClinicName;
	}

	public void setDocClinicName(String docClinicName) {
		this.docClinicName = docClinicName;
	}

	public String getDocSpecialization() {
		return docSpecialization;
	}

	public void setDocSpecialization(String docSpecialization) {
		this.docSpecialization = docSpecialization;
	}

	public float getDocFees() {
		return docFees;
	}

	public void setDocFees(float docFees) {
		this.docFees = docFees;
	}

}
