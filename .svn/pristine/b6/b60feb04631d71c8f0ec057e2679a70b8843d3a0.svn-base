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
 * 转送单位表
 */
@Entity
@Table(name = "File_Transfer", schema = "WDBL")
public class File_Transfer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8306080206718357895L;
	private Long id;
	private FileReg fileReg;  //文件登记
	private String handleMode;   //处理方式
	private String fileWhereabouts;  //文件去向
	private String remarks;//备注
	private String handleMan;  //处理人
	private String handleDate;  //处理时间
	private String jishouren;				/*传真接收人*/
	private String psfk;		//批示反馈
	
	public File_Transfer(){
	}
	public File_Transfer(Long id){
		this.id = id;
	}
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "fileTransferIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_FILE_TRANSFER_ID") })
    @GeneratedValue(generator = "fileTransferIdGen")
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ForeignKey(name="FK_FILE_REG_ID_OF_TRANSFER")
	@JoinColumn(name = "FILE_REG_ID")
	@ManyToOne
	@JsonIgnore
	public FileReg getFileReg() {
		return fileReg;
	}
	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}
	@Column(length = 100)
	public void setHandleMode(String handleMode) {
		this.handleMode = handleMode;
	}
	public String getHandleMode() {
		return handleMode;
	}
	@Column(length = 100)
	public void setFileWhereabouts(String fileWhereabouts) {
		this.fileWhereabouts = fileWhereabouts;
	}
	public String getFileWhereabouts() {
		return fileWhereabouts;
	}
	@Column(length = 500)
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	@Column(length = 100)
	public void setHandleMan(String handleMan) {
		this.handleMan = handleMan;
	}
	public String getHandleMan() {
		return handleMan;
	}
	public String getJishouren() {
		return jishouren;
	}
	@Column(length = 100)
	public void setJishouren(String jishouren) {
		this.jishouren = jishouren;
	}
	@Column(length = 100)
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
	public String getHandleDate() {
		return handleDate;
	}
	public String getPsfk() {
		return psfk;
	}
	@Column(length=500)
	public void setPsfk(String psfk) {
		this.psfk = psfk;
	}
}
