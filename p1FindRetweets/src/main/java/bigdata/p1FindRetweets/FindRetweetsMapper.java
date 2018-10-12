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
	 	String message = twitter_json.get("extended_tweet").get("full_text").textValue();
        // Check if retweet
        if(twitter_json.has("retweeted_status")) {
        	System.out.println("TRUE");
        	String retweeted_message = twitter_json.get("retweeted_status").get("extended_tweet").get("full_text").textValue();
        	context.write(new Text(retweeted_message), new Text(message));
        }
        else {
        	context.write(new Text(message), new Text("0"));
        }

    }
}
