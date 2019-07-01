package com.capinfo.schedule.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**字典表*/

@Entity
@Table(name = "SCHEDULE_CONFIG", schema = "WDBL")
public class ScheduleConfig implements Serializable {

	private static final long serialVersionUID = -2685108598506679766L;
	
	private Long id;   			/*ID*/
	private String executeDate;		/*执行时间*/
	private String executeCron;		/*执行时间cron表达式*/
	private String autoFinishDays;		/*自动办结期限*/
	private String isActive;		/*是否启用*/

	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "execute_date")
	public String getExecuteDate() {
		return executeDate;
	}
	
	@Column(name = "execute_cron")
	public String getExecuteCron() {
		return executeCron;
	}

	public void setExecuteCron(String executeCron) {
		this.executeCron = executeCron;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}
	
	@Column(name = "autofinish_days")
	public String getAutoFinishDays() {
		return autoFinishDays;
	}
	
	public void setAutoFinishDays(String autoFinishDays) {
		this.autoFinishDays = autoFinishDays;
	}

	@Column(name = "isactive")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}