package com.hdfs;

import java.io.*;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
public class Hdfs {
	public static void main(String[] args) throws IOException{
		String uri = "hdfs://localhost:9000/";
		String local = "/home/wang/Workspace/hdfsfileop/file.txt";
		String remote = "hdfs://localhost:9000/Workspace/hdfsfileop/file.txt";
		Configuration conf = new Configuration();
		upLoad(conf,uri,local,remote);  //本地上传文件到hdfs
		//download(conf,uri,"hdfs://localhost:9000/Workspace/hdfsfileop/test","/home/wang/Workspace/hdfsfileop"); //下载
		//cat(conf,uri,remote);  
		//mkDir(conf,uri,"hdfs://localhost:9000/Workspace/hdfsfileop/test");
		//createFile(conf,uri,"hdfs://localhost:9000/Workspace/hdfsfileop/file.txt");
		//delete(conf,uri,"hdfs://localhost:9000/Workspace/hdfsfileop/file.txt");
		//ls(conf,uri,"hdfs://localhost:9000/user");
		//checkDir(uri,"hdfs://localhost:9000/user");
		//batchUpload(conf,uri,"../hdfsfileop","hdfs://localhost:9000/user/wang/input/");  //将hdfsfileop下的文件递归地（不包含hdfsfileop）上传到input/目录下
	}
	
	 /** 
     * 上传文件 
     * @param conf 
     * @param local 
     * @param remote 
     * @throws IOException 
     * 
     */  
	
	public static void upLoad(Configuration conf,String uri, String local, String remote) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(uri), conf);  //创建链接
		fs.copyFromLocalFile(new Path(local), new Path(remote)); //执行拷贝函数
		System.out.println("copy from :" + local +" to " + remote); //打印拷贝信息
		fs.close();
	}
	
	/** 
     * 下载 hdfs上的文件 
     * @param conf 
     * @param uri 
     * @param remote 
     * @param local 
     * @throws IOException 
     */  
    public static void download(Configuration conf , String uri ,String remote, String local) throws IOException {  
           Path r_path = new Path(remote);  
           FileSystem fs = FileSystem.get(URI.create(uri), conf);  
           fs.copyToLocalFile(r_path, new Path(local));  
           System.out.println("download: from" + remote + " to " + local);  
           fs.close();  
   }  
	
	
	
	
	 /** 
     * 获取hdfs上文件流 
     * @param conf 
     * @param uri 
     * @param local 
     * @param remote 
     * @throws IOException 
     */  
	
	public static void getFileStream(Configuration conf, String uri, String local, String remote ) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(uri),conf);
		Path r_path = new Path(remote);
		String l_path = local;
		FSDataInputStream in = fs.open(r_path);//获取文件流
		FileOutputStream fos = new FileOutputStream(l_path);
		int ch = 0;
		while((ch = in.read())!= -1){
			fos.write(ch);
		}
		System.out.println("--------");
		fos.close();
		in.close();
		
		
	}
	/** 
     * 创建目录 
     * @param conf 
     * @param uri 
     * @param remote
     * @throws IOException 
     */  
	
	public static void mkDir(Configuration conf, String uri, String remote) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(uri),conf);
		Path r_path = new Path(remote);
		if(!fs.isDirectory(r_path)){ //如果该文件夹已经存在，则提示文件夹已经存在
			fs.mkdirs(r_path);
			System.out.println("在hdfs上创建文件夹"+remote);
		}
		System.out.println("该文件夹已经存在！");
	}
	
	/** 
     * 创建文件 
     * @param conf 
     * @param uri 
     * @param remote
     * @throws IOException 
     */  
	public static void createFile(Configuration conf, String uri, String remote) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(uri),conf);
		Path r_path = new Path(remote);
		if(!fs.isFile(r_path)){
			fs.create(r_path);
			System.out.println("在hdfs上创建文件"+remote);
		}else{
			System.out.println("文件已经存在");
		}
		
		
	}
	/** 
     * 删除文件或者文件夹 
     * @param conf 
     * @param uri 
     * @param filePath 
     * @throws IOException 
     */  
	public static void delete(Configuration conf, String uri, String remote) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(uri),conf);
		Path r_path = new Path(remote);
		fs.deleteOnExit(r_path);
		System.out.println("Deleted:" + remote);
		fs.close();
	}
	
	/** 
     * 查看文件内容
     * @param conf 
     * @param uri 
     * @param remote
     * @throws IOException 
     */  
	public static void cat(Configuration conf, String uri, String remote) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(uri),conf);
		FSDataInputStream fsdir = null;
		System.out.println("cat:" + remote);
		Path r_path = new Path(remote);
		fsdir = fs.open(r_path);
		IOUtils.copyBytes(fsdir, System.out, 4096,false);
		IOUtils.closeStream(fsdir);
		fs.close();
	}
	
	
	/** 
     * 列出目录下面的文件 
     * @param conf 
     * @param uri 
     * @param folder 
     * @throws IOException 
     */  
    public static  void ls(Configuration conf , String uri , String folder) throws IOException {  
           Path path = new Path(folder);  
           FileSystem fs = FileSystem.get(URI.create(uri), conf);  
           FileStatus[] list = fs.listStatus(path);  
           System.out.println("ls: " + folder);  
           System.out.println("==========================================================");  
           for (FileStatus f : list) {  
               System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath(),f.isDirectory() , f.getLen());  
           }  
           System.out.println("==========================================================");  
           fs.close();  
   }  
 
    /**
    * @throws IOException 
    * @Title: bathUpload
    * @param localPath本地文件目录
    * @param hdfsPathHDFS文件目录
    * @throws
    */
    public static boolean batchUpload(Configuration conf,String uri,String localDir,
	    String hdfsPath) throws IOException {
	    boolean bln = false;
	    FileSystem fs = FileSystem.get(URI.create(uri), conf);
	    File f = new File(localDir);
	    try {
	    // 读取本地文件
	    	File[] files = f.listFiles();
	    if (files != null && files.length > 0) {
	    for (File file : files) {
	    	String fileName = file.getName();
	    	upLoad(conf,uri,localDir+"/"+fileName, hdfsPath);  //调用
	    }
	    	bln = true;
	    }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return bln;
	    }
}
