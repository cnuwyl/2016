package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;



public class ForkPy {

	public static void main(String[] args) throws IOException, InterruptedException {
		

		//	第一种方法：
	    //jython引入python， java可通过它调用python脚本。缺点是jython带的库比较少，第三方模块无法引入
		//执行一个py文件	 
		  PythonInterpreter interpreter = new PythonInterpreter();  
		  interpreter.execfile("./hello.py");  
		    
		  //得到py文件里面的函数模块add
		  interpreter.execfile("./adder.py");  
		  PyFunction func = interpreter.get("add",PyFunction.class); 
		  //传参数 a,b
		  int a = 2016, b = 1;
		  PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
		  System.out.println(a+" + "+b+" = " + pyobj.toString());
		 
		  //直接执行py命令
		  interpreter.exec("import sys\nprint sys.path");

		  
		  //第二种方法：
		  //这种方法可以得到当前系统环境下的所有python可用的模块库，可用
		  try{  
              Process pr = Runtime.getRuntime().exec("python test.py");  
                
              BufferedReader in = new BufferedReader(new  
                      InputStreamReader(pr.getInputStream()));  
              String line;  
              while ((line = in.readLine()) != null) {  
                  System.out.println(line);  
              }  
              in.close();  
              pr.waitFor();  
              System.out.println("end");  
      } catch (Exception e){  
                  e.printStackTrace();  
              }  
      }

}
