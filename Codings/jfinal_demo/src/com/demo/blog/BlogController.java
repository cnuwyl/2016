package com.demo.blog;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.thrift.generated.Hbase;

import com.demo.common.model.Blog;
import com.hbase.HBase;
import com.hdfs.Hdfs;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	Result result = null;
	
	public void index() {
		setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 10));
		
		System.out.println("http to hdfs!");
		upload();
		render("blog.html");
	}
	
	public void add() {
	}
	
	@Before(BlogValidator.class)
	public void save() {
		getModel(Blog.class).save();
		System.out.println("http to hbase!");
		hbase_insert();
		

		System.out.println(this.getPara("blog.title"));
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", Blog.me.findById(getParaToInt()));
	}
	
	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		Blog.me.deleteById(getParaToInt());
		redirect("/blog");
	}
	
	public void upload(){
		String uri = "hdfs://localhost:9000/";
		String local = "/home/wang/Workspace/hdfsfileop/file.txt";
		String remote = "hdfs://localhost:9000/Workspace/hdfsfileop/file.txt";
		Configuration conf = new Configuration();
		try {
			Hdfs.upLoad(conf,uri,local,remote);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //本地上传文件到hdfs
		System.out.println("upload over!");
		render("blog.html");
	}
	
	public void ls() throws IOException{
		String uri = "hdfs://localhost:9000/";
		Configuration conf = new Configuration();
		Hdfs.ls(conf,uri,"hdfs://localhost:9000/user");
	}
	
	public void hbase_insert() {

	    try {
	    	HBase hbase = new HBase();
	    	
	    	hbase.createTable("t1",new String[]{"cf1","cf2"});
	    	hbase.insterRow("t1", "rw1", "cf1", "username", this.getPara("blog.title"));  
	    	hbase.insterRow("t1", "rw1", "cf1", "password", this.getPara("blog.content")); 
	    	
		    //result = HBase.getData("t1", "rw1", "cf1", "username"); 
		    //HBase.getData("t1", "rw1", "cf1", "password"); 
		   // HBase.scanData("t1", "rw1","rw2");  //rw1开始处，rw2结束处
		    //HBase.deleRow("t1","rw1","cf1","username");  
		   // HBase.deleteTable("t1");  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    
	}  
	
	
	
	
	
	
}


