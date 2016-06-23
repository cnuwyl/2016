package demo1;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

    public class WordCountTopo {
        public static void main(String[] args) throws Exception {
            if (args.length != 2) {
                System.err.println("Usage: inputPaht timeOffset");
                System.err.println("such as : java -jar WordCount.jar inpath 2");
                System.exit(2);
            }
            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("word-reader", new WordReader());
            builder.setBolt("word-spilter", new WordSpliter()).shuffleGrouping("word-reader");
            builder.setBolt("word-counter", new WordCounter()).shuffleGrouping("word-spilter");
            String inputPaht = args[0];
            String timeOffset = args[1];
            Config conf = new Config();
            conf.put("INPUT_PATH", inputPaht);
            conf.put("TIME_OFFSET", timeOffset);
            conf.setDebug(false);
      
            if (args.length == 2) {
            	conf.setMaxTaskParallelism(3);
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("WordCount", conf, builder.createTopology()); 
              }
              else {
//                 conf.setNumWorkers(3);
//                 StormSubmitter.submitTopology("first", conf, builder.createTopology());
                //StormSubmitter.submitTopologyWithProgressBar("first", conf, builder.createTopology());
             }             
        }
    }

