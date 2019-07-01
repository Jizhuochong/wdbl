package com.capinfo.wdbl.bean.file_process;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**电话记录*/

@Entity
@Table(name = "File_PhoneRecord", schema = "WDBL")
public class File_PhoneRecord implements Serializable {
	private static final long serialVersionUID = -577303431704783285L;
	private Long id;   				/*ID*/
	private String sn;				/*流水号*/
	private String phone_unit;      /*来电单位*/
	private String phone_person;	/*来电人*/
	private String phone_time;		/*来电时间*/
	private String telephone;		/*联系电话*/
	private String phone_content;	/*来电内容*/
	private String leader_opinion;	/*批示*/
	private String handle_result;	/*办理结果*/
	private String handle_unit;		/*经办单位*/
	private String handle_operator;	/*经办人*/
	
	private String leader_name;		/*领导*/
	private String isdel;           /*是否删除*/
	private String process_state;	/*流程当前状态*/
	private String nextprocess_state;/*流程待办状态*/
	
	@Id
	@SequenceGenerator(name = "SEQ_FilePhoneRecord", sequenceName = "SEQ_FilePhoneRecord")
	@GeneratedValue(generator = "SEQ_FilePhoneRecord")
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPhone_content() {
		return phone_content;
	}
	public void setPhone_content(String phone_content) {
		this.phone_content = phone_content;
	}
	public String getLeader_name() {
		return leader_name;
	}
	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}
	public String getLeader_opinion() {
		return leader_opinion;
	}
	public void setLeader_opinion(String leader_opinion) {
		this.leader_opinion = leader_opinion;
	}
	public String getHandle_result() {
		return handle_result;
	}
	public void setHandle_result(String handle_result) {
		this.handle_result = handle_result;
	}
	public String getHandle_unit() {
		return handle_unit;
	}
	public void setHandle_unit(String handle_unit) {
		this.handle_unit = handle_unit;
	}
	public String getHandle_operator() {
		return handle_operator;
	}
	public void setHandle_operator(String handle_operator) {
		this.handle_operator = handle_operator;
	}
	public String getProcess_state() {
		return process_state;
	}
	public void setProcess_state(String process_state) {
		this.process_state = process_state;
	}

	public String getPhone_unit() {
		return phone_unit;
	}
	public void setPhone_unit(String phone_unit) {
		this.phone_unit = phone_unit;
	}
	public String getPhone_person() {
		return phone_person;
	}
	public void setPhone_person(String phone_person) {
		this.phone_person = phone_person;
	}
	public String getPhone_time() {
		return phone_time;
	}
	public void setPhone_time(String phone_time) {
		this.phone_time = phone_time;
	}

	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getNextprocess_state() {
		return nextprocess_state;
	}
	public void setNextprocess_state(String nextprocess_state) {
		this.nextprocess_state = nextprocess_state;
	}



}