package com.site.controller.fileUpload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Date;

@Controller
public class FileUploadController implements ServletContextAware {
	
	
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		this.servletContext = arg0;
	}
	
	@RequestMapping(value="fileUpload/upload", method = RequestMethod.POST)
	public String handleUploadData(String name, @RequestParam("file") CommonsMultipartFile file){
		if(!file.isEmpty()){
			String path = this.servletContext.getRealPath("/upload/");//获取本地存储路径
			System.out.println(path);
			
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.indexOf("."));
			System.out.println("fileType=" + fileType);
			File file2 = new File(path, new Date().getTime() + fileType);//新建一个文件
			try{
				file.getFileItem().write(file2);//将上传的文件写到新建的文件中
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return "redirect:upload_ok";
//			return "upload_ok";
		}else{
//			return "forward:upload_error";
			return "redirect:upload_error";
//			return "redirect:/upload_error";
//			return "upload_error";
		}
	}
	
	@RequestMapping(value="fileUpload/upload", method=RequestMethod.GET)
	public String openUploadPage(){
		return "fileUpload/upload";
	}
	
	@RequestMapping("fileUpload/upload_ok")
	public String uploadOk(){
		return "fileUpload/upload_ok";
	}
	
	@RequestMapping("fileUpload/upload_error")
	public String uploadError(){
		return "fileUpload/upload_error";
	}
	

}
