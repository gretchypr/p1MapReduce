package bigdata.p1WordOccurenceCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CountWordOccurenceMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	 @Override
	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		 	// Get the received JSON
		 	JsonNode twitter_json = new ObjectMapper().readTree(value.toString());
	        String newKey = "";
	        // Get full text
	        String full_text = twitter_json.get("extended_tweet").get("full_text").textValue();
	        
	        if(full_text.contains("Trump") || full_text.contains("trump")) {
	        	newKey = "Trump";
	        }
	        if(full_text.contains("Flu") || full_text.contains("flu")) {
	        	newKey = "Flu";
	        }
	        if(full_text.contains("Zika") || full_text.contains("zika")) {
	        	newKey = "Zika";
	        }
	        if(full_text.contains("Diarrhea") || full_text.contains("diarrhea")) {
	        	newKey = "Diarrhea";
	        }
	        if(full_text.contains("Ebola") || full_text.contains("ebola")) {
	        	newKey = "Ebola";
	        }
	        if(full_text.contains("Headache") || full_text.contains("headache")) {
	        	newKey = "Headache";
	        }
	        if(full_text.contains("Measles") || full_text.contains("Measles")) {
	        	newKey = "Measles";
	        }
	        if(!newKey.equals(""))
	        	context.write(new Text(newKey), new IntWritable(1));

	    }
}
