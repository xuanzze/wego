//package com.wego.fastdfs;
//
//import org.csource.fastdfs.ClientGlobal;
//import org.csource.fastdfs.StorageClient;
//import org.csource.fastdfs.StorageServer;
//import org.csource.fastdfs.TrackerClient;
//import org.csource.fastdfs.TrackerServer;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.alibaba.dubbo.config.ApplicationConfig;
//import com.alibaba.dubbo.config.ReferenceConfig;
//import com.wego.common.pojo.EasyUIDataGridResult;
//import com.wego.service.ItemService;
//
//public class TestFast {
//	@Test
//	public void testupload()throws Exception{
//		//1.创建一个配置文件  配置连接tracker的服务器地址
//		//2.初始化加载配置文件
//		ClientGlobal.init("/Users/arch/Projects/IdeaProjects/wego/wego-manager-web/src/main/resources/resource/fastdfs.conf");
//		//3.创建trackerClient对象
//		TrackerClient client = new TrackerClient();
//		//4.通过trackerClient获取trackerServer对象
//		TrackerServer trackerServer = client.getConnection();
//		//5.创建storegeServer 赋值为null
//		StorageServer storageServer = null;
//		//6.创建storgeClient  需要两个参数：trackserver   storageServer
//		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//		//7.调用上传图片的方法
//		//第一个参数：本地文件的路径
//		//第二个参数：文件的扩展名 不带"."
//		//第三个参数：元数据
//		String[] upload_file = storageClient.upload_file("/Users/arch/Pictures/38718546-CE42-41AC-8DE6-CFCECD2B9A71.png", "png", null);
//		//8.打印图片的地址 ，测试访问
//		for (String string : upload_file) {
//			System.out.println(string);
//		}
//	}
//
//	@Test
//	public void testFastClient() throws Exception{
//		FastDFSClient client = new FastDFSClient("/Users/arch/Projects/IdeaProjects/wego/wego-manager-web/src/main/resources/resource/fastdfs.conf");
//		String uploadFile = client.uploadFile("/Users/arch/Pictures/15306040354843.jpeg", "jpeg");
//		System.out.println(uploadFile);
//	}
//
////	@Test
////	public void testService(){
////		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/springmvc.xml");
////		ItemService itemservice = (ItemService)context.getBean("itemService");
////		System.out.println(itemservice.getClass());
////		//
////		ApplicationConfig config = new ApplicationConfig();
////		ReferenceConfig referenceConfig = new ReferenceConfig<ReferenceConfig>();
////		EasyUIDataGridResult itemList = itemservice.getItemList(1, 30);
////		System.out.println(itemList.toString());
////	}
//}
