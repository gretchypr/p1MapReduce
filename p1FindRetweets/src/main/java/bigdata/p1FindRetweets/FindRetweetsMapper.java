package bigdata.p1FindRetweets;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FindRetweetsMapper extends Mapper<LongWritable, Text, Text, Text>{
	@Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// Get the received JSON
	 	JsonNode twitter_json = new ObjectMapper().readTree(value.toString());
	 	String id = twitter_json.get("id_str").textValue();
        // Check if retweet
        if(twitter_json.has("retweeted_status")) {
        	String retweeted_id = twitter_json.get("retweeted_status").get("id_str").textValue();
        	context.write(new Text(retweeted_id), new Text(id));
        }
        else {
        	context.write(new Text(id), new Text("0"));
        }

    }
}
