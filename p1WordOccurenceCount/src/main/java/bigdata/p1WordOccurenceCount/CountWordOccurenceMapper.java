package bigdata.p1WordOccurenceCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class CountWordOccurenceMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	 @Override
	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

	        String cols[] = value.toString().split(",");
	        String newKey = "";
	        // get the state name, located in column
	        String extended_message = cols[17];
	        // Split message as well (JSON)
	        String[] ext_msg_properties = extended_message.split(",");
	        // Get full text
	        String full_text = ext_msg_properties[0];
	        
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
