var WDBL$Globals = {
	/**
	 * 允许上传的文件最大数量
	 * 
	 * @type Number
	 */
	MAX_ALLOWED_UPLOAD_FILE_SIZE : 5,
	/**
	 * 文件类型名称
	 * @type 
	 */
	DocTypeNames:['','CITY_GOV_DOC','WIDE_POST_DOC','TEL_RCD_DOC','MPS_DOC','CHIEF_APRV_DOC',
		'BUR_INSIDE_DOC','LEADER_ASND_DOC','PT_LEADER_DOC','DIRECT_POST_DOC','PRIVATE_DOC','OTHER_DOC','MAIN_LEARDER_DOC','CIPHER_TELEGRAPH_DOC'],
	HandleStatus : {
		/**
		 * 0.已登记
		 * 
		 * @type Number
		 */
		REGISTERED : 0,
		/**
		 * 1.已发送拟办
		 * 
		 * @type Number
		 */
		SENT_ADVICE : 1,
		/**
		 * 2.已拟办
		 * 
		 * @type Number
		 */
		ADVICED : 2,
		/**
		 * 3.待阅示
		 * 
		 * @type Number
		 */
		WAIT_AUDIT : 3,
		/**
		 * 4.已批示
		 * 
		 * @type Number
		 */
		AUDITED : 4,
		/**
		 * 5.已发送
		 * 
		 * @type Number
		 */
		SENT : 5,
		/**
		 * 6.已办结
		 * 
		 * @type Number
		 */
		FINISHED : 6
	},
	HandleStatusLable : ['已登记', '已发送拟办', '已拟办', '待阅示', '已批示', '已发送', '已办结'],
	DocTypeEditForms:['BaseEditForm','GovEditForm','WideEditForm','PhoneEditForm','MpsEditForm',
	'InstructionsEditForm','InsideEditForm','LeaderEditForm','PtLeaderEditForm','TransferEditForm',
	'PrivateEditForm','OtherEditForm','MainLearderEditForm','CipherTelegraphEditForm','SuperLeadersEditForm']
}