package com.capinfo.wdbl.bean.processConfig;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**流程信息表*/

@Entity
@Table(name = "process_info", schema = "WDBL")
public class ProcessInfo implements Serializable {
	private static final long serialVersionUID = 7700737944709726179L;
	private Long id;   				/*ID*/
	private String name;      		/*名称*/
	private List<ProcessNode> processNodeList;

	@Id
	@SequenceGenerator(name = "SEQ_process_info", sequenceName = "SEQ_process_info")
	@GeneratedValue(generator = "SEQ_process_info")
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


	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy="processInfo")
	@OrderBy(" serialNun asc ")
	public List<ProcessNode> getProcessNodeList() {
		return processNodeList;
	}
	public void setProcessNodeList(List<ProcessNode> processNodeList) {
		this.processNodeList = processNodeList;
	}

}