package com.hbase;


import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.*;  
import org.apache.hadoop.hbase.client.*;  
import org.apache.hadoop.hbase.util.Bytes;  
  

import java.io.IOException;  
  
/** 
 * Created by wang
 */  
public class HBase {  
	public static Configuration configuration;  
	public static Connection connection;  
	public static Admin admin;  
	public HBase(){
		createHbaseConnection();
	}
	//初始化链接  
	public  void createHbaseConnection(){
	    configuration = HBaseConfiguration.create();  
	    configuration.set("hbase.zookeeper.quorum","localhost");  
	    configuration.set("hbase.zookeeper.property.clientPort","2181");  
	    configuration.set("zookeeper.znode.parent","/hbase");  

	    try {  
	        connection = ConnectionFactory.createConnection(configuration);  
	        admin = connection.getAdmin();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}  

	//关闭连接  
	public void closeHbaseConnection(){
	    try {  
	        if(null != admin)  
	            admin.close();  
	        if(null != connection)  
	            connection.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  

	}  



//建表  
public void createTable(String tableNmae,String[] colFamily) throws IOException {  

    TableName tableName = TableName.valueOf(tableNmae);  

    if(admin.tableExists(tableName)){  
        System.out.println("talbe is exists!");
    }else {
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);  
        for(String col:colFamily){  
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);  
            hTableDescriptor.addFamily(hColumnDescriptor);  
        }  
        admin.createTable(hTableDescriptor);  
    }  
   // closeHbaseConnection();  
}  

//删表  
public void deleteTable(String tableName) throws IOException {   
    TableName tn = TableName.valueOf(tableName);  
    if (admin.tableExists(tn)) {  
        admin.disableTable(tn);  
        admin.deleteTable(tn);  
    }  
    //closeHbaseConnection();  
    System.out.print("deleteTable over!");
}  

//查看已有表  
public void listTables() throws IOException {   
    HTableDescriptor hTableDescriptors[] = admin.listTables();  
    for(HTableDescriptor hTableDescriptor :hTableDescriptors){  
        System.out.println(hTableDescriptor.getNameAsString());  
    }
    //closeHbaseConnection();  
}  

//插入数据  
public void insterRow(String tableName,String rowkey,String colFamily,String col,String val) throws IOException {  
    Table table = connection.getTable(TableName.valueOf(tableName));  
    Put put = new Put(Bytes.toBytes(rowkey));  
    put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col), Bytes.toBytes(val));  
    table.put(put);  

    //批量插入  
   /* List<Put> putList = new ArrayList<Put>(); 
    puts.add(put); 
    table.put(putList);*/  
    table.close();  
   // closeHbaseConnection();  
    
}  

//删除数据  
public void deleRow(String tableName,String rowkey,String colFamily,String col) throws IOException {  
    Table table = connection.getTable(TableName.valueOf(tableName));  
    Delete delete = new Delete(Bytes.toBytes(rowkey));  
    //删除指定列族  
    //delete.addFamily(Bytes.toBytes(colFamily));  
    //删除指定列  
    //delete.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));  
    table.delete(delete);  
    //批量删除  
   /* List<Delete> deleteList = new ArrayList<Delete>(); 
    deleteList.add(delete); 
    table.delete(deleteList);*/  
    table.close();  
   // closeHbaseConnection();  
    System.out.println("deleRow over!");
}  

//根据rowkey查找数据  
public  Result getData(String tableName,String rowkey,String colFamily,String col)throws  IOException{   
    Table table = connection.getTable(TableName.valueOf(tableName));
    Get get = new Get(Bytes.toBytes(rowkey));
    //获取指定列族数据  
    //get.addFamily(Bytes.toBytes(colFamily));  
    //获取指定列数据  
    //get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));  
    Result result = table.get(get);
    
    showCell(result);  
    table.close();  
    //closeHbaseConnection();  
    return result;
}  

//格式化输出  
public void showCell(Result result){
    Cell[] cells = result.rawCells();  
    for(Cell cell:cells){  
        System.out.println("RowName:"+new String(CellUtil.cloneRow(cell))+" ");  
        System.out.println("Timetamp:"+cell.getTimestamp()+" ");  
        System.out.println("column Family:"+new String(CellUtil.cloneFamily(cell))+" ");  
        System.out.println("column Name:"+new String(CellUtil.cloneQualifier(cell))+" ");  
        System.out.println("value:"+new String(CellUtil.cloneValue(cell))+" ");  
    }  
}  

//批量查找数据  
public void scanData(String tableName,String startRow,String stopRow)throws IOException{    
    Table table = connection.getTable(TableName.valueOf(tableName));  
    Scan scan = new Scan();  
    scan.setStartRow(Bytes.toBytes(startRow));  
    scan.setStopRow(Bytes.toBytes(stopRow));  
    ResultScanner resultScanner = table.getScanner(scan);  
    for(Result result : resultScanner){
        showCell(result);
    }
    table.close();  
   // closeHbaseConnection();  
}  
}  