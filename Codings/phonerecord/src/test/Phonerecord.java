package test;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

public class Phonerecord {
private static Configuration config;
private static Connection connection;
private static Admin admin;
@Test
public void test1() throws Exception{
//	config = HBaseConfiguration.create();
////		System.out.println("aaaaaaaaaa");
//config.set("hbase.zookeeper.quorum", "localhost ");
//config.set("hbase.zookeeper.property.clientport", "2181 ");
//System.out.println(config.get("hbase.zookeeper.property.clientport"));
//connection = ConnectionFactory.createConnection(config);  
//admin = connection.getAdmin();
//
//	String table = "mytable1";
//       TableName tn = TableName.valueOf(table);
//		if(admin.tableExists(tn)){
//			admin.disableTables(table);
//			admin.deleteTables(table);
//			System.out.println("wwwww");
//		}else{	
//			@SuppressWarnings("deprecation")
//			HTableDescriptor t = new HTableDescriptor(tn);
//		    HColumnDescriptor hcd = new HColumnDescriptor("good".getBytes());
//		    t.addFamily(hcd);
//			admin.createTable(t);
//			System.out.println("sssssssssss");
//		}
//		System.out.println("xxxxxxxxxxxxx");
//		admin.close();
//		
//  ////////////////////////////////////////////////////////////////////////////////////////////
		Configuration config = HBaseConfiguration.create();
		config.set( "hbase.zookeeper.quorum", "localhost" );
		config.set( "hbase.zookeeper.property.clientport", "2181" );
		//config.set("zookeeper.znode.parent", "/hbase-unsecure"); // this is what most people miss :) 
		HBaseAdmin.checkHBaseAvailable( config );
		HTable    t    = new HTable( config, "test_hbase" );
		Scan    s    = new Scan();
		//s.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("name")); 
		ResultScanner rs = t.getScanner( s );
		try{
		    for ( Result r : rs )
		    {
		        for ( Cell cell : r.rawCells() )
		        {
		            System.out.println( "RowName:" + new String( CellUtil.cloneRow( cell ) ) + " " );
		            System.out.println( "Timetamp:" + cell.getTimestamp() + " " );
		            System.out.println( "column Family:" + new String( CellUtil.cloneFamily( cell ) ) + " " );
		            System.out.println( "row Name:" + new String( CellUtil.cloneQualifier( cell ) ) + " " );
		            System.out.println( "value:" + new String(CellUtil.cloneValue( cell ) ) + " " );
		        }
		    }
		} finally {
		    t.close();
		}
		
		System.out.println( "Done!" );
	
	}
}
