package com.capinfo.wdbl.bean.file_process;

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
 * 上级领导批示
 * @author liulei
 *
 */
@Entity
@Table(name = "File_SuperLeader_Command", schema = "WDBL")
public class SuperLeaderCommand {
	
	private Long id;
	
	private FileReg fileReg;  //文件登记
	
	private String leaderName; //上级领导名
	
	private String commandContent; //领导批示
	
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "fileSLCIdGen", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_FILE_SLC_ID") })
    @GeneratedValue(generator = "fileSLCIdGen")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ForeignKey(name="FK_FILE_REG_ID_OF_SLC") @JoinColumn(name = "FILE_REG_ID")
	@ManyToOne @JsonIgnore
	public FileReg getFileReg() {
		return fileReg;
	}

	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getCommandContent() {
		return commandContent;
	}

	public void setCommandContent(String commandContent) {
		this.commandContent = commandContent;
	}

	
}
