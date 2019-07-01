package com.capinfo.wdbl.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 
 * [原始文件]
 * 
 */
@Entity(name = "ORIGINAL_FILE")
public class OriginalFile implements Serializable {

	// properties
	private static final long serialVersionUID = -1997091580796080795L;
	@Id
	@Column(nullable = false)
	@GenericGenerator(name = "originalFileIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_ORIGINAL_FILE_ID") })
	@GeneratedValue(generator = "originalFileIdGen")
	private Long id;
	/**
	 * 文件名
	 */
	@Column(name = "FILE_NAME")
	private String fileName;
	/**
	 * 文件大小
	 */
	@Column(name = "FILE_SIZE")
	private int fileSize;
	/**
	 * 文件路径
	 */
	@Column(name = "FILE_PATH")
	private String filePath;
	/**
	 * 文件后缀
	 */
	@Column(name = "FILE_SUFFIX")
	private String fileSuffix;
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	/**
	 * 序号(从0开始)
	 */
	private int ordinal;
	/**
	 * 文件登记信息
	 */
	@ForeignKey(name="FK_FILE_REG_ID_OF_ORIG")
	@JoinColumn(name = "FILE_REG_ID")
	@ManyToOne
	private FileReg fileReg;

	// constructors
	public OriginalFile() {
		super();
	}
	
	public OriginalFile(Long id) {
		super();
		this.id = id;
	}

	public OriginalFile(FileReg fileReg) {
		super();
		this.fileReg = fileReg;
	}


	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	@JsonIgnore
	public FileReg getFileReg() {
		return fileReg;
	}

	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}

	@Override
	public boolean equals(Object obj) {
		OriginalFile f = null;
		if(this == null || obj == null)
			return false;
		if(obj instanceof OriginalFile){
			f = (OriginalFile)obj;
		}else{
			return false;
		}
		if(this.getId() == null && f.getId() == null){ //都没有ID的情况下，有可能是都没有存入数据库中，这时比较文件的存储路径
			boolean isEqual = false;
			if(StringUtils.equals(this.getFilePath(), f.getFilePath()))
				isEqual = true;
			return isEqual;
		}
		if(this.getId() == null || f.getId() == null) //上一步已判断ID都为空的情况，此时，只要有一个id为空，两个对象就不相等
			return false;
		return this.getId().longValue() == f.getId().longValue();
	}
	
	

}