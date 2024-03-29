package com.capinfo.wdbl.action.file_process;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.file_process.File_PhoneRecord;
import com.capinfo.wdbl.enums.DocType;
import com.capinfo.wdbl.service.FileRegExportService;
import com.capinfo.wdbl.service.FileRegService;

/**
 * 
 * [导出阅批单]
 * 
 */
@Controller
@RequestMapping("/file_export")
public class FileExportController {

	private Log log = LogFactory.getLog(FileExportController.class);

	@Autowired
	private FileRegExportService exportService;
	@Autowired
	private FileRegService fileRegService;

	@RequestMapping("/{docTypeName}/{id}/export.do")
	public void doFileExport(@PathVariable String docTypeName, @PathVariable Long id, HttpServletResponse response)
	{
		log.debug(String.format("export %s to word invoked", docTypeName));
		File tmpFile = null;
		try {
			int docTypeVal = DocType.valueOf(docTypeName.toUpperCase()).ordinal() + 1;
			String filenamePrefix = null;
			if(docTypeVal != (DocType.TEL_RCD_DOC.ordinal()+1) ){
				FileReg fileReg = fileRegService.getObject(FileReg.class, id);
				Assert.notNull(fileReg,"未找到相应文件的登记信息");
				filenamePrefix = fileReg.getTitle();
			}else{
				File_PhoneRecord rcd = fileRegService.getObject(File_PhoneRecord.class, id);
				filenamePrefix = String.format("电话记录.%s", 
						rcd.getPhone_time().replaceAll(" ",".").replaceAll(":","."));
			}
			tmpFile = exportService.getGenerateExportFile(docTypeVal, id);
			InputStream is = FileUtils.openInputStream(tmpFile);
			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition", "attachment;filename=["
					+ URLEncoder.encode(filenamePrefix, "UTF-8")
					+ "]_"+System.currentTimeMillis()+".doc");

			// copy it to response's OutputStream
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception ex) {
			log.error("生成阅批单失败:" + ex.getMessage(), ex);
		} finally {
			FileUtils.deleteQuietly(tmpFile);
		}
	}
	
	@RequestMapping(value="/{docTypeName}/{id}/exportFile.do")
	public ResponseEntity<byte[]> downLoad(@PathVariable String docTypeName, @PathVariable Long id) throws Exception {
		File tmpFile = null;
		String filenamePrefix = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
		try {
			
			// 获取文档类型
			int docTypeVal = DocType.valueOf(docTypeName.toUpperCase()).ordinal() + 1;
			
			// 获取文件信息
			FileReg fileReg = fileRegService.getObject(FileReg.class, id);
			// 判断文件是否为空
			Assert.notNull(fileReg,"未找到相应文件的登记信息");
			// 设置文件名前缀
			filenamePrefix = fileReg.getTitle();
			if(filenamePrefix == null || filenamePrefix.equals("")) {
				filenamePrefix = UUID.randomUUID().toString();
			}
			// 设置文件名称
			String fileName = "["+URLEncoder.encode(filenamePrefix, "UTF-8")+"]_"+System.currentTimeMillis()+".docx";
			headers.setContentDispositionFormData("attachment", fileName);
			
			if(docTypeVal == (DocType.TEL_RCD_DOC.ordinal()+1) ){ //电话记录
//				File_PhoneRecord rcd = fileRegService.getObject(File_PhoneRecord.class, id);
//				filenamePrefix = String.format("电话记录.%s", rcd.getPhone_time().replaceAll(" ",".").replaceAll(":","."));
//				String fileName = "["+URLEncoder.encode(filenamePrefix, "UTF-8")+"]_"+System.currentTimeMillis()+".doc";
//				headers.setContentDispositionFormData("attachment", fileName);
				tmpFile = exportService.getGeneratePhoneRecordExportFile(docTypeVal, id);
		        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tmpFile),headers, HttpStatus.OK);
			} else if(docTypeVal == (DocType.CIPHER_TELEGRAPH_DOC.ordinal()+1) ){ //密码电报审批单
				tmpFile = exportService.getGenerateTelegraphExportFile(docTypeVal, id);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tmpFile),headers, HttpStatus.OK);
			}  else if(docTypeVal == (DocType.INSTRUCTIONS_DOC.ordinal()+1) ){ //批示抄清
				tmpFile = exportService.getGenerateInstructionsExportFile(docTypeVal, id);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tmpFile),headers, HttpStatus.OK);
			} else { // 公文审批单
				tmpFile = exportService.getGenerateExportFile(docTypeVal, id);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tmpFile),headers, HttpStatus.OK);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
