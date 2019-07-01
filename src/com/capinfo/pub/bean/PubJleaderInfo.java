package com.capinfo.pub.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubJleaderinfo entity. @author MyEclipse Persistence Tools
 * 局领导信息表
 */
@Entity
@Table(name = "PUB_JLEADERINFO", schema = "WDBL")
public class PubJleaderInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7004116200128371951L;
	// Fields

	private Long id;
	private String name;//姓名
	private String job;//职务
	private String cityPhone;//市电
	private String specialPhone;//专电
	private String redPhone;//红机
	private String phone;//手机
	private String address;//住址
	private String carNum;//车号
	private String driverPhone;//司机电话
	private String driverUnit;//司机单位
	private Integer dis_num;		/*显示顺序*/

	// Constructors

	/** default constructor */
	public PubJleaderInfo() {
	}

	/** full constructor */
	public PubJleaderInfo(String name, String job, String cityPhone,
			String specialPhone, String redPhone, String phone, String address,
			String carNum, String driverPhone, String driverUnit) {
		this.name = name;
		this.job = job;
		this.cityPhone = cityPhone;
		this.specialPhone = specialPhone;
		this.redPhone = redPhone;
		this.phone = phone;
		this.address = address;
		this.carNum = carNum;
		this.driverPhone = driverPhone;
		this.driverUnit = driverUnit;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "jleaderId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_JLEADERINFO") })
	@GeneratedValue(generator = "jleaderId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 10)
	public String getName() {
		return this.name;
	}    

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "JOB", length = 10)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "CITY_PHONE", length = 11)
	public String getCityPhone() {
		return this.cityPhone;
	}

	public void setCityPhone(String cityPhone) {
		this.cityPhone = cityPhone;
	}

	@Column(name = "SPECIAL_PHONE", length = 11)
	public String getSpecialPhone() {
		return this.specialPhone;
	}

	public void setSpecialPhone(String specialPhone) {
		this.specialPhone = specialPhone;
	}

	@Column(name = "RED_PHONE", length = 11)
	public String getRedPhone() {
		return this.redPhone;
	}

	public void setRedPhone(String redPhone) {
		this.redPhone = redPhone;
	}

	@Column(name = "PHONE", length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CAR_NUM", length = 20)
	public String getCarNum() {
		return this.carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	@Column(name = "DRIVER_PHONE", length = 11)
	public String getDriverPhone() {
		return this.driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	@Column(name = "DRIVER_UNIT", length = 200)
	public String getDriverUnit() {
		return this.driverUnit;
	}

	public void setDriverUnit(String driverUnit) {
		this.driverUnit = driverUnit;
	}
	
	@Column(name = "dis_num")
	public Integer getDis_num() {
		return dis_num;
	}
	
	public void setDis_num(Integer dis_num) {
		this.dis_num = dis_num;
	}

}