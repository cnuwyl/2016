package demo2;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class RandomSpout extends BaseRichSpout{
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private static String[] words = {"happy","excited","angry"};
	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
    this.collector = arg2;
	}
  
	 public void nextTuple() {
	    // TODO Auto-generated method stub
	    String word = words[new Random().nextInt(words.length)]; 
	    collector.emit(new Values(word));
	  }
  
  /* (non-Javadoc)
   * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
   */
	 public void declareOutputFields(OutputFieldsDeclarer arg0) {
    // TODO Auto-generated method stub
		 arg0.declare(new Fields("randomstring"));
	 }
}