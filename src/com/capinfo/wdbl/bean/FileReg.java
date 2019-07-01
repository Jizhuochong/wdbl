package com.capinfo.wdbl.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.capinfo.wdbl.bean.file_process.FileDispense;
import com.capinfo.wdbl.bean.file_process.File_Handle;
import com.capinfo.wdbl.bean.file_process.File_Leader;
import com.capinfo.wdbl.bean.file_process.File_Transfer;
import com.capinfo.wdbl.bean.file_process.SuperLeaderCommand;

/**
 * 
 * [文件登记信息]
 *
 */
@Entity(name="FILE_REG")
public class FileReg implements Serializable{

	//properties
	private static final long serialVersionUID = -1497914832795538869L;
	/**
	 * 编号
	 */
	@GenericGenerator(name = "fileRegIdGen", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_FILE_REG_ID") })
	@GeneratedValue(generator = "fileRegIdGen")
	@Id
	private Long id;
	/**
	 * 文件流水号
	 */
	private String sn;
	/**
	 * 条码编号
	 */
	@Column(name="BAR_NO")
	private String barNo;
	/**
	 * 文件类型
	 */
	@Column(name="DOC_TYPE")
	private String docType;
	/**
	 * 文件标题
	 */
	@Column(name="TITLE")
	private String title;
	/**
	 * 内容摘要
	 */
	@Column(length=600)
	private String summary;
	/**
	 * 来文单位
	 */
	@Column(name="FORM_UNIT")
	private String formUnit;
	/**
	 * 经办单位
	 */
	@Column(name="HANDLE_UNIT")
	private String handleUnit;
	/**
	 * 经办人
	 */
	@Column(name="HANDLE_OPERATOR")
	private String handleOperator;
	/**
	 * 联系电话
	 */
	@Column(name="TELEPHONE")
	private String telephone;
	/**
	 * 来电人
	 */
	@Column(name="PHONE_PERSON")
	private String phonePerson;
	/**
	 * 领导批示
	 */
	@Column(name="LEADERSHIPPS",length=200)
	private String leadershipPS;
	/**
	 * 领导批示
	 */
	@Column(name="MAIN_LEADERSHIPPS",length=200)
	private String mainLeadershipPS;
	/**
	 * 来文时间
	 */
	@Column(name="RECEIVE_TIME")
	private String receiveTime;
	/**
	 * 文号(文件字号)
	 */
	@Column(name="DOC_NUMBER",length=60)
	private String docNumber;
	/**
	 * 领导文号
	 */
	@Column(name="FILE_NUMBER",length=60)
	private String fileNumber;
	/**
	 * 联合文号
	 */
	@Column(name="JOINT_DOC_NUMBER",length=60)
	private String jointDocNumber;
	/**
	 * 文件去向
	 */
	@Column(name="FILE_WHEREABOUTS",length=100)
	private String fileWhereabouts;
	/**
	 * 办理情况
	 */
	@Column(name="HANDLE_SITUATION",length=100)
	private String handleSituation;
	/**
	 * 密级和保密期限
	 */
	@Column(name="SECURITY_GRADE")
	private String securityGrade;
	/**
	 * 紧急程度
	 */
	@Column(name="URGENCY_DEGREE")
	private String urgencyDegree;
	/**
	 * 主题词(关键字)
	 */
	@Column(length=200)
	private String keyword;
	/**
	 * 份数
	 */
	private String copies;
	/**
	 * 任职领导
	 */
	@Column(name="RZLEADER")
	private String rzLeader;
	/**
	 * 主责单位
	 */
	@Column(name="ZEUNIT")
	private String zeUnit;
	/**
	 * 拟任职务
	 */
	@Column(name="TOJOB")
	private String tojob;
	/**
	 * 序号范围（开始）
	 */
	@Column(name="NUMBER_RANGE_START")
	private String numberRangeStart;
	/**
	 * 序号范围（结束）
	 */
	@Column(name="NUMBER_RANGE_END")
	private String numberRangeEnd;
	/**
	 * 完成期限
	 */
	private String deadline;
	
	private String sameNumTit;
	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;
	/**
	 * 登记人
	 */
	private String registrar;
	/**
	 * 联合发文
	 */
	@Column(name="JOINTLYFW")
	private String jointlyFw;
	/**
	 * 登记时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REGISTER_TIME")
	private Date registerTime; 
	/**
	 * 原文件
	 */
	@OrderBy("ordinal ASC")
	@OneToMany(mappedBy="fileReg",cascade=CascadeType.ALL)
	private List<OriginalFile> originalFiles = new ArrayList<OriginalFile>(0);
	
	/**
	 * 呈送信息
	 */
	@OneToMany(mappedBy="fileReg",cascade=CascadeType.ALL)
	private List<File_Handle> fileHandle = new ArrayList<File_Handle>(0);
	
	 /**
	 * 领导批示信息
	 */
	@OneToMany(mappedBy="fileReg",cascade=CascadeType.ALL)
	private List<File_Leader> fileLeader = new ArrayList<File_Leader>(0);
	
	/**
	 * 转送单位信息
	 */
	@OneToMany(mappedBy="fileReg",cascade=CascadeType.ALL)
	private List<File_Transfer> fileTransfer = new ArrayList<File_Transfer>(0);
	
	/**
	 * 单位分发信息
	 */
	@OneToMany(mappedBy="fileReg",cascade=CascadeType.ALL)
	private List<FileDispense> fileDispense = new ArrayList<FileDispense>(0);
	
	/**
	 * 注办信息
	 */
	@OrderBy("id DESC")
	@OneToMany(mappedBy="fileReg",cascade=CascadeType.ALL)
	private List<ProcessRecord> processRecord = new ArrayList<ProcessRecord>(0);
	
	/*办理状态*/
	@Column(name="HANDLE_STATUS")
	private String handlestatus;
	
	/*是否阅退件 市委市政府文件和大范围分发有回收*/
	@Column(name="IFBACK")
	private String ifback;
	
	/*分工类别*/
	@Column(name="FGLB")
	private String fglb;
	
	/*条码信息*/
	@Column(name="LABEL_INFO")
	private String labelInfo;
	
	/*原条码信息*/
	@Column(name="OLD_LABEL_INFO")
	private String oldLabelInfo;
	
	/**
	 * 条码创建人
	 */
	@Column(name="BC_CREATER")
	private String bcCreater;
	/**
	 * 条码创建人联系电话
	 */
	@Column(name="BC_LXDH")
	private String bcLxdh;
	
	/**
	 * 上级领导批示
	 */
	@OrderBy("id DESC")
	@OneToMany(mappedBy="fileReg",cascade=CascadeType.ALL)
	private List<SuperLeaderCommand> slcList = new ArrayList<SuperLeaderCommand>(0);
	
	//constructors
	public FileReg() {
		super();
	}
	
	public FileReg(Long id) {
		super();
		this.id = id;
	}

	//getters and setters
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
	public String getBarNo() {
		return barNo;
	}
	public void setBarNo(String barNo) {
		this.barNo = barNo;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFormUnit() {
		return formUnit;
	}
	public void setFormUnit(String formUnit) {
		this.formUnit = formUnit;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getSecurityGrade() {
		return securityGrade;
	}
	public void setSecurityGrade(String securityGrade) {
		this.securityGrade = securityGrade;
	}

	public String getUrgencyDegree() {
		return urgencyDegree;
	}

	public void setUrgencyDegree(String urgencyDegree) {
		this.urgencyDegree = urgencyDegree;
	}
	

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getCopies() {
		return copies;
	}

	public void setCopies(String copies) {
		this.copies = copies;
	}

	public String getNumberRangeStart() {
		return numberRangeStart;
	}

	public void setNumberRangeStart(String numberRangeStart) {
		this.numberRangeStart = numberRangeStart;
	}
	
	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public String getSameNumTit() {
		return sameNumTit;
	}

	public void setSameNumTit(String sameNumTit) {
		this.sameNumTit = sameNumTit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<OriginalFile> getOriginalFiles() {
		return originalFiles;
	}

	public void setOriginalFiles(List<OriginalFile> originalFiles) {
		this.originalFiles = originalFiles;
	}
	
	public List<File_Handle> getFileHandle() {
		return fileHandle;
	}

	public void setFileHandle(List<File_Handle> fileHandle) {
		this.fileHandle = fileHandle;
	}
	
	public List<File_Leader> getFileLeader() {
		return fileLeader;
	}

	public void setFileLeader(List<File_Leader> fileLeader) {
		this.fileLeader = fileLeader;
	}

	public String getHandlestatus() {
		return handlestatus;
	}

	public void setHandlestatus(String handlestatus) {
		this.handlestatus = handlestatus;
	}

	public String getIfback() {
		return ifback;
	}

	public void setIfback(String ifback) {
		this.ifback = ifback;
	}

	public String getRegistrar() {
		return registrar;
	}

	public void setRegistrar(String registrar) {
		this.registrar = registrar;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getFglb() {
		return fglb;
	}

	public void setFglb(String fglb) {
		this.fglb = fglb;
	}

	public String getJointlyFw() {
		return jointlyFw;
	}

	public void setJointlyFw(String jointlyFw) {
		this.jointlyFw = jointlyFw;
	}

	public String getRzLeader() {
		return rzLeader;
	}

	public void setRzLeader(String rzLeader) {
		this.rzLeader = rzLeader;
	}

	public String getZeUnit() {
		return zeUnit;
	}

	public void setZeUnit(String zeUnit) {
		this.zeUnit = zeUnit;
	}

	public String getTojob() {
		return tojob;
	}

	public void setTojob(String tojob) {
		this.tojob = tojob;
	}

	public String getLeadershipPS() {
		return leadershipPS;
	}

	public void setLeadershipPS(String leadershipPS) {
		this.leadershipPS = leadershipPS;
	}

	public void setNumberRangeEnd(String numberRangeEnd) {
		this.numberRangeEnd = numberRangeEnd;
	}

	public String getNumberRangeEnd() {
		return numberRangeEnd;
	}

	public void setHandleSituation(String handleSituation) {
		this.handleSituation = handleSituation;
	}

	public String getHandleSituation() {
		return handleSituation;
	}

	public void setFileWhereabouts(String fileWhereabouts) {
		this.fileWhereabouts = fileWhereabouts;
	}

	public String getFileWhereabouts() {
		return fileWhereabouts;
	}

	public void setJointDocNumber(String jointDocNumber) {
		this.jointDocNumber = jointDocNumber;
	}

	public String getJointDocNumber() {
		return jointDocNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setMainLeadershipPS(String mainLeadershipPS) {
		this.mainLeadershipPS = mainLeadershipPS;
	}

	public String getMainLeadershipPS() {
		return mainLeadershipPS;
	}

	public void setFileTransfer(List<File_Transfer> fileTransfer) {
		this.fileTransfer = fileTransfer;
	}

	public List<File_Transfer> getFileTransfer() {
		return fileTransfer;
	}

	public void setFileDispense(List<FileDispense> fileDispense) {
		this.fileDispense = fileDispense;
	}

	public List<FileDispense> getFileDispense() {
		return fileDispense;
	}

	public void setProcessRecord(List<ProcessRecord> processRecord) {
		this.processRecord = processRecord;
	}

	public List<ProcessRecord> getProcessRecord() {
		return processRecord;
	}

	public void setPhonePerson(String phonePerson) {
		this.phonePerson = phonePerson;
	}

	public String getPhonePerson() {
		return phonePerson;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setHandleOperator(String handleOperator) {
		this.handleOperator = handleOperator;
	}

	public String getHandleOperator() {
		return handleOperator;
	}

	public void setHandleUnit(String handleUnit) {
		this.handleUnit = handleUnit;
	}

	public String getHandleUnit() {
		return handleUnit;
	}

	public String getLabelInfo() {
		return labelInfo;
	}

	public void setLabelInfo(String labelInfo) {
		this.labelInfo = labelInfo;
	}

	public String getOldLabelInfo() {
		return oldLabelInfo;
	}

	public void setOldLabelInfo(String oldLabelInfo) {
		this.oldLabelInfo = oldLabelInfo;
	}

	public String getBcCreater() {
		return bcCreater;
	}

	public void setBcCreater(String bcCreater) {
		this.bcCreater = bcCreater;
	}

	public String getBcLxdh() {
		return bcLxdh;
	}

	public void setBcLxdh(String bcLxdh) {
		this.bcLxdh = bcLxdh;
	}

	public List<SuperLeaderCommand> getSlcList() {
		return slcList;
	}

	public void setSlcList(List<SuperLeaderCommand> slcList) {
		this.slcList = slcList;
	}

}
