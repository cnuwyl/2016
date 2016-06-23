package test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class JobRun {
	
	public void start(){
		Configuration conf = new Configuration();  //new一个配置
		conf.set("mapred.job.tracker", "localhost:9001"); //设置mapreduce的主机以及端口号

		try {
			Job job;
			job = new Job(conf,"wordount");
			job.setJarByClass(JobRun.class);   //提交方式为jar时必须设置好此类,主程序的入口
			job.setMapperClass(MyMapper.class);  //设置Mapper业务类
			job.setReducerClass(MyReducer.class); //设置Reducer业务类
			job.setMapOutputKeyClass(Text.class);  //设置Map的key输出类型
			job.setMapOutputValueClass(IntWritable.class); //设置Map的value输出类型
			
			job.setOutputKeyClass(Text.class);    //设置job的key输出数据类型
	        job.setOutputValueClass(IntWritable.class);   //设置job的value输出数据类型
			job.setNumReduceTasks(1);   //设置reducer的数量
			
			FileInputFormat.addInputPath(job,new Path("input/data.txt"));  //设置输入文件的路径
			FileOutputFormat.setOutputPath(job,new Path("output/test"));   //设置输出文件的路径
			System.out.println("finished job");
			System.exit(job.waitForCompletion(true)? 0:1);   //完成任务后正常推出
			
		} catch (IOException e) {		
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void main(String [] args){
		new JobRun().start();
	}
}
