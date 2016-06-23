package com.demo.blog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;

import com.demo.common.model.ExerciseModel;
import com.hbase.HBase;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

public class ExerciseController extends Controller{
	private String questionIndex = null;
	public void index() throws IOException{
		questions();
		this.render("index.html");
	}
	
	
	public void upload(){
		String uri = "hdfs://localhost:9000/";
		
		
		String local = "/home/wang/Workspace/hdfsfileop/file.txt";  //this.getPara("local");
		//String local = this.getPara("file");
		
		System.out.println(local);
		String remote = "hdfs://localhost:9000/Workspace/hdfsfileop/file.txt";  //this.getPara("remote");
		ExerciseModel em = new ExerciseModel();
		em.uploadM(uri, local, remote);
		
		this.setAttr("path", remote);
		this.render("listFiles.html");
	}


   
	
	
	public void questions() throws IOException{
		ExerciseModel em = new ExerciseModel();
		String [] value = em.questionM();
		questionIndex = value[0].substring(0,1);
		String question_one_title = value[4];
		String question_one_value_1=value[0];
		String question_one_value_2=value[1];
		String question_one_value_3=value[2];
		String question_one_value_4=value[3];
		
		this.setAttr("question_one_title", question_one_title);
		this.setAttr("question_one_value_1", question_one_value_1);
		this.setAttr("question_one_value_2", question_one_value_2);
		this.setAttr("question_one_value_3", question_one_value_3);
		this.setAttr("question_one_value_4", question_one_value_4);
	
	}
	
	

	
	public void answer() throws IOException{
		ExerciseModel em = new ExerciseModel();
		String ans = this.getPara("single1").substring(0, 1);
		String quesIndex = questionIndex;
		String info = em.answerM(ans,quesIndex);
		
		setAttr("info",info);
		this.render("answer.html");
	}
	
	
	
	
}
