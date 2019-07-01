package com.capinfo.meeting.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "history_meeting")
@SequenceGenerator(sequenceName = "seq_history_meeting", name = "seq_history_meeting")
public class History_Meeting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 592120181040621727L;
	private Long id; // 主键
	private Long meetingid;
	private String title; // 会议标题
	private String content; // 会议内容
	private String undertakeUnit; // 承办单位
	private String remark; // 备注
	private String contacter; // 联系人
	private String phonenum; // 联系电话
	private Integer typeJuge; // 类型判断(1、处理 2、转走)
	private Integer meetStatu; // 会议状态(1拟办中2已确定)
	private String forwardUnit; // 转走单位
	private Boolean isDeleted; // 删除状态(1、删除 0、未删除)
	private String oprUser; // 操作用户
	private String oprUserUnit; // 操作用户单位
	private String updateTime; // 更新时间
	private Integer oprstatus; // 操作状态(1新增、2修改、0删除等)

	private List<History_MeetingTime> timeList;
	private List<History_MeetingLeader> leaderList;
	private List<History_MeetingPerson> persons;
	private List<History_MeetingDoc> docList;


	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_history_meeting")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMeetingid() {
		return meetingid;
	}

	public void setMeetingid(Long meetingid) {
		this.meetingid = meetingid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUndertakeUnit() {
		return undertakeUnit;
	}

	public void setUndertakeUnit(String undertakeUnit) {
		this.undertakeUnit = undertakeUnit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public Integer getTypeJuge() {
		return typeJuge;
	}

	public void setTypeJuge(Integer typeJuge) {
		this.typeJuge = typeJuge;
	}

	public Integer getMeetStatu() {
		return meetStatu;
	}

	public void setMeetStatu(Integer meetStatu) {
		this.meetStatu = meetStatu;
	}

	public String getForwardUnit() {
		return forwardUnit;
	}

	public void setForwardUnit(String forwardUnit) {
		this.forwardUnit = forwardUnit;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getOprUser() {
		return oprUser;
	}

	public void setOprUser(String oprUser) {
		this.oprUser = oprUser;
	}

	public String getOprUserUnit() {
		return oprUserUnit;
	}

	public void setOprUserUnit(String oprUserUnit) {
		this.oprUserUnit = oprUserUnit;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getOprstatus() {
		return oprstatus;
	}

	public void setOprstatus(Integer oprstatus) {
		this.oprstatus = oprstatus;
	}

	public int hashCode() {
		return (this.id == null) ? 0 : this.id.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof History_Meeting) {
			final History_Meeting obj = (History_Meeting) object;
			return (this.id != null) ? this.id.equals(obj.id)
					: (obj.id == null);
		}
		return false;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "meeting")
	public List<History_MeetingTime> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<History_MeetingTime> timeList) {
		this.timeList = timeList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "meeting")
	public List<History_MeetingLeader> getLeaderList() {
		return leaderList;
	}

	public void setLeaderList(List<History_MeetingLeader> leaderList) {
		this.leaderList = leaderList;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "meeting")
	public List<History_MeetingPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<History_MeetingPerson> persons) {
		this.persons = persons;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "meeting")
	public List<History_MeetingDoc> getDocList() {
		return docList;
	}

	public void setDocList(List<History_MeetingDoc> docList) {
		this.docList = docList;
	}

}
