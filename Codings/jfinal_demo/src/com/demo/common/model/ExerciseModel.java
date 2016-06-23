package com.demo.common.model;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import com.hbase.HBase;
import com.hdfs.Hdfs;

public class ExerciseModel {
	
	public boolean uploadM(String u,String l,String r){
		String uri = u;
		String local = l;
		String remote = r;
		Configuration conf = new Configuration();
		try {
			boolean status =Hdfs.upLoad(conf,uri,local,remote);
			if(status) return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //本地上传文件到hdfs
		System.out.println("upload over!");
		return false;
	}
	
	public void ls() throws IOException{
		String uri = "hdfs://localhost:9000/";
		Configuration conf = new Configuration();
		Hdfs.ls(conf,uri,"hdfs://localhost:9000/user");
	}
	
	public String[] questionM() throws IOException{

		HBase hbase = new HBase();
		Result result ;		
		String[] value = new String[5];
		int n=0;
//		String[] option = new String[]{"question_one_title","question_one_value_1",
//				"question_one_value_2","question_one_value_3","question_one_value_4"};
						result= hbase.getData("single_question", "question_one", "options",null);
						
//						Cell[] cells = result.rawCells();  
//					    for(Cell cell:cells){  
//					        value[++n] = new String(CellUtil.cloneValue(cell)+" ");
//					        System.out.println("RowName:"+new String(CellUtil.cloneRow(cell))+" ");  
//					        System.out.println("Timetamp:"+cell.getTimestamp()+" ");  
//					        System.out.println("column Family:"+new String(CellUtil.cloneFamily(cell))+" ");  
//					        System.out.println("column Name:"+new String(CellUtil.cloneQualifier(cell))+" ");  
//					        System.out.println("value:"+new String(CellUtil.cloneValue(cell))+" ");  
//					    }  
						
						
						
					for(Cell keyValue:result.rawCells()){//迭代地取一行数据中的所有列
						
						System.out.println(keyValue.getValue().toString().equalsIgnoreCase(""));
						value[n++] = new String(keyValue.getValue())+" ";	
						System.out.println(result.rawCells().length+"1");
					}		
			
		return value;
		
		
	}
	public String answerM(String ans,String index) throws IOException{
		
		//判断对错并反馈结果
		String info = "";
		String answer = ans;
		boolean isCorrect = answer.equalsIgnoreCase("B");
		if(isCorrect){
			info ="你的答案是:"+answer+"  \n你答对了";			
		}else{
			info = "你的答案是:"+answer+"\t  正确答案是B，\n你答错了";
		}
		
		//往Hbase里面写数据。
		HBase hbase = new HBase();
		hbase.createTable("single_answer", new String[]{"option"});
		hbase.insterRow("single_answer", index+":single_answer" , "option", "answer", answer);
		return info;
		
	}
	

}
