package com.capinfo.wdbl.bean.processConfig;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**电话记录*/

@Entity
@Table(name = "Process_Node", schema = "WDBL")
public class ProcessNode implements Serializable {
	private static final long serialVersionUID = -4167337102295408448L;
	private Long id;   				/*ID*/
	private String name;      		/*名称*/
	private int serialNun;			/*序号*/
	private ProcessInfo processInfo; /*流程信息*/

	@Id
	@SequenceGenerator(name = "SEQ_ProcessNode", sequenceName = "SEQ_ProcessNode")
	@GeneratedValue(generator = "SEQ_ProcessNode")
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSerialNun() {
		return serialNun;
	}
	public void setSerialNun(int serialNun) {
		this.serialNun = serialNun;
	}
	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "processinfoid")
	public ProcessInfo getProcessInfo() {
		return processInfo;
	}
	public void setProcessInfo(ProcessInfo processInfo) {
		this.processInfo = processInfo;
	}


}