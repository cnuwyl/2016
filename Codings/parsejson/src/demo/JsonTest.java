package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTest {

public static void main(String[] args) throws IOException {
	 JSonSet jsonSet=new JSonSet();
	 String path = "/home/wang/os_course_exercise_library/data/json/15/1458.json";
	 String sets=readFile(path);//获得json文件的内容
	 JSONObject jo=JSONObject.fromObject(sets);//格式化成json对象
	 jsonSet.setStatus(jo.getString("status"));
	 jsonSet.setKnowledge(jo.getString("knowledge"));
	 jsonSet.setDegree_of_diff(jo.getString("degree_of_difficulty"));
	 jsonSet.setExplain(jo.getString("explain"));
	 jsonSet.setQuestion(jo.getString("question"));
	 jsonSet.setSource(jo.getString("source"));
	 jsonSet.setAnswer(jo.getString("answer"));
	 jsonSet.setType(jo.getString("type"));
	 jsonSet.setQ_number(jo.getString("q_number"));
	 
	 System.out.println("\n\n///////////////////////////////////\n获取json文件属性：");
	 System.out.println("status:"+jo.getString("status"));
	 System.out.println("knowledge:"+jo.getJSONArray("knowledge").get(0));
	 System.out.println("degree_of_difficulty:"+jo.getString("degree_of_difficulty"));
	 System.out.println("explain:"+jo.getString("explain"));
	 System.out.println("question:"+jo.getString("question"));
	 System.out.println("source:"+jo.getString("source"));
	 System.out.println("answer:"+jo.getString("answer"));
	 System.out.println("type:"+jo.getString("type"));
	 System.out.println("q_number:"+jo.getString("q_number"));
	 JSONArray arr = jo.getJSONArray("options");
	 System.out.println("option:");
		  for(int i = 0; i<arr.size();i++){
			  System.out.println("	"+arr.get(i));
		  }
	//将json格式的字符串写入json文件
		 System.out.println("\n\n文件写入："+writeFile(new String("test.json"),sets) );
		  
//	String json =    "{'response':{'data':[{'address':'南京市游乐园','province':'江苏','district':'玄武区','city':'南京'}]},'status':'ok'}";
//	JSONObject jsonObject = JSONObject.fromObject(json);
//	String response = jsonObject.getString("response");
//	String status = jsonObject.getString("status");
//	JSONObject jsonObject2 = JSONObject.fromObject(response);
//	
//	JSONArray data = jsonObject2.getJSONArray("data");
//	for(int i=0;i<data.size();i++){
//	    String s = data.getString(i);
//	    JSONObject data2 = JSONObject.fromObject(s);
//	    System.out.println(data2.getString("address"));
//	    System.out.println(data2.getString("province"));
//	    System.out.println(data2.getString("district"));
//	    System.out.println(data2.getString("city"));
//	}
//	System.out.println(status);
}

	//读文件，返回字符串
	public static String readFile(String path){
	    File file = new File(path);
	    BufferedReader reader = null;
	    String laststr = "";
	    try {
	     //System.out.println("以行为单位读取文件内容，一次读一整行：");
	     reader = new BufferedReader(new FileReader(file));
	     String tempString = null;
	     int line = 1;
	     //一次读入一行，直到读入null为文件结束
	     while ((tempString = reader.readLine()) != null) {
	      //显示行号
	      System.out.println("line " + line + ": " + tempString);
	      laststr = laststr + tempString;
	      line ++;
	     }
	     reader.close();
	    } catch (IOException e) {
	     e.printStackTrace();
	    } finally {
	     if (reader != null) {
	      try {
	       reader.close();
	      } catch (IOException e1) {
	      }
	     }
	    }
	    return laststr;
	}
	
	   //把json格式的字符串写到文件
		public static boolean writeFile(String filePath, String sets) throws IOException {
		    FileWriter fw = new FileWriter(filePath);
		    PrintWriter out = new PrintWriter(fw);
		    out.write(sets);
		    out.println();
		    fw.close();
		    out.close();
			return true;
		   }

}