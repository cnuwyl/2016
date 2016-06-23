package recommendfriend;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import test.JobRun;
import test.MyMapper;
import test.MyReducer;

public class RunJob {
	public void start(){
		Configuration conf = new Configuration();  //new一个配置
		conf.set("mapred.job.tracker", "localhost:9001"); //设置mapreduce的主机以及端口号

		try {
			Job job;
			job = new Job(conf,"wordount");
			job.setJarByClass(RunJob.class);   //提交方式为jar时必须设置好此类,主程序的入口
			job.setMapperClass(RecommMapper.class);  //设置Mapper业务类
			job.setReducerClass(RecommReducer.class); //设置Reducer业务类
			job.setMapOutputKeyClass(Text.class);  //设置Map的key输出类型
			job.setMapOutputValueClass(Text.class); //设置Map的value输出类型
			
			job.setOutputKeyClass(Text.class);    //设置job的key输出数据类型
	        job.setOutputValueClass(Text.class);   //设置job的value输出数据类型
			job.setNumReduceTasks(1);   //设置reducer的数量
			
			FileInputFormat.addInputPath(job,new Path("input/data.txt"));  //设置输入文件的路径
			FileOutputFormat.setOutputPath(job,new Path("output/test2"));   //设置输出文件的路径
			//这一阶段进行map reduce的过程 ...
			
			System.out.println("finished "+job.getJobName() +" job");
			System.exit(job.waitForCompletion(true)? 0:1);   //完成任务后正常推出
			//完成map reduce过程
		} catch (IOException e) {		
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
		new RunJob().start();
	}

}
