package com.capinfo.wdbl.bean.file_process;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.capinfo.wdbl.bean.FileReg;

/**
 * 分送单位表
 */
@Entity
@Table(name = "File_Dispense", schema = "WDBL")
public class FileDispense implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -227684216933323070L;
	private Long id;
	private FileReg fileReg;  //文件登记
	private String unit;   //分送单位
	private String copyNum;   //份数
	private String numbers;   //号码
	private String isRecovery;//是否回收
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "fileDispenseIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_FILE_DISPENSE_ID") })
    @GeneratedValue(generator = "fileDispenseIdGen")
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ForeignKey(name="FK_FILE_REG_ID_OF_DISPENSE")
	@JoinColumn(name = "FILE_REG_ID")
	@ManyToOne @JsonIgnore
	public FileReg getFileReg() {
		return fileReg;
	}
	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnit() {
		return unit;
	}
	public void setCopyNum(String copyNum) {
		this.copyNum = copyNum;
	}
	public String getCopyNum() {
		return copyNum;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public String getNumbers() {
		return numbers;
	}
	public String getIsRecovery() {
		return isRecovery;
	}
	public void setIsRecovery(String isRecovery) {
		this.isRecovery = isRecovery;
	}
}
