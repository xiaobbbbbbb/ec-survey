package com.ecarinfo.survey.command.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.command.AbstractCommand;
import com.ecarinfo.survey.dao.CarReportDao;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.po.CarReportImage;
import com.ecarinfo.survey.rm.CarReportImageRM;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.vo.BaseVO;
import com.ecarinfo.survey.vo.ImageVO;
@Component("ImageCommand")
public class ImageCommand extends AbstractCommand {
	private static final Logger logger = Logger.getLogger(ImageCommand.class);
	@Resource
	private CarReportDao carReportDao;
	@Resource
	private GenericService genericService;
	
	@Override
	public Object execute(ChannelHandlerContext ctx, BaseVO requestMessage) {
		
		try {
			ImageVO vo = (ImageVO)requestMessage;
			System.err.println(vo);
			
			EcOnline online = getEcOnline(ecOnlineManager,ctx.getChannel(), vo.getImei(),null);
			if(online == null) {
				logger.error("no device found imei = "+vo.getImei());
				return null;
			}
			
			String basePath = PropUtil.getProp("server.properties", "image_path");
			
			saveFile(online,basePath,vo);
			
			return "$V"+vo.getPictureSerialNumber()+vo.getPkgOrder()+"#\r\n";
		} catch (Exception e) {
			logger.error("",e);
			return "$V0#\r\n";
		}
	}

	private synchronized void saveFile(EcOnline online,String parentDir,ImageVO vo) throws Exception {
		FileOutputStream fos = null;
		FileInputStream in = null;
			//年月日+carId+carReportId+picSeriaNo+.jpg
		try{	
			CarReport report = carReportDao.findOne(new Criteria()
			.eq(CarReportRM.id, online.getCarReportId())
			.orderBy(CarReportRM.lastClientTime, OrderType.DESC));
			if(report == null) {
				throw new RuntimeException("no report found.");
			}
			int order = Integer.parseInt(vo.getPkgOrder());
			//临时文件
			File file=new File(parentDir+"/"+report.getCarId()+"/"+vo.getPictureSerialNumber(),vo.getPictureSerialNumber()+order+".temp");
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()) {
				file.createNewFile();
				fos = new FileOutputStream(file, true);//追加
				fos.write(vo.getImageData());
			}
			File[] files = file.getParentFile().listFiles();
			CarReportImage image = null;
			
			if(files.length== Integer.parseInt(vo.getPkgSize())){//所有图片片段传完
				
				image = new CarReportImage();
				image.setCarReportId(report.getId());
				image.setCreateTime(new Date());
				image.setSerialNo(Integer.parseInt(vo.getPictureSerialNumber()));
				image.setUrl(report.getId()+vo.getPictureSerialNumber()+".jpg");
				genericService.save(image);
				File file1 = new File(parentDir+"/"+report.getCarId(),image.getUrl());
				if(!file1.getParentFile().exists()) {
					file1.getParentFile().mkdirs();
				}
				if(file1.exists()){
					file1.delete();
				}
				if(!file1.exists()) {
					file1.createNewFile();
				}
				for(int i=0;i<files.length;i++){//全部数据整合
					
					fos = new FileOutputStream(file1, true);//追加
					in = new FileInputStream(parentDir+"/"+report.getCarId()+"/"+vo.getPictureSerialNumber()+"/"+vo.getPictureSerialNumber()+(i+1)+".temp");
					byte[] inOutb = new byte[in.available()];
					in.read(inOutb);
					fos.write(inOutb);
					fos.flush();
					in.close();
					fos.close();
					fos.flush();
				}
				//清空临时文件
				for(File f:files){
					if(f.isFile()){
						f.delete();
						f.getParentFile().delete();
					}
				}
				System.err.println("图片整合完毕：total="+vo.getPkgSize());
			} 
//			else {
//				image = genericService.findOne(CarReportImage.class, new Criteria()
//				.eq(CarReportImageRM.carReportId, report.getId())
//				.eq(CarReportImageRM.serialNo, vo.getPictureSerialNumber()));
//			}
//			if(image == null) {
//				throw new RuntimeException("no image found.");
//			}
			
		} finally {
			try {
				fos.flush();
				fos.close();
				in.close();
			} catch (Exception e) {
				logger.error("",e);
			}
		}
	}
	
	public static void main(String orgs[]) throws IOException{
//	    //    
//		ImageCommand im=new ImageCommand();
//		FileOutputStream fos=null;
//		FileInputStream in=null;
//		int order =4;
//		//临时文件
//		File file=new File("d:/1/0001","00013.temp");
//		if(!file.getParentFile().exists()) {
//			file.getParentFile().mkdirs();
//		}
//		if(!file.exists()) {
//			file.createNewFile();
//			fos = new FileOutputStream(file, true);//追加
//		}
//		File[] files = file.getParentFile().listFiles();
//		
//		CarReportImage image = null;
//		if(files.length== 4){
//			File file1 = new File("d:/1","10001.temp");
//			if(!file1.getParentFile().exists()) {
//				file1.getParentFile().mkdirs();
//			}
//			if(file1.exists()){
//				file1.delete();
//			}
//			if(!file1.exists()) {
//				file1.createNewFile();
//			}
//			for(int i=0;i<files.length;i++){//全部数据整合
//				
//				fos = new FileOutputStream(file1, true);//追加
//				in =new FileInputStream("d:/1/0001/0001"+(i+1)+".temp");
//				byte[] inOutb = new byte[in.available()];
//				in.read(inOutb);
//				fos.write(inOutb);
//				
//				fos.close();
//				in.close();		
//				fos.flush();
//			}
//
//			//清空临时文件
//			for(File f:files){
//				if(f.isFile()){
//					f.delete();
//					f.getParentFile().delete();
//				}
//			}
//			System.err.println("图片生产完毕：tot"+4);
//		}
//	 
	}
	
}

