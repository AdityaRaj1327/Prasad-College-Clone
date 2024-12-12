package com.app.pitapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="responses")
public class Response {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	@Column(length =100,nullable = false)
	
	private long enrollmentno;
	@Column(length =100,nullable = false)
	
	private String name;
	@Column(length =100,nullable = false)
	
	private String branch;
	@Column(length =100,nullable = false)
	
	private String program;
	@Column(length =100,nullable = false)
	
	private String year;
	
	@Column(length =100,nullable = false)
	private String responsetype;
	@Column(length =100,nullable = false)
	
	private String responsetext;
	@Column(length =100,nullable = false)
	
	private String posteddate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResponsetype() {
		return responsetype;
	}
	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
	}
	public String getResponsetext() {
		return responsetext;
	}
	public void setResponsetext(String responsetext) {
		this.responsetext = responsetext;
	}
	public long getEnrollmentno() {
		return enrollmentno;
	}
	public void setEnrollmentno(long enrollmentno) {
		this.enrollmentno = enrollmentno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPosteddate() {
		return posteddate;
	}
	public void setPosteddate(String posteddate) {
		this.posteddate = posteddate;
	}
	
}
