package bigdata.p1FindCountMessagesByUser;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FindCountMessagesByUserMapper extends Mapper<LongWritable, Text, Text, Text>{
	@Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// Get the received JSON
	 	JsonNode twitter_json = new ObjectMapper().readTree(value.toString());
	 	String screen_name = twitter_json.get("user").get("screen_name").textValue();
	 	String tweet_id = twitter_json.get("id_str").textValue();

        context.write(new Text(screen_name), new Text(tweet_id));
    }
}
