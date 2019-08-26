package com.legou.Controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.legou.Utils.utils.FastDFSClient;
import com.legou.Utils.utils.JsonUtils;



@Controller
public class PicUploadController {
	
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try {
			//加载FastDFSClient工具类
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fastdfs.properties");
			//截取文件名
			int i = uploadFile.getOriginalFilename().lastIndexOf(".")+1;
			//截取后缀
			String substring = uploadFile.getOriginalFilename().substring(i);
			//获取http://192.168.25.133/后的url字段
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(),substring);
			//获取http://192.168.25.133/
			String hostname = "http://192.168.25.133/";
			//拼接字符串
			url = hostname+url;
			//创建map键值对
			Map map = new HashMap();			
			map.put("error", 0);
			map.put("url", url);
			//将map转化成json并返回其结果
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			Map map = new HashMap();
			map.put("error", 1);
			map.put("message", "上传图片异常");
			return JsonUtils.objectToJson(map);
		}											
	}

	
//		try {
//			
//			FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fastdfs.properties");
//						
//			System.out.println(uploadFile.getOriginalFilename());
//			
//			int i = uploadFile.getOriginalFilename().lastIndexOf(".")+1;
//			
//			String extnameString = uploadFile.getOriginalFilename().substring(i);
//			
//			//group/
//			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extnameString);
//			
//			String hostname="http://192.168.25.133/";
//			
//			url=hostname+url;			
//			System.out.println(url);
//			
//			Map map = new HashMap();
//			map.put("error", 0);
//			map.put("url", url);
//			
//			return JsonUtils.objectToJson(map);
//						
//		} catch (Exception e) {
//			Map map = new HashMap();
//			map.put("error", 1);
//			map.put("message", "上传图片异常");
//			return JsonUtils.objectToJson(map);						
//		}		
//	}
}
