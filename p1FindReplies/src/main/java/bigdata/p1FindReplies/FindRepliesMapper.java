package bigdata.p1FindReplies;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FindRepliesMapper extends Mapper<LongWritable, Text, Text, Text>{
	@Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// Get the received JSON
	 	JsonNode twitter_json = new ObjectMapper().readTree(value.toString());
	 	String reply_id = twitter_json.get("in_reply_to_status_id_str").textValue();
    	String reply_message_id = twitter_json.get("id_str").textValue();
        // Check if retweet
        if(reply_id != null) {
        	context.write(new Text(reply_id), new Text(reply_message_id));
        }
        else {
        	context.write(new Text(reply_message_id), new Text("A"));
        }

    }

}
