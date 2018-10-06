package bigdata.p1UniqueScreenameCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountScreennamesMapper extends Mapper<LongWritable, Text, Text, Text>{
	@Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String cols[] = value.toString().split(",");
        // get user JSON
        String user = cols[10];
        // Split JSON by fields
        String[] user_properties = user.split(",");
        // Get screenname attrubite 
        String screenname_attr = user_properties[25];
        //Spit screenname to key value
        String[] screename_key_value = screenname_attr.split(":");
        // Get value
        String screename_value = screename_key_value[1];
        // Get screename
        String screenname = screename_value.substring(screename_value.indexOf("\""), screename_value.lastIndexOf("\""));
        
        
       context.write(new Text(screenname), new Text(screenname));

    }
}
