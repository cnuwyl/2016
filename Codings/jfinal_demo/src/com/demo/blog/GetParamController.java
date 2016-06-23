package com.demo.blog;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;

import com.hbase.HBase;
import com.jfinal.core.Controller;

public class GetParamController extends Controller {
	public void index(){
		renderFreeMarker("param.html");
	}
	public void get(){
		String id1 = getPara(0);
		String id2 = getPara(1);
		System.out.println(id1);
		System.out.println(id2);
		renderFreeMarker("get.html");
	}
	public void post() throws IOException{
		String username = getPara("username");
		String password = getPara("password");
		HBase hbase = new HBase();
		hbase.createTable("user",new String[]{"cf1","cf2"});
		hbase.insterRow("user", "rw1", "cf1", "username", username);
		hbase.insterRow("user", "rw1", "cf1", "password", password);
		Result result = hbase.getData("t1", "rw1", "cf1", username);
		ArrayList<String> list = new ArrayList<String>();
		for(Cell keyValue:result.rawCells()){
			list.add(new String(keyValue.getQualifier())+ "："+ new String(keyValue.getValue()));
		}
		setAttr("infolist", list);
	
		setAttr("msg","接收成功！");
		renderFreeMarker("post.html");
	}

	
}
