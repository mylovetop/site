package com.site.controller.fileUpload;




import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zdsoft on 14-8-1.
 */
@Controller
public class UeditorUploadController implements ServletContextAware {

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping(value="ueditor/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleUploadData(String name, @RequestParam("upfile") CommonsMultipartFile file){
        String retVal = "";
        if(!file.isEmpty()){
            String uploadPath = "/resource/upload/";
//            String path = this.servletContext.getRealPath("/WEB-INF/upload/");//获取本地存储路径
            String path = this.servletContext.getRealPath(uploadPath);
            System.out.println(path);

            String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.indexOf("."));
            System.out.println("fileType=" + fileType);

            String fileNameNew = new Date().getTime() + fileType;
            File file2 = new File(path, fileNameNew);//新建一个文件
            try{
                file.getFileItem().write(file2);//将上传的文件写到新建的文件中
            }catch(Exception ex){
                ex.printStackTrace();
            }


            try{
                Map m = new HashMap();
                m.put("state", "SUCCESS");
                m.put("url", uploadPath + fileNameNew);

                ObjectMapper mapper = new ObjectMapper();
                //ueditor 多张图片上传插件上传图片后，返回规则如下
                //'{"state":"SUCCESS", "url":"http://localhost:8080/resouce/upload/1406860398578.gif"}'
                retVal = mapper.writeValueAsString(m);

            } catch (Exception e){
                e.printStackTrace();
            }
        }



        return retVal;
    }

    public static void main(String[] args){


    }
}


