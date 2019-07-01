package com.capinfo.wdbl.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity(name="LENUMBER")
public class LeNumber implements Serializable{
	private static final long serialVersionUID = -2478855959892749454L;
/*领导文号自增长序列*/
@GenericGenerator(name = "leIdGen", strategy = "sequence",
parameters = { @Parameter(name = "sequence", value = "SEQ_le_ID") })
@GeneratedValue(generator = "leIdGen")
@Id
private Long id;
private String fileNumber;
public LeNumber(){
	super();
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getfileNumber() {
	return fileNumber;
}
public void setfileNumber(String fileNumber) {
	this.fileNumber = fileNumber;
}
}