package bigdata.p1UniqueScreenameCount;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RawLocalFileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CountScreennames {

	public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: CountScreennames <input path> <output path>");
            System.exit(-1);
        }
        Job job = new Job();
        job.setJarByClass(bigdata.p1UniqueScreenameCount.CountScreennames.class);
        job.setJobName("Find all the unique screennames");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path("/output/inter"));

        job.setMapperClass(bigdata.p1UniqueScreenameCount.CountScreennamesMapper.class);
        job.setReducerClass(bigdata.p1UniqueScreenameCount.CountScreennamesReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable .class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.waitForCompletion(true);
        
        Job job2 = new Job();
        job2.setJarByClass(bigdata.p1UniqueScreenameCount.CountScreennames.class);
        job2.setJobName("Find all the unique screennames");

        FileInputFormat.addInputPath(job2,  new Path("output/inter/part*"));
        FileOutputFormat.setOutputPath(job2, new Path(args[1]));

        job2.setMapperClass(bigdata.p1UniqueScreenameCount.CountScreennamesMapper2.class);
        job2.setReducerClass(bigdata.p1UniqueScreenameCount.CountScreennamesReducer2.class);

        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(Text .class);
        
        job2.setOutputKeyClass(IntWritable.class);
        job2.setOutputValueClass(Text.class);
        
        if(job2.waitForCompletion(true)) {
    		FileSystem fs = new RawLocalFileSystem();
    		fs.delete(new Path("output/inter/part*"), true);
    		fs.close();
        	System.exit(0);
        }
        
    }

}
