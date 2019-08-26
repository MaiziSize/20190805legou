package com.legou.fastdfs;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class TestFastdfs {
	public static void main(String[] args) throws FileNotFoundException, IOException, MyException {
	// 1.先创建一个配置文件——fastdfs.properties，配置文件的内容就是指定TrackerServer的地址
	//2.使用全局方法加载配置文件
		ClientGlobal.init("C:\\Users\\Administrator\\Desktop\\最后一个阶段\\20190805-legou\\20190805legou\\legou-manager-web\\src\\main\\resources\\config\\fastdfs.properties");
	//3.创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
	//4.通过TrackerClient对象来获取TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
	//5.创建StorageServer的引用 使用null就可以了
		StorageServer storageServer = null;
	//6.创建一个StoragerClient对象，其需要两个对象一个TrackerServer一个StorageServer;
		StorageClient  storageClient = new StorageClient (trackerServer,storageServer);
	//7.使用StorageClient对象上传文件(图片)
	// // 参数1：文件名，参数名：扩展名，不能包含"."，参数3：文件的元数据，保存文件的原始名、大小、尺寸等，如果没有可为null
		String[]  strings = storageClient.upload_file("D:/img/作业/12.jpg","jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}	
}
