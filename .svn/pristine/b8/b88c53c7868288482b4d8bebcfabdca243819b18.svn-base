package com.capinfo.wdbl.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**字典表*/

@Entity
@Table(name = "CodeInfo", schema = "WDBL")
public class CodeInfo implements Serializable {

	private static final long serialVersionUID = -8265603154572697901L;
	private Long id;   			/*ID*/
	private String code;		/*代码*/
	private String node;		/*文本*/
	private String parent_code;	/*父节点*/
	private Integer dis_num;		/*显示顺序*/
	
	
	@Id
	@SequenceGenerator(name = "SEQ_CodeInfo1", sequenceName = "SEQ_CodeInfo")
	@GeneratedValue(generator = "SEQ_CodeInfo1")
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "node")
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	@Column(name = "parent_code")
	public String getParent_code() {
		return parent_code;
	}
	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}
	@Column(name = "dis_num")
	public Integer getDis_num() {
		return dis_num;
	}
	public void setDis_num(Integer dis_num) {
		this.dis_num = dis_num;
	}


}