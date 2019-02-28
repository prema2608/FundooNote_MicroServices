package com.bridgelabz.fundoonotes.usermodels;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "UserInfo")
public class User implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "emailId", unique = true)
	private String emailId;

	@Column(name = "password", length = 60)
	private String password;

	@Column(name = "mobileNumber")
	private long mobileNumber;

	@Column(name = "activate_Status")
	private boolean activate_Status;


	public boolean isActivate_Status() {
		return activate_Status;
	}

	public User setActivate_Status(boolean activate_Status) {
		this.activate_Status = activate_Status;
		return this;
	}

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getEmailId() {
		return emailId;
	}

	public User setEmailId(String emailId) {
		this.emailId = emailId;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public User setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
		return this;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", emailId=" + emailId + ", password=" + password
				+ ", mobileNumber=" + mobileNumber + ", activate_Status=" + activate_Status +  "]";
	}

}
