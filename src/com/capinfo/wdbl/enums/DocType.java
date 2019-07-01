package com.capinfo.wdbl.enums;
/**
 * 
 * [文件类型]
 *
 */
public enum DocType {
	/**
	 * 1.市委市政府正式文件
	 */
	CITY_GOV_DOC,
	/**
	 * 2.大范围分发
	 */
	WIDE_POST_DOC,
	/**
	 * 3.电话记录
	 */
	TEL_RCD_DOC,
	/**
	 * 4.公安部正式文件 MPS(Ministry of Public Security)
	 */
	MPS_DOC,
	/**
	 * 5.局长批示件 (chief approval doc)
	 */
	CHIEF_APRV_DOC,
	/**
	 * 6.局内文件 (bureau inside doc)
	 */
	BUR_INSIDE_DOC,
	/**
	 * 7.领导交办件 (leader assigned doc)
	 */
	LEADER_ASND_DOC,
	/**
	 * 8.领导兼职 (parttime leader doc)
	 */
	PT_LEADER_DOC,
	/**
	 * 9.直接转文
	 */
	DIRECT_POST_DOC,
	/**
	 * 10.亲启件
	 */
	PRIVATE_DOC,
	/**
	 * 11.其他
	 */
	OTHER_DOC,
	/**
	 * 12.主要领导文件
	 */
	MAIN_LEARDER_DOC,
	/**
	 * 13.密码电报
	 */
	CIPHER_TELEGRAPH_DOC,
	
	/**
	 * 14.上级领导批示件
	 */
	SUPER_LEADERS,
	
	/**
	 * 15.批示抄清
	 */
	INSTRUCTIONS_DOC;
}