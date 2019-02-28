package com.bridgelabz.fundoonotes.notemodels;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "Labels")
public class Labels implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "labelId")
	private int labelId;

	@Column(name = "labelName")
	private String labelName;


	@JoinColumn(name="userId")
	private int userId;


	public int getLabelId() {
		return labelId;
	}


	public Labels setLabelId(int labelId) {
		this.labelId = labelId;
		return this;
	}


	public String getLabelName()
	{
		return labelName;
	}


	public Labels setLabelName(String labelName) 
	{
		this.labelName = labelName;
		return this;
	}


	public int getUserId() {
		return userId;
	}


	public Labels setUserId(int userId) {
		this.userId = userId;
		return this;
	}


	@Override
	public String toString() {
		return "Labels [labelId=" + labelId + ", labelName=" + labelName + ", userId=" + userId + "]";
	}



}
