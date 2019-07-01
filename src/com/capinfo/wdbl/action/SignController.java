package com.capinfo.wdbl.action;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.base.service.BaseService;
import com.capinfo.security.userdetails.UserDetailsHelper;
import com.capinfo.util.DateUtil;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.FileSign;
import com.capinfo.wdbl.service.FileSignService;
import com.capinfo.wdbl.util.ConstantInfo;
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
@RequestMapping("sign_file")
public class SignController {
	
	private static final Log log = LogFactory.getLog(RegController.class);
	@Autowired
	@Qualifier("baseService")
	private BaseService service;
	@Autowired
	private FileSignService fileSignService;
	
	/**
	 * 跳转到展示文件列表
	 * 
	 * @param docTypeName
	 * 
	 * @return
	 */
	@RequestMapping("/showList.do")
	public String showList() {
		return "sign_file/list";
	}
	
	
	@RequestMapping(value = "/listLoad.do", method = RequestMethod.POST)
	public @ResponseBody GridJsonBean listLoad(PageBean p, @RequestParam Map<String,Object> paramMap) {
		Search search = new Search(FileSign.class);
		SearchResult<FileSign> result = null;
		try {
			processParamsFilter(paramMap, search);
			if(!StringUtils.isEmpty(p.getDir()) && !StringUtils.isEmpty(p.getSort())){
				search.addSort(p.getSort(), p.getDir().toLowerCase().equals("desc"));
			}else{
				search.addSort("id", true);
			}
			search.setFirstResult(p.getStart());
			search.setMaxResults(p.getLimit());
			result = fileSignService.list(search);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return new GridJsonBean().valueOf(result);
	}
	
	/**
	 * 
	 * @param p
	 * @param paramMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	/*@RequestMapping(value = "/{backTime}/exportXls.do")
	public ModelAndView  exportXls(@PathVariable String backTime) {
		ModelAndView model = new ModelAndView("sign_file/excel");
		Search search = new Search(FileSign.class);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("backTime", backTime);
		SearchResult<FileSign> result = null;
		try {
			processParamsFilter(paramMap, search);
			search.addSort("id", true);
			search.setFirstResult(0);
			search.setMaxResults(1000);
			result = fileSignService.list(search);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		model.addObject("data", result);
		model.addObject("backTime",backTime);
		return model;
	}*/
	
	@RequestMapping(value = "/{backTime}/exportXls.do")
	public void exportXls(@PathVariable String backTime, HttpServletResponse response) throws Exception {
		Search search = new Search(FileSign.class);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("backTime", backTime);
		processParamsFilter(paramMap, search);
		search.addSort("id", true);
		search.setFirstResult(0);
		search.setMaxResults(1000);
		
		String filename = new String(("退件登记列表").getBytes("GBK"),"ISO-8859-1");
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流  
        response.setHeader("Content-disposition", "attachment; filename="+filename+".xls");// 设定输出文件头  
        response.setContentType("application/msexcel");// 定义输出类型
		boolean daoChu = fileSignService.daoChu(backTime, search,os);
		 try {
           response.flushBuffer();
         } catch (Exception e) {
           e.printStackTrace();
         }finally{
           os.flush();
           os.close();
           os = null;
         }
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
			fs.add(new Filter("fromUnit", "%"+value+"%",Filter.OP_ILIKE));
			fs.add(new Filter("docNumber", "%"+value+"%",Filter.OP_ILIKE));
		}
		if(paramMap.containsKey(property="backTime") && !StringUtils.isEmpty(value=String.valueOf(paramMap.get(property)))){
			search.addFilter(new Filter("backTime", value+" 06:00:00",Filter.OP_GREATER_OR_EQUAL));
			try {
				value = DateUtil.format(DateUtil.getDateAfter(DateUtil.parse(value, "yyyy-MM-dd"), 1), "yyyy-MM-dd");
			} catch (Exception e) {
				log.error("查询退件登记日期转换异常！");
			}
//			search.addFilter(new Filter("backTime", value+" 23:59:59",Filter.OP_LESS_OR_EQUAL));
			search.addFilter(new Filter("backTime", value+" 06:59:59",Filter.OP_LESS_OR_EQUAL));
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
	public @ResponseBody FormLoadJsonBean<FileSign> loadBean(@RequestParam(value = "id", required = true) Long id) {
		FormLoadJsonBean<FileSign> result = new FormLoadJsonBean<FileSign>();
		try {
			FileSign bean = fileSignService.load(id);
			result.setSuccess(true);
			result.setData(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping(value="/search.do",method=RequestMethod.POST)
	public @ResponseBody FormLoadJsonBean<String> searchByPorperty(@RequestParam(value="barNo",required = true) String barNo){
		FormLoadJsonBean<String> result = new FormLoadJsonBean<String>();
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("barNo", barNo);
		List<FileReg> list = service.getByProperty(FileReg.class, conditionMap, null);
		result.setSuccess(true);
		result.setData(list.get(0).getFormUnit());
		return result;
	}
	
	/**
	 * 添加文件登记信息
	 * 
	 * @param docTypeName
	 * @param fileReg
	 * @return
	 */
	@RequestMapping(value = "/addSave.do", method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean addSave(FileSign fileSign) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fileSign.setBackTime(df.format(new Date()));
			fileSign.setSignUser(UserDetailsHelper.getUser().getName());
			fileSign = fileSignService.add(fileSign);
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
	 * 保存修改文件登记信息
	 * 
	 * @param docTypeName
	 * @param fileReg
	 * @return
	 */
	@RequestMapping(value = "/editSave.do", method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean editSave(FileSign fileSign) {
		try {
			FileSign fs = fileSignService.load(fileSign.getId());
			fs.setBackTime(fileSign.getBackTime());
			fs.setDocNumber(fileSign.getDocNumber());
			fs.setFromUnit(fileSign.getFromUnit());
			fs.setRemark(fileSign.getRemark());
			fs.setTitle(fileSign.getTitle());
			fs.setSignUser(fileSign.getSignUser());
			fileSignService.update(fs);  
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
				fileSignService.delete(ids);
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
