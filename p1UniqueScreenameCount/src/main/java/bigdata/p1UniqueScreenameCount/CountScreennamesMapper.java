package bigdata.p1UniqueScreenameCount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CountScreennamesMapper extends Mapper<LongWritable, Text, Text, Text>{
	@Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// Get the received JSON
	 	JsonNode twitter_json = new ObjectMapper().readTree(value.toString());
        // Get screename
        String screenname = twitter_json.get("user").get("screen_name").textValue();
                
       context.write(new Text("user_name"), new Text(screenname));

    }
}
