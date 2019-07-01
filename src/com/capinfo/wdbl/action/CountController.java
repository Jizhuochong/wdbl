package com.capinfo.wdbl.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capinfo.base.service.BaseService;
import com.capinfo.wdbl.bean.DateUtil;
import com.capinfo.wdbl.bean.FileReg;


/**
 * [文件登记Controller]
 * 
 */
@Controller
@RequestMapping("count")
public class CountController {

	private static final Log log = LogFactory.getLog(CountController.class);
	@Autowired
	@Qualifier("baseService")
	private BaseService service;

	/**
	 * @describing 主要领导文件，未退件统计表
	 * @author 姜民飞
	 */
	@RequestMapping(value = "/printExcel2.controller")
	public void printExcel2( HttpServletRequest request, HttpServletResponse response) {
		FileReg model = new FileReg();
		//model.setDocType("");
		model.setHandlestatus("送阅");
		List<FileReg> list = service.getByProperty(FileReg.class, model, null);
		
		// 创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
		// sheet页名称
		workBook.setSheetName(0, "sheet1");

		//自动换行
		HSSFCellStyle cellStyle = workBook.createCellStyle();    
		cellStyle.setWrapText(true);    
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index); 

		// 设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(Short.valueOf("0"));
		cell0.setCellStyle(cellStyle); 
		HSSFCell cell1 = row.createCell(Short.valueOf("1"));
		cell1.setCellStyle(cellStyle); 
		HSSFCell cell2 = row.createCell(Short.valueOf("2"));
		cell2.setCellStyle(cellStyle); 
		HSSFCell cell3 = row.createCell(Short.valueOf("3"));
		cell3.setCellStyle(cellStyle); 
		HSSFCell cell4 = row.createCell(Short.valueOf("4"));
		cell4.setCellStyle(cellStyle); 
		HSSFCell cell5 = row.createCell(Short.valueOf("5"));
		cell5.setCellStyle(cellStyle); 

		cell0.setCellValue("文件id");
		cell1.setCellValue("呈送日期");
		cell2.setCellValue("单位");
		cell3.setCellValue("文件编号");
		cell4.setCellValue("标题");
		cell5.setCellValue("备注");

		if(list != null && !list.isEmpty()) {  
            for(int i = 0; i < list.size(); i++) {  
            	FileReg fileReg = list.get(i);  
                row = sheet.createRow(i + 1);  
                cell0 = row.createCell(Short.valueOf("0"));  
                cell1 = row.createCell(Short.valueOf("1"));  
                cell2 = row.createCell(Short.valueOf("2")); 
                cell3 = row.createCell(Short.valueOf("3"));  
                cell4 = row.createCell(Short.valueOf("4"));  
                cell5 = row.createCell(Short.valueOf("5")); 
  
                cell0.setCellValue(fileReg.getId()); 
                cell1.setCellValue(fileReg.getReceiveTime());
                cell4.setCellValue(fileReg.getTitle());  
                cell5.setCellValue(fileReg.getRemark());
                
                cell0.setCellStyle(cellStyle);
                cell1.setCellStyle(cellStyle);
                cell2.setCellStyle(cellStyle);
                cell3.setCellStyle(cellStyle);
                cell4.setCellStyle(cellStyle);
                cell5.setCellStyle(cellStyle); 
                
                sheet.setColumnWidth((short) 0, (short) 3000);  
                sheet.setColumnWidth((short) 1, (short) 3000);  
                sheet.setColumnWidth((short) 2, (short) 3000); 
                sheet.setColumnWidth((short) 3, (short) 3000);  
                sheet.setColumnWidth((short) 4, (short) 3000); 
                sheet.setColumnWidth((short) 5, (short) 3000); 
            }  
        }

		/* 创建文件 */
		String d = DateUtil.getCurrentDate() + ".xls";
		d = d.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		try{
			File f=new File("");   
        	String creFilePath = f.getCanonicalPath().replace("\\bin", "");
			File dir=new File(creFilePath, d); 
			if(!dir.exists()) {
				dir.createNewFile(); 
			}
		}catch(Exception e){
			System.out.print("创建失败");
			e.printStackTrace();   
		} 
		
		// 将文件存到指定位置  
		String filePath = "";
        try {
        	File b=new File(d);   
        	filePath = b.getAbsolutePath().replace("\\bin", "");
            FileOutputStream fout = new FileOutputStream(filePath);  
            workBook.write(fout);  
            fout.close();  
            File c = new File(filePath);
           
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(c.getName(), "UTF-8"));
            response.addHeader("Content-Length", "" + c.length());
            response.setContentType("application/octet-stream");
            InputStream is = FileUtils.openInputStream(c);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } 
        catch (Exception e) {  
            e.printStackTrace();
        }
    }
	
	/**
	 * @describing printExcel 打印当前页面统计表
	 * @author 姜民飞
	 */
	@RequestMapping(value = "/printCurrentExcel.controller")
	public void printCurrentExcel( HttpServletRequest request, HttpServletResponse response ,
			Long[] ids) {
		//FileReg model = new FileReg();
		List<FileReg> list = new ArrayList<FileReg>();
		
		for (Long string : ids) {
			FileReg info = service.getObject(FileReg.class, string);
			if("1".equals(info.getDocType())){
				info.setDocType("市委市政府文件");
			}
			if("2".equals(info.getDocType())){
				info.setDocType("大范围分发");
			}
			if("3".equals(info.getDocType())){
				info.setDocType("电话记录");
			}
			if("4".equals(info.getDocType())){
				info.setDocType("公安部正式文件");
			}
			if("5".equals(info.getDocType())){
				info.setDocType("局长批示件");
			}
			if("7".equals(info.getDocType())){
				info.setDocType("领导交办件");
			}
			if("8".equals(info.getDocType())){
				info.setDocType("领导兼职");
			}
			if("9".equals(info.getDocType())){
				info.setDocType("直接转文");
			}
			if("10".equals(info.getDocType())){
				info.setDocType("亲启件");
			}
			if("11".equals(info.getDocType())){
				info.setDocType("其它");
			}
			if("12".equals(info.getDocType())){
				info.setDocType("主要领导文件文件");
			}
			if("13".equals(info.getDocType())){
				info.setDocType("密码电报文件");
			}
			list.add(info);
		}
		
		// 创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
		// sheet页名称
		workBook.setSheetName(0, "sheet1");
		// 创建header页
		HSSFHeader header = sheet.getHeader();
		// 设置标题居中
		//header.setCenter("信件"); 
		//自动换行
		HSSFCellStyle cellStyle = workBook.createCellStyle();    
		cellStyle.setWrapText(true);    
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index); 

		// 设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(Short.valueOf("0"));
		cell0.setCellStyle(cellStyle); 
		HSSFCell cell1 = row.createCell(Short.valueOf("1"));
		cell1.setCellStyle(cellStyle); 
		HSSFCell cell2 = row.createCell(Short.valueOf("2"));
		cell2.setCellStyle(cellStyle); 
		HSSFCell cell3 = row.createCell(Short.valueOf("3"));
		cell3.setCellStyle(cellStyle); 
		HSSFCell cell4 = row.createCell(Short.valueOf("4"));
		cell4.setCellStyle(cellStyle); 
		HSSFCell cell5 = row.createCell(Short.valueOf("5"));
		cell5.setCellStyle(cellStyle); 
		HSSFCell cell6 = row.createCell(Short.valueOf("6"));
		cell6.setCellStyle(cellStyle); 
		HSSFCell cell7 = row.createCell(Short.valueOf("7"));
		cell7.setCellStyle(cellStyle); 

		cell0.setCellValue("id");
		cell1.setCellValue("流水号");
		cell2.setCellValue("来文单位");
		cell3.setCellValue("文号");
		cell4.setCellValue("文件标题");
		cell5.setCellValue("文件类型");
		cell6.setCellValue("来文时间");
		cell7.setCellValue("状态");

		if(list != null && !list.isEmpty()) {  
            for(int i = 0; i < list.size(); i++) {  
            	FileReg fileReg = list.get(i);  
                row = sheet.createRow(i + 1);  
                cell0 = row.createCell(Short.valueOf("0"));  
                cell1 = row.createCell(Short.valueOf("1"));  
                cell2 = row.createCell(Short.valueOf("2")); 
                cell3 = row.createCell(Short.valueOf("3"));  
                cell4 = row.createCell(Short.valueOf("4"));  
                cell5 = row.createCell(Short.valueOf("5")); 
                cell6 = row.createCell(Short.valueOf("6")); 
                cell7 = row.createCell(Short.valueOf("7")); 
  
                cell0.setCellValue(fileReg.getId()); 
                cell1.setCellValue(fileReg.getSn());
                cell2.setCellValue(fileReg.getFormUnit()); 
                cell3.setCellValue(fileReg.getDocNumber()); 
                cell4.setCellValue(fileReg.getTitle());  
                cell5.setCellValue(fileReg.getDocType());
                cell6.setCellValue(fileReg.getReceiveTime());
                cell7.setCellValue(fileReg.getHandlestatus());
                
                cell0.setCellStyle(cellStyle);
                cell1.setCellStyle(cellStyle);
                cell2.setCellStyle(cellStyle);
                cell3.setCellStyle(cellStyle);
                cell4.setCellStyle(cellStyle);
                cell5.setCellStyle(cellStyle); 
                cell6.setCellStyle(cellStyle); 
                cell7.setCellStyle(cellStyle); 
                
                sheet.setColumnWidth((short) 0, (short) 3000);  
                sheet.setColumnWidth((short) 1, (short) 3000);  
                sheet.setColumnWidth((short) 2, (short) 3000); 
                sheet.setColumnWidth((short) 3, (short) 3000);  
                sheet.setColumnWidth((short) 4, (short) 3000); 
                sheet.setColumnWidth((short) 5, (short) 3000); 
                sheet.setColumnWidth((short) 6, (short) 3000); 
                sheet.setColumnWidth((short) 7, (short) 3000); 
            }  
        }

		/* 创建文件 */
		//Random random = new Random(100);//指定种子数100
		String d = DateUtil.getCurrentDate() + ".xls";
		d = d.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		try{
			 
			File A=new File("");   
        	String creFilePath = A.getCanonicalPath().replace("\\bin", "");
			File dir=new File(creFilePath, d); 
			if(!dir.exists()) {
				dir.createNewFile(); 
			}
		}catch(Exception e){
			e.printStackTrace();   
		} 
		
		// 将文件存到指定位置  
		String filePath = "";
        try {
        	File B=new File(d);   
        	filePath = B.getAbsolutePath().replace("\\bin", "");
            FileOutputStream fout = new FileOutputStream(filePath);  
            workBook.write(fout);  
            fout.close();  
            File C = new File(filePath);
           
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(C.getName(), "UTF-8"));
            response.addHeader("Content-Length", "" + C.length());
            response.setContentType("application/octet-stream");
            InputStream is = FileUtils.openInputStream(C);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            
        } 
        catch (Exception e) {  
            e.printStackTrace();
        }
    }
	
	/**
	 * @describing printExcel 打印当前页面统计表
	 * @author 姜民飞
	 */
	@RequestMapping(value = "/printExcel.controller")
	public void printExcel( HttpServletRequest request, HttpServletResponse response,Long[] ids) {
		List<FileReg> list = new ArrayList<FileReg>();
		
		for (Long string : ids) {
			FileReg info = service.getObject(FileReg.class, string);
			if("1".equals(info.getDocType())){
				info.setDocType("市委市政府文件");
			}
			if("2".equals(info.getDocType())){
				info.setDocType("大范围分发");
			}
			if("3".equals(info.getDocType())){
				info.setDocType("电话记录");
			}
			if("4".equals(info.getDocType())){
				info.setDocType("公安部正式文件");
			}
			if("5".equals(info.getDocType())){
				info.setDocType("局长批示件");
			}
			if("7".equals(info.getDocType())){
				info.setDocType("领导交办件");
			}
			if("8".equals(info.getDocType())){
				info.setDocType("领导兼职");
			}
			if("9".equals(info.getDocType())){
				info.setDocType("直接转文");
			}
			if("10".equals(info.getDocType())){
				info.setDocType("亲启件");
			}
			if("11".equals(info.getDocType())){
				info.setDocType("其它");
			}
			if("12".equals(info.getDocType())){
				info.setDocType("主要领导文件文件");
			}
			if("13".equals(info.getDocType())){
				info.setDocType("密码电报文件");
			}
			list.add(info);
		}
		
		// 创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
		// sheet页名称
		workBook.setSheetName(0, "sheet1");
		// 创建header页
		HSSFHeader header = sheet.getHeader();
		// 设置标题居中
		//header.setCenter("信件"); 
		//自动换行
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index); 

		// 设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(Short.valueOf("0"));
		cell0.setCellStyle(cellStyle); 
		HSSFCell cell1 = row.createCell(Short.valueOf("1"));
		cell1.setCellStyle(cellStyle); 
		HSSFCell cell2 = row.createCell(Short.valueOf("2"));
		cell2.setCellStyle(cellStyle); 
		HSSFCell cell3 = row.createCell(Short.valueOf("3"));
		cell3.setCellStyle(cellStyle); 
		HSSFCell cell4 = row.createCell(Short.valueOf("4"));
		cell4.setCellStyle(cellStyle); 
		HSSFCell cell5 = row.createCell(Short.valueOf("5"));
		cell5.setCellStyle(cellStyle); 
		HSSFCell cell6 = row.createCell(Short.valueOf("6"));
		cell6.setCellStyle(cellStyle); 
		HSSFCell cell7 = row.createCell(Short.valueOf("7"));
		cell7.setCellStyle(cellStyle); 

		cell0.setCellValue("id");
		cell1.setCellValue("流水号");
		cell2.setCellValue("来文单位");
		cell3.setCellValue("文号");
		cell4.setCellValue("文件标题");
		cell5.setCellValue("文件类型");
		cell6.setCellValue("来文时间");
		cell7.setCellValue("状态");

		if(list != null && !list.isEmpty()) {  
            for(int i = 0; i < list.size(); i++) {  
            	FileReg fileReg = list.get(i);  
                row = sheet.createRow(i + 1);  
                cell0 = row.createCell(Short.valueOf("0"));  
                cell1 = row.createCell(Short.valueOf("1"));  
                cell2 = row.createCell(Short.valueOf("2")); 
                cell3 = row.createCell(Short.valueOf("3"));  
                cell4 = row.createCell(Short.valueOf("4"));  
                cell5 = row.createCell(Short.valueOf("5")); 
                cell6 = row.createCell(Short.valueOf("6")); 
                cell7 = row.createCell(Short.valueOf("7")); 
  
                cell0.setCellValue(fileReg.getId()); 
                cell1.setCellValue(fileReg.getSn());
                cell2.setCellValue(fileReg.getFormUnit()); 
                cell3.setCellValue(fileReg.getDocNumber()); 
                cell4.setCellValue(fileReg.getTitle());  
                cell5.setCellValue(fileReg.getDocType());
                cell6.setCellValue(fileReg.getReceiveTime());
                cell7.setCellValue(fileReg.getHandlestatus());
                
                cell0.setCellStyle(cellStyle);
                cell1.setCellStyle(cellStyle);
                cell2.setCellStyle(cellStyle);
                cell3.setCellStyle(cellStyle);
                cell4.setCellStyle(cellStyle);
                cell5.setCellStyle(cellStyle); 
                cell6.setCellStyle(cellStyle); 
                cell7.setCellStyle(cellStyle); 
                
                sheet.setColumnWidth((short) 0, (short) 3000);  
                sheet.setColumnWidth((short) 1, (short) 3000);  
                sheet.setColumnWidth((short) 2, (short) 3000); 
                sheet.setColumnWidth((short) 3, (short) 3000);  
                sheet.setColumnWidth((short) 4, (short) 3000); 
                sheet.setColumnWidth((short) 5, (short) 3000); 
                sheet.setColumnWidth((short) 6, (short) 3000); 
                sheet.setColumnWidth((short) 7, (short) 3000); 
            }  
        }

		/* 创建文件 */
		//Random random = new Random(100);//指定种子数100
		String d = DateUtil.getCurrentDate() + ".xls";
		d = d.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		try{
			File A=new File("");   
        	String creFilePath = A.getCanonicalPath().replace("\\bin", "");
			File dir=new File(creFilePath, d); 
			if(!dir.exists()) {
				dir.createNewFile(); 
			}
		}catch(Exception e){
			System.out.print("创建失败");
			e.printStackTrace();   
		} 
		
		// 将文件存到指定位置  
		String filePath = "";
        try {
        	File B=new File(d);   
        	filePath = B.getAbsolutePath().replace("\\bin", "");
        	System.out.println("路径：" + filePath);
            FileOutputStream fout = new FileOutputStream(filePath);  
            workBook.write(fout);  
            fout.close();  
            File C = new File(filePath);
           
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
            		+URLEncoder.encode(C.getName(), "UTF-8"));
            response.addHeader("Content-Length", "" + C.length());
            response.setContentType("application/octet-stream");
            InputStream is = FileUtils.openInputStream(C);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            
        } 
        catch (Exception e) {  
            e.printStackTrace();
        }
    }
}
