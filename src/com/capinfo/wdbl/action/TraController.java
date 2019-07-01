package com.capinfo.wdbl.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.security.userdetails.UserDetailsHelper;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.api.FileManager;
import com.capinfo.wdbl.api.SerialNumberManager;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.OriginalFile;
import com.capinfo.wdbl.bean.ProcessRecord;
import com.capinfo.wdbl.bean.file_process.SuperLeaderCommand;
import com.capinfo.wdbl.service.FileRegService;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FileValidationUtils;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * [文件办理Controller]
 * 
 */
@Controller
@RequestMapping("tra_file")
public class TraController {
	
	private static final Log log = LogFactory.getLog(RegController.class);
	@Autowired
	@Qualifier("baseService")
	private BaseService service;
	@Autowired
	private FileRegService fileRegService;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private SerialNumberManager serialNumberManager;
	@Autowired
	private FileValidationUtils validationUtils;

	/**
	 * 跳转到展示文件办理列表
	 * 
	 * @param docTypeName
	 * 
	 * @return
	 */
	@RequestMapping("/showList.do")
	public String showList() {
		return "tra_file/transact";
	}
	
	/**
	 * 根据文件类型跳转页面
	 * 
	 * @return
	 */
	@RequestMapping("/jumpList.do")
	public String jumpList() {
		return "tra_file/jumpList";
	}
	
	
	@RequestMapping(value = "/listLoad.do", method = RequestMethod.POST)
	public @ResponseBody GridJsonBean listLoad(PageBean p, @RequestParam Map<String,Object> paramMap) {
		Search search = new Search(FileReg.class);
		SearchResult<FileReg> result = null;
		try {
			processParamsFilter(paramMap, search);
			if(!StringUtils.isEmpty(p.getDir()) && !StringUtils.isEmpty(p.getSort())){
				search.addSort(p.getSort(), p.getDir().toLowerCase().equals("desc"));
			}else{
				search.addSort("receiveTime", true);
			}
			
			search.addFilterAnd(new Filter("handlestatus","登记",Filter.OP_NOT_EQUAL),new Filter("handlestatus","办结",Filter.OP_NOT_EQUAL),new Filter("handlestatus","系统办结",Filter.OP_NOT_EQUAL));
			search.setFirstResult(p.getStart());
			search.setMaxResults(p.getLimit());
			result = fileRegService.listData(search);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return new GridJsonBean().valueOf(result);
	}
	
	/**
	 * 处理查询参数
	 * @param paramMap
	 * @param search
	 */
	private void processParamsFilter(Map<String, Object> paramMap, Search search) {
		if (paramMap.isEmpty() || search == null || search.getSearchClass() == null){
			return;
		}
		List<Filter>  fs = new ArrayList<Filter>();
		String property=null,value=null;
		if(paramMap.containsKey(property="docType") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			search.addFilter(new Filter(property,value));
		}
		
		if(paramMap.containsKey(property="title") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			fs.add(new Filter(property, "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("barNo", "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("formUnit", "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("docNumber", "%"+value+"%",Filter.OP_ILIKE));
		}
		if(paramMap.containsKey(property="iTitle") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			fs.add(new Filter("title", value,Filter.OP_EQUAL));
		}
		if(paramMap.containsKey(property="barNo") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			if(value.startsWith("GA1101")) {//识别内部编码，非内部编码不进行查询
				//fs.add(new Filter(property, value,Filter.OP_EQUAL));
				search.addFilterAnd(new Filter(property, value,Filter.OP_EQUAL) );
				return;
			}else{
				fs.add(new Filter(property, value,Filter.OP_EQUAL));
			}
		}
		if(paramMap.containsKey(property="labelInfo") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			value = StringUtils.stripEnd(value, "\r\n");
			fs.add(new Filter(property, value,Filter.OP_EQUAL));
			fs.add(new Filter("oldLabelInfo", value,Filter.OP_EQUAL));
		}
		if(paramMap.containsKey(property="docNumber") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			if(!value.equals("﹝﹞"))
				fs.add(new Filter(property, value,Filter.OP_EQUAL));
		}
		Filter[] fa = new Filter[fs.size()];
		for(int i = 0;i<fs.size();i++){
			fa[i] = fs.get(i);
		}
		search.addFilterOr(fa);
	}
	
	/**
	 * 加载文件登记信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/loadBean.do", method = RequestMethod.POST)
	public @ResponseBody FormLoadJsonBean<FileReg> loadBean(@RequestParam(value = "id", required = true) Long id) {
		FormLoadJsonBean<FileReg> result = new FormLoadJsonBean<FileReg>();
		try {
			FileReg bean = service.getObject(FileReg.class, id);
			result.setSuccess(true);
			result.setData(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 保存修改文件登记信息
	 * 
	 * @param docTypeName
	 * @param fileReg
	 * @return
	 */
	@RequestMapping(value = "/editSave.do", method = RequestMethod.POST)
	public @ResponseBody
	SaveJsonBean editSave(String docTypeName, FileReg fileReg,String sessionID,HttpSession session,HttpServletRequest request) {
		try {
			FileReg reg = service.getObjectWithProperties(FileReg.class, fileReg.getId(),"originalFiles");
			Assert.notNull(reg, "未找到文件注册信息");
			if(reg == null){
				return new SaveJsonBean(false, "未找到文件注册信息");
			}
			List<OriginalFile> list = (List<OriginalFile>)session.getAttribute(sessionID);
			if(list != null && list.size() > 0){
				for (OriginalFile file : list) {
					file.setId(null);
					file.setFileReg(reg);
				}
			}
			
			
			//修改附件信息，去掉原有，再添加session中的所有
			fileReg.setOriginalFiles(null);
			if(reg.getSlcList() != null && reg.getSlcList().size()> 0){ //去除旧的领导批示
				List<SuperLeaderCommand> oldList = reg.getSlcList();
				for(SuperLeaderCommand slc:oldList){
					slc.setFileReg(null);
				}
				reg.setSlcList(null);
				fileRegService.deleteAll(oldList);
			}
			fileRegService.updateFileReg(reg);
			fileReg.setOriginalFiles(list);
			fileRegService.updateFileReg(fileReg);
			
			if(reg.getDocType().equals("14") || reg.getDocType().equals("12") || reg.getDocType().equals("13") || reg.getDocType().equals("7") || reg.getDocType().equals("9") || reg.getDocType().equals("5")){ //添加上级领导批示
				String[] leaderNames = request.getParameterValues("leaderName");
				String[] commandContents = request.getParameterValues("commandContent");
				if(leaderNames != null && leaderNames.length > 0){
					for(int i = 0; i<leaderNames.length;i++){
						SuperLeaderCommand slc = new SuperLeaderCommand();
						slc.setFileReg(fileReg);
						slc.setLeaderName(leaderNames[i]);
						slc.setCommandContent(commandContents[i]);
						fileReg.getSlcList().add(slc);
					}
				}
				service.saveOrUpdateAll(fileReg.getSlcList());
			}
			ProcessRecord processRecord = new ProcessRecord();
			processRecord.setHandlestatus("修改");
			processRecord.setHandlecontent("修改了"+fileReg.getTitle());
			processRecord.setApprovalOp(fileReg.getLeadershipPS());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			processRecord.setSys_date(df.format(new Date()));
			processRecord.setSys_user(UserDetailsHelper.getUser().getName());
			processRecord.setAgent(UserDetailsHelper.getUser().getName());
			processRecord.setFileReg(fileReg);
			service.saveObject(processRecord);
			return new SaveJsonBean(true, ConstantInfo.saveSuccessMsg);
		
		} catch (IllegalArgumentException iae) {
			return new SaveJsonBean(false, iae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return new SaveJsonBean(false, ConstantInfo.saveErrorMsg);
	}

	/**
	 * 删除文件登记信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean delete(Long[] ids) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			if (ids != null && ids.length > 0) {
				fileRegService.deleteFileReg(ids);
				result.setSuccess(true);
				result.setMsg(ConstantInfo.delSuccessMsg);
			} else {
				result.setSuccess(true);
				result.setMsg(ConstantInfo.noCheckedMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.delErrorMsg);
		}
		return result;
	}

}