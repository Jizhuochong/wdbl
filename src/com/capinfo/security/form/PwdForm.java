package com.capinfo.security.form;

import org.hibernate.validator.constraints.NotBlank;


public class PwdForm {
	@NotBlank
	private String oldPwd;
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	@NotBlank
	private String newPwd;
	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	
}
