package com.capinfo.pub.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubExternunitinfo entity. @author MyEclipse Persistence Tools
 * 外部联系单位信息
 */
@Entity
@Table(name = "PUB_EXTERNUNITINFO", schema = "WDBL")
public class PubExternUnitInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 171785482916278326L;
	// Fields

	private Long id;
	/*private String name;//姓名
	private String job;//职务
	private String phone;//联系电话*/
	private String unit;//单位名称
	private String unitCall;//单位电话
	private String unitIntroduce;//单位简介
	private String unitnumber;
	private Integer dis_num;		/*显示顺序*/

	// Constructors

	/** default constructor */
	public PubExternUnitInfo() {
	}

	/** full constructor */
	public PubExternUnitInfo(String unit, String unitCall, String unitIntroduce,String unitnumber) {
		this.unitIntroduce = unitIntroduce;
		this.unit = unit;
		this.unitCall = unitCall;
		this.unitnumber = unitnumber;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "externUnitId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_EXTERNUNITINFO") })
	@GeneratedValue(generator = "externUnitId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "UNIT", length = 200)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "UNIT_CALL", length = 20)
	public String getUnitCall() {
		return this.unitCall;
	}

	public void setUnitCall(String unitCall) {
		this.unitCall = unitCall;
	}

	@Column(name = "UNIT_INTRODUCE", length = 500)
	public void setUnitIntroduce(String unitIntroduce) {
		this.unitIntroduce = unitIntroduce;
	}

	public String getUnitIntroduce() {
		return unitIntroduce;
	}

	public String getUnitnumber() {
		return unitnumber;
	}
	@Column(name="UNITNUMBER",length=20)
	public void setUnitnumber(String unitnumber) {
		this.unitnumber = unitnumber;
	}
	@Column(name = "dis_num")
	public Integer getDis_num() {
		return dis_num;
	}
	public void setDis_num(Integer dis_num) {
		this.dis_num = dis_num;
	}

}