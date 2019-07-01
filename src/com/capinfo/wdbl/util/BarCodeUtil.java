package com.capinfo.wdbl.util;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.capinfo.wdbl.bean.FileReg;
import com.htky.pdf417.BarCodeEntity;
import com.htky.pdf417.BarCodeImgCreator;

public class BarCodeUtil {
	
	public static String createBarCode(FileReg fr) throws IOException{
		// 设置条形码参数
		BarCodeEntity entity = new BarCodeEntity();
		// 条码标准
		entity.setVerStr("GB0626-2005");
		// 条码编号
		entity.setBarcode(fr.getBarNo());
		// 发文机关
		entity.setSendDept(fr.getFormUnit());
		// 公文种类或刊物名称
		entity.setFileName(fr.getDocType());
		// 发文字号或期号
		entity.setFileNum(fr.getDocNumber());
		// 主送单位
		entity.setMainSendDept(fr.getFormUnit());
		// 标题
		entity.setTitle(fr.getTitle());
		// 秘密等级
		entity.setSecret(fr.getSecurityGrade()!=null?fr.getSecurityGrade():"无");
		// 紧急程度
		entity.setHurry(fr.getUrgencyDegree()!=null?fr.getUrgencyDegree():"无");
		// 成文日期
		entity.setFileCreateDate(fr.getReceiveTime());
		// 发布层次
		entity.setSendDegree("无");
		// 条码制作单位
		entity.setBarCreateDept(fr.getFormUnit());
		// 条码制作时间
		entity.setBarCreateDate(fr.getReceiveTime());
		// 公文页数
		entity.setFileCount("无");
		// 附件标识
		entity.setAttachmentIdentity("无");
		// 关联文件文号
		entity.setRelationFileNum("无");
		// 自定义字段
		entity.setOther("");
		BarCodeImgCreator bic = new BarCodeImgCreator();
		String filePath = FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".bmp";
		// 生成条形码图片（指定图片名称及图片路径）
		bic.imgCreator(entity, filePath);
		return filePath;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		// 设置条形码参数
		BarCodeEntity entity = new BarCodeEntity();
		// 条码标准
		entity.setVerStr("GB0626-2005");
		// 条码编号
		entity.setBarcode("GA11010111010101010101");
		// 发文机关
		entity.setSendDept("北京市公安局");
		// 公文种类或刊物名称
		entity.setFileName("国务院文件");
		// 发文字号或期号
		entity.setFileNum("公通字（2015）123号");
		// 主送单位
		entity.setMainSendDept("中华人民共和国商务部");
		// 标题
		entity.setTitle("标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题");
		// 秘密等级
		entity.setSecret("秘密");
		// 紧急程度
		entity.setHurry("特提");
		// 成文日期
		entity.setFileCreateDate("20050101");
		// 发布层次
		entity.setSendDegree("省军级");
		// 条码制作单位
		entity.setBarCreateDept("办公室");
		// 条码制作时间
		entity.setBarCreateDate("20050101");
		// 公文页数
		entity.setFileCount("2");
		// 附件标识
		entity.setAttachmentIdentity("附件标识");
		// 关联文件文号
		entity.setRelationFileNum("关联文件文号");
		// 自定义字段
		entity.setOther("");
		
		
		
		// 生成条形二维码图片工具类（不指定长度）
		//BarCodeImgCreator bic = new BarCodeImgCreator();
		
		// 生成条形二维码图片工具类（指定长度）
		BarCodeImgCreator bic = new BarCodeImgCreator();
		
		// 生成条形码图片（指定图片名称及图片路径）
		bic.imgCreator(entity, "d:/pdf417.bmp");
	}
}
