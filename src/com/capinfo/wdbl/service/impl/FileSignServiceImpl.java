package com.capinfo.wdbl.service.impl;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.wdbl.bean.FileSign;
import com.capinfo.wdbl.dao.FileSignDao;
import com.capinfo.wdbl.service.FileSignService;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

@Service
public class FileSignServiceImpl implements FileSignService{
	
	
	@Autowired
	private FileSignDao fileSignDao;

	public SearchResult<FileSign> list(ISearch search){
		return fileSignDao.searchAndCount(search);
	}
	
	public boolean daoChu(String backTime,ISearch search,OutputStream os){
		try {
	        @SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet("退件登记表");   //--->创建了一个工作簿
	        HSSFCellStyle style = wb.createCellStyle(); // 样式对象  标题
	        HSSFCellStyle style1 = wb.createCellStyle(); // 样式对象  
	        HSSFCellStyle style2 = wb.createCellStyle(); // 样式对象  
	        HSSFCellStyle style3 = wb.createCellStyle(); // 样式对象  
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
	        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直  
	        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 水平
	        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直  
	        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
	        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直  
	        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
	      //设置标题字体格式  
	        Font font = wb.createFont();     
	        Font font1 = wb.createFont();     
	        Font font2 = wb.createFont();     
	        //设置字体样式   
	        font1.setFontHeightInPoints((short)11);   //--->设置字体大小  
	        
	        font1.setFontName("宋体");   //---》设置字体，是什么类型例如：宋体  
	        font2.setFontHeightInPoints((short)11);   //--->设置字体大小  
	        font2.setFontName("宋体");   //---》设置字体，是什么类型例如：宋体  
	        font2.setBold(true);
	        style1.setFont(font1);  
	        style2.setFont(font1);
	        style3.setFont(font2);
	        style1.setWrapText(true);   //设置是否能够换行，能够换行为true  
	        style2.setWrapText(true);   //设置是否能够换行，能够换行为true  
	        style3.setWrapText(true);   //设置是否能够换行，能够换行为true  
	        
	        font.setFontHeightInPoints((short)24);   //--->设置字体大小  
	        font.setFontName("宋体");   //---》设置字体，是什么类型例如：宋体  
	        font.setBold(true);     //--->设置是否是加粗  
	        style.setFont(font);     //--->将字体格式加入到style中     
	        style.setWrapText(true);   //设置是否能够换行，能够换行为true  
	        
	        style.setBorderBottom((short)1);   //设置下划线，参数是黑线的宽度  
	        style.setBorderLeft((short)1);   //设置左边框  
	        style.setBorderRight((short)1);   //设置有边框  
	        style.setBorderTop((short)1);   //设置上边框  

	        style1.setBorderBottom((short)1);   //设置下划线，参数是黑线的宽度  
	        style1.setBorderLeft((short)1);   //设置左边框  
	        style1.setBorderRight((short)1);   //设置有边框  
	        style1.setBorderTop((short)1);   //设置下边框  

	        style2.setBorderLeft((short)1);   //设置左边框  
	        style2.setBorderRight((short)1);   //设置有边框  
	        
	        style3.setBorderBottom((short)1);   //设置下划线，参数是黑线的宽度  
	        style3.setBorderLeft((short)1);   //设置左边框  
	        style3.setBorderRight((short)1);   //设置有边框  
	        style3.setBorderTop((short)1);   //设置下边框  
	        

	        List<FileSign> result = list(search).getResult();
	        
	        HSSFRow row1 = sheet.createRow(0);   //--->创建一行 
	        // 四个参数分别是：起始行，结束行，起始列，结束列  
	        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) result.size()));
	        row1.setHeightInPoints(80);
	        HSSFCell cell10 = row1.createCell(0);
	        for(int i=0;i<=result.size();i++){
	            HSSFCell cell = row1.createCell(i);       
	            cell.setCellValue("");       
	            cell.setCellStyle(style);       
	        }    
	        cell10.setCellStyle(style);  
	        cell10.setCellValue("文件签收登记表");  
	        
	        HSSFRow row2 = sheet.createRow(1);
	        sheet.addMergedRegion(new CellRangeAddress((short)1,(short) 1, (short)0,(short) result.size()));
	        row2.setHeightInPoints(30);
	        HSSFCell cell20 = row2.createCell(0);
	        for(int i=0;i<=result.size();i++){
	            HSSFCell cell = row2.createCell(i);       
	            cell.setCellValue("");       
	            cell.setCellStyle(style2);       
	        }    
	        cell20.setCellStyle(style2);
	        cell20.setCellValue(backTime);
	        //垂直
//	        HSSFRow row4 = sheet.createRow(2);
//	        row4.setHeightInPoints(50);
//	        HSSFCell cell40 = row4.createCell(0);
//	        cell40.setCellStyle(style3);
//	        cell40.setCellValue("序号");
//	        HSSFCell cell41 = row4.createCell(1);
//	        cell41.setCellStyle(style3);
//	        cell41.setCellValue("来文单位");
//	        HSSFCell cell42 = row4.createCell(2);
//	        cell42.setCellStyle(style3);
//	        cell42.setCellValue("文件标题");
//	        HSSFCell cell43 = row4.createCell(3);
//	        cell43.setCellStyle(style3);
//	        cell43.setCellValue("文号");
//	        HSSFCell cell44 = row4.createCell(4);
//	        cell44.setCellStyle(style3);
//	        cell44.setCellValue("签收人");
//	        HSSFCell cell45 = row4.createCell(5);
//	        cell45.setCellStyle(style3);
//	        cell45.setCellValue("退件时间");
//	        HSSFCell cell46 = row4.createCell(6);
//	        cell46.setCellStyle(style3);
//	        cell46.setCellValue("备注");
//	        if(!result.isEmpty()){
//	        	for (int i = 0; i < result.size(); i++) {
//	        		FileSign fileSign = result.get(i);
//	        		HSSFRow row5 = sheet.createRow(3+i);
//	        		row5.setHeightInPoints(50);
//	        		HSSFCell cell50 = row5.createCell(0);
//	        		cell50.setCellStyle(style1);
//	        		cell50.setCellValue(i+1);
//	        		HSSFCell cell51 = row5.createCell(1);
//	        		cell51.setCellStyle(style1);
//	        		cell51.setCellValue(fileSign.getFromUnit());
//	        		HSSFCell cell52 = row5.createCell(2);
//	        		cell52.setCellStyle(style1);
//	        		cell52.setCellValue(fileSign.getTitle());
//	        		HSSFCell cell53 = row5.createCell(3);
//	        		cell53.setCellStyle(style1);
//	        		cell53.setCellValue(fileSign.getDocNumber());
//	        		HSSFCell cell54 = row5.createCell(4);
//	        		cell54.setCellStyle(style1);
//	        		cell54.setCellValue("");
//	        		HSSFCell cell55 = row5.createCell(5);
//	        		cell55.setCellStyle(style1);
//	        		cell55.setCellValue(fileSign.getBackTime().substring(11, 16));
//	        		HSSFCell cell56 = row5.createCell(6);
//	        		cell56.setCellStyle(style1);
//	        		cell56.setCellValue(fileSign.getRemark());
//	        	}
//	        }
	        //水平
	        HSSFRow rows4 = sheet.createRow(2);
	        rows4.setHeightInPoints(30);
	        HSSFCell cells40 = rows4.createCell(0);
	        cells40.setCellStyle(style3);
	        cells40.setCellValue("序号");
	        HSSFRow rows5 = sheet.createRow(3);
	        rows5.setHeightInPoints(50);
	        HSSFCell cells50 = rows5.createCell(0);
	        cells50.setCellStyle(style3);
	        cells50.setCellValue("来文单位");
	        HSSFRow rows6 = sheet.createRow(4);
	        rows6.setHeightInPoints(70);
	        HSSFCell cells60 = rows6.createCell(0);
	        cells60.setCellStyle(style3);
	        cells60.setCellValue("文件标题");
	        HSSFRow rows7 = sheet.createRow(5);
	        rows7.setHeightInPoints(50);
	        HSSFCell cells70 = rows7.createCell(0);
	        cells70.setCellStyle(style3);
	        cells70.setCellValue("文号");
	        HSSFRow rows8 = sheet.createRow(6);
	        rows8.setHeightInPoints(50);
	        HSSFCell cells80 = rows8.createCell(0);
	        cells80.setCellStyle(style3);
	        cells80.setCellValue("签收人");
	        HSSFRow rows9 = sheet.createRow(7);
	        rows9.setHeightInPoints(50);
	        HSSFCell cells90 = rows9.createCell(0);
	        cells90.setCellStyle(style3);
	        cells90.setCellValue("退件时间");
	        HSSFRow rows10 = sheet.createRow(8);
	        rows10.setHeightInPoints(50);
	        HSSFCell cells100 = rows10.createCell(0);
	        cells100.setCellStyle(style3);
	        cells100.setCellValue("备注");
	        if(!result.isEmpty() && result!= null && result.size()>0){
		        sheet.setDefaultRowHeight((short) (2 * 256)); //设置默认行高，表示2个字符的高度
		        sheet.setDefaultColumnWidth(115/(result.size()+1));
	        	for (int i = 0; i < result.size(); i++) {
	        		
	        		FileSign fileSign = result.get(i);
	        		HSSFCell cells4 = rows4.createCell(i+1);
	    	        cells4.setCellStyle(style3);
	    	        cells4.setCellValue(i+1);
	    	        HSSFCell cells5 = rows5.createCell(i+1);
	    	        cells5.setCellStyle(style3);
	    	        cells5.setCellValue(fileSign.getFromUnit());
	    	        HSSFCell cells6 = rows6.createCell(i+1);
	    	        cells6.setCellStyle(style3);
	    	        cells6.setCellValue(fileSign.getTitle());
	    	        HSSFCell cells7 = rows7.createCell(i+1);
	    	        cells7.setCellStyle(style3);
	    	        cells7.setCellValue(fileSign.getDocNumber());
	    	        HSSFCell cells8 = rows8.createCell(i+1);
	    	        cells8.setCellStyle(style3);
	    	        cells8.setCellValue("");
	    	        HSSFCell cells9 = rows9.createCell(i+1);
	    	        cells9.setCellStyle(style3);
	    	        cells9.setCellValue(fileSign.getBackTime().substring(11, 16));
	    	        HSSFCell cells10 = rows10.createCell(i+1);
	    	        cells10.setCellStyle(style3);
	    	        cells10.setCellValue(fileSign.getRemark());
	        	}
	        }
	        
	        
	        wb.write(os);
			os.close();
			return true; 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public FileSign load(Long id) {
		return fileSignDao.find(id);
	}


	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public FileSign add(FileSign fileSign) {
		fileSignDao.save(fileSign);
		return fileSign;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void update(FileSign fileSign) {
		fileSignDao.save(fileSign);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void delete(Long[] ids) {
		fileSignDao.removeByIds(ids);
	}

}
