package com.capinfo.wdbl.action.file_process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.pub.bean.PubExternUnitInfo;
import com.capinfo.security.userdetails.UserDetailsHelper;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.ProcessRecord;
import com.capinfo.wdbl.bean.file_process.FileDiapensesNew;
import com.capinfo.wdbl.bean.file_process.FileDispense;
import com.capinfo.wdbl.bean.file_process.NewFileDiapense;
import com.capinfo.wdbl.service.FileRegService;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 分送单位
 */
@Controller  
@Scope("prototype")
@RequestMapping("/filedispense")
public class File_DispenseController {
public static Logger log = Logger.getLogger(File_DispenseController.class);
	

	@Autowired
	private FileRegService fileRegService;
	@Autowired
	private BaseService baseService;

	/**
	 * 跳转到大范围文件页面
	 * @return
	 */
	@RequestMapping("/showWideList.do")
	public String wideList(){
		return "tra_file/wideList";
	}
	
	/**列表加载数据*/
	@RequestMapping(value="/wideList.do",method = RequestMethod.POST)  
	public @ResponseBody GridJsonBean wideList1(PageBean p, @RequestParam Map<String,Object> paramMap) {
		GridJsonBean grid = null;
		Search search = new Search(FileReg.class);
		SearchResult<FileReg> result = null;
		try {
			processParamsFilter(paramMap, search);
			if(!StringUtils.isEmpty(p.getDir()) && !StringUtils.isEmpty(p.getSort())){
				search.addSort(p.getSort(), p.getDir().toLowerCase().equals("desc"));
			}else{
				search.addSort("receiveTime", true);
			}
			search.addFilterAnd(new Filter("docType","2",Filter.OP_EQUAL));
			result = fileRegService.listData(search);
			List<FileReg> fileReglist = result.getResult();
			grid = processingResult(fileReglist,p,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return grid;
	}
	/**
	 * 结果处理
	 * @param fileReglist
	 * @param p
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private GridJsonBean  processingResult(List<FileReg> fileReglist,PageBean p,Map<String,Object> paramMap) throws Exception{
		GridJsonBean grid = new GridJsonBean();
		List<FileDiapensesNew> diapensesNews = new ArrayList<FileDiapensesNew>();
		for (FileReg fileReg : fileReglist) {
			List<FileDispense> fileDispenses = fileReg.getFileDispense();
			for (int i = 0; i < fileDispenses.size(); i++) {
				FileDiapensesNew diapensesNew = new FileDiapensesNew();
				diapensesNew.setBarNo(fileReg.getBarNo());
				diapensesNew.setCopyNum(fileDispenses.get(i).getCopyNum());
				diapensesNew.setId(fileDispenses.get(i).getId());
				diapensesNew.setDocNumber(fileReg.getDocNumber());
				diapensesNew.setUnit(fileDispenses.get(i).getUnit());
				diapensesNew.setTitle(fileReg.getTitle());
				diapensesNew.setNumbers(fileDispenses.get(i).getNumbers());
				diapensesNew.setIsRecovery(fileDispenses.get(i).getIsRecovery());
				List<ProcessRecord> processRecords = fileReg.getProcessRecord();
				for (ProcessRecord processRecord : processRecords) {
					if("分发".equals(processRecord.getHandlestatus())){
						diapensesNew.setSys_date(processRecord.getSys_date());
					}
				}
				String property=null,value=null; int compareTo=-1;
				boolean title = false, unit=false,barNo=false;
				if(!StringUtils.isEmpty((String) paramMap.get("title"))){
					unit = diapensesNew.getUnit().contains(String.valueOf(paramMap.get("title")));
				}
				if(!StringUtils.isEmpty((String) paramMap.get("title"))){
					barNo = diapensesNew.getBarNo().contains(String.valueOf(paramMap.get("title")));
				}
				if(!StringUtils.isEmpty((String) paramMap.get("title")) && !StringUtils.isEmpty(diapensesNew.getTitle())){
					title = diapensesNew.getTitle().contains(String.valueOf(paramMap.get("title")));
				}
				if(paramMap.containsKey(property="sys_date") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
					SimpleDateFormat df = new SimpleDateFormat("yyyy");
					compareTo = df.parse(diapensesNew.getSys_date()).compareTo(df.parse(value));
				}
				if(StringUtils.isEmpty(value=String.valueOf(paramMap.get("sys_date"))) && StringUtils.isEmpty(value=String.valueOf(paramMap.get("title")))){
					diapensesNews.add(diapensesNew);
				}else if(compareTo >= 0 && StringUtils.isEmpty(String.valueOf(paramMap.get("title")))){
					diapensesNews.add(diapensesNew);
				}else if(compareTo >= 0 && (unit || barNo || title)){
					diapensesNews.add(diapensesNew);
				}else if(StringUtils.isEmpty(String.valueOf(paramMap.get("sys_date"))) && (unit || barNo || title)){
					diapensesNews.add(diapensesNew);
				}
			}
		}
		for(int i = diapensesNews.size() - 1; i > 0; i--) {
		    FileDiapensesNew item = diapensesNews.get(i);
		    for(int j = i - 1; j >= 0; j--) {
		        if(diapensesNews.get(j).getId().equals(item.getId())) {
		        	diapensesNews.remove(i);
		            break;
		        }
		    }
		}

		List<FileDiapensesNew> diapensesNew = new ArrayList<FileDiapensesNew>();
		if(p.getStart()!=0){
			p.setLimit(diapensesNews.size());
		}
		if(diapensesNews.size() < p.getLimit()){
			p.setLimit(diapensesNews.size());
		}
		for (int i = p.getStart(); i < p.getLimit(); i++) {
			diapensesNew.add(diapensesNews.get(i));
		}
		grid = new GridJsonBean();
		grid.setRows(diapensesNew);
		grid.setTotalrows(diapensesNews.size());
		return grid;
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
		if(paramMap.containsKey(property="title") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			fs.add(new Filter(property, "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("barNo", "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("docNumber", "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("title", "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("fileDispense.unit", "%"+value+"%",Filter.OP_ILIKE));
		}
		if(paramMap.containsKey(property="sys_date") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			fs.add(new Filter("receiveTime", value,Filter.OP_GREATER_OR_EQUAL));
		}
		Filter[] fa = new Filter[fs.size()];
		for(int i = 0;i<fs.size();i++){
			fa[i] = fs.get(i);
		}
		search.addFilterOr(fa);
	}
	
	/**表单保存*/
	@RequestMapping(value="/add.do")  
	@ResponseBody 
	public Object addSave(@RequestBody List<NewFileDiapense> list,@RequestParam(value="id",required=true) Long id){
		SaveJsonBean result = new SaveJsonBean();
		try{
			FileReg info = baseService.getObject(FileReg.class, id);
			if(list == null || list.size() == 0)
			{
				return null;
			}
			if(info.getFileDispense() != null){
				List<FileDispense> fList = new ArrayList<FileDispense>();
				Iterator<FileDispense> iter = info.getFileDispense().iterator();
				while(iter.hasNext()){
					FileDispense f = iter.next();
					f.setFileReg(null);
					fList.add(f);
					iter.remove();
				}
				baseService.deleteAll(fList);
			}
			for (NewFileDiapense fileDispense : list) {
				if(StringUtils.isNotEmpty(fileDispense.getCopyNumA()) && !StringUtils.equals(fileDispense.getCopyNumA(), "0")){
					FileDispense entityA = new FileDispense();
					entityA.setFileReg(info);
					entityA.setIsRecovery(fileDispense.getIsRecoveryA());
					entityA.setCopyNum(fileDispense.getCopyNumA());
					entityA.setNumbers(fileDispense.getNumbersA());
					entityA.setUnit(fileDispense.getUnitA());
					baseService.saveObject(entityA);
				}
				if(StringUtils.isNotEmpty(fileDispense.getCopyNumB()) && !StringUtils.equals(fileDispense.getCopyNumB(), "0")){
					FileDispense entityB = new FileDispense();
					entityB.setFileReg(info);
					entityB.setIsRecovery(fileDispense.getIsRecoveryB());
					entityB.setCopyNum(fileDispense.getCopyNumB());
					entityB.setNumbers(fileDispense.getNumbersB());
					entityB.setUnit(fileDispense.getUnitB());
					baseService.saveObject(entityB);
				}
			}
			ProcessRecord processRecord = new ProcessRecord();
			processRecord.setHandlestatus("分发");
			processRecord.setHandlecontent("做了分发操作");
			processRecord.setApprovalOp(info.getLeadershipPS());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			processRecord.setSys_date(df.format(new Date()));
			processRecord.setSys_user(UserDetailsHelper.getUser().getName());
			processRecord.setAgent(UserDetailsHelper.getUser().getName());
			processRecord.setFileReg(info);
			baseService.saveObject(processRecord);
			result.setSuccess(true);
			result.setMsg(ConstantInfo.saveSuccessMsg);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
	    return result; 
	} 
	/**列表加载数据*/
	@RequestMapping(value="/listLoad.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoad(@RequestParam(value="id",required=true) Long id,@RequestParam(value="unitNumber",required=true) String unit_number){
		GridJsonBean grid = null;
		FormLoadJsonBean<FileDispense> reslut = new FormLoadJsonBean<FileDispense>();
		try{

			FileReg fileReg = baseService.getObject(FileReg.class, id);
			List<NewFileDiapense> dislist = new ArrayList <NewFileDiapense>();
//			List<PubExternUnitInfo> publist = baseService.loadAll(PubExternUnitInfo.class);
			PubExternUnitInfo pubExternUnitInfo = new PubExternUnitInfo();
			pubExternUnitInfo.setUnitnumber(unit_number);
			List<PubExternUnitInfo> publist = baseService.getByProperty(PubExternUnitInfo.class, pubExternUnitInfo, null);
			int number = Integer.parseInt(fileReg.getNumberRangeStart(),10);
			int numberEnd = Integer.parseInt(fileReg.getNumberRangeEnd(),10);
			List<FileDispense> list = fileReg.getFileDispense();
			Map<String,FileDispense> oldMap = new HashMap<String,FileDispense>();
			for(FileDispense f:list){
				oldMap.put(f.getUnit(), f);
			}
			for (int i=0;i< publist.size();i+=2) {
					NewFileDiapense dispense = new NewFileDiapense();
					dispense.setIdA("A");
					String unit = publist.get(i).getUnit();
					dispense.setUnitA(unit);
					if(oldMap.containsKey(unit)){ //已有数据，提取
						dispense.setCopyNumA(oldMap.get(unit).getCopyNum());
						dispense.setNumbersA(oldMap.get(unit).getNumbers());
						number += Integer.parseInt(oldMap.get(unit).getCopyNum());
					}else{                       //没有数据
						if(number<numberEnd) //当有可分配的文号时
						{
							dispense.setCopyNumA("1");
							dispense.setNumbersA(String.valueOf(number));
							number += 1;
						}else{               //没有文号置空
							
							dispense.setCopyNumA("0");
							dispense.setNumbersA("");
						}
					}
					
					
					dispense.setIsRecoveryA("未回收");
					if(i+1<publist.size()){
							unit = publist.get(i+1).getUnit();
							
							dispense.setIdB("B");
							dispense.setUnitB(unit);
							if(oldMap.containsKey(unit)){
								dispense.setCopyNumB(oldMap.get(unit).getCopyNum());
								dispense.setNumbersB(oldMap.get(unit).getNumbers());
								number += Integer.parseInt(oldMap.get(unit).getCopyNum());
							}else{
								if(number<numberEnd) //当有可分配的文号时
								{
									dispense.setCopyNumB("1");
									dispense.setNumbersB(String.valueOf(number));
									number += 1; 
								}else{
									dispense.setCopyNumB("0");
									dispense.setNumbersB(String.valueOf(""));
								}
							}
							
							dispense.setIsRecoveryB("未回收");
					}
					dislist.add(dispense);
				
			}
			grid = new GridJsonBean();
			grid.setRows(dislist);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}  
	
	/**修改份数加载生成号码*/
	@RequestMapping(value="/updateListLoad.do")  
	@ResponseBody 
	public Object updateListLoad(@RequestBody List<NewFileDiapense> list,@RequestParam(value="id",required=true) Long id,@RequestParam(value="rows",required=true) int rows){
		GridJsonBean grid = null;
		//FormLoadJsonBean<File_Dispense> reslut = new FormLoadJsonBean<File_Dispense>();
		try{
			FileReg fileReg = baseService.getObject(FileReg.class, id);
			int start = Integer.parseInt(fileReg.getNumberRangeStart()) - 1;
			int end = Integer.parseInt(fileReg.getNumberRangeEnd(),10);
			for (int i=0;i<list.size();i++) {
				//取份数
				int fs = Integer.parseInt(list.get(i).getCopyNumA(),10);
				String  hm = "";
				int copyNumA = 0;
				for(int j = 1; j<=fs;j++){
					if(start < end){
						start = start+1;
						hm += (start +",");
					}else{
						break;
					}
					copyNumA++;
				}
				if(hm.endsWith(","))
					hm = hm.substring(0, hm.lastIndexOf(","));
				
				list.get(i).setNumbersA(hm);
				list.get(i).setCopyNumA(""+copyNumA);
				
				if(list.get(i).getCopyNumB() != null){
					int fsB = Integer.parseInt(list.get(i).getCopyNumB(),10);
					String  hmB = "";
					int copyNumB = 0;
					for(int j = 1; j<=fsB;j++){
						if(start < end){
							start = start+1;
							hmB += (start +",");
						}else{
							break;
						}
						copyNumB++;
					}
					if(hmB.endsWith(","))
						hmB = hmB.substring(0, hmB.lastIndexOf(","));
					list.get(i).setCopyNumB(""+copyNumB);
					list.get(i).setNumbersB(hmB);
				}
			}
			grid = new GridJsonBean();
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}  
	
	

	/**根据主表id加载数据*/
	@RequestMapping(value="/searchIdlistLoad.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object searchIdListLoad(@RequestParam(value="id",required=true) Long id){
		GridJsonBean grid = new GridJsonBean();
		try{
			FileReg info = baseService.getObject(FileReg.class, id);
			List<FileDispense> list = info.getFileDispense();
			grid.setTotalrows(list.size());
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	} 
	
	/**
	 * 回收
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/hsUnit.do", method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean hsUnit(Long[] ids) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			if (ids != null && ids.length > 0) {
				for (Long id : ids) {
					FileDispense info = baseService.getObject(FileDispense.class, id);
					info.setIsRecovery("已回收");
					baseService.saveObject(info);
				}
				FileDispense info = baseService.getObject(FileDispense.class, ids[0]);
				ProcessRecord processRecord = new ProcessRecord();
				processRecord.setHandlestatus("回收");
				processRecord.setHandlecontent("做了回收操作");
				processRecord.setApprovalOp(info.getFileReg().getLeadershipPS());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				processRecord.setSys_date(df.format(new Date()));
				processRecord.setSys_user(UserDetailsHelper.getUser().getName());
				processRecord.setAgent(UserDetailsHelper.getUser().getName());
				processRecord.setFileReg(info.getFileReg());
				baseService.saveObject(processRecord);
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			} else {
				result.setSuccess(true);
				result.setMsg(ConstantInfo.noCheckedMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}
	
	/**
	 * 取消回收
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/unHsUnit.do", method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean unHsUnit(Long[] ids) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			if (ids != null && ids.length > 0) {
				for (Long id : ids) {
					FileDispense info = baseService.getObject(FileDispense.class, id);
					info.setIsRecovery("未回收");
					baseService.saveObject(info);
				}
				FileDispense info = baseService.getObject(FileDispense.class, ids[0]);
				ProcessRecord processRecord = new ProcessRecord();
				processRecord.setHandlestatus("取消回收");
				processRecord.setHandlecontent("做了取消回收操作");
				processRecord.setApprovalOp(info.getFileReg().getLeadershipPS());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				processRecord.setSys_date(df.format(new Date()));
				processRecord.setSys_user(UserDetailsHelper.getUser().getName());
				processRecord.setAgent(UserDetailsHelper.getUser().getName());
				processRecord.setFileReg(info.getFileReg());
				baseService.saveObject(processRecord);
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			} else {
				result.setSuccess(true);
				result.setMsg(ConstantInfo.noCheckedMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}
}
