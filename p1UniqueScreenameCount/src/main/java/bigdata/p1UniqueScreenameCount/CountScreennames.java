package bigdata.p1UniqueScreenameCount;

import org.apache.hadoop.fs.Path;
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
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(bigdata.p1UniqueScreenameCount.CountScreennamesMapper.class);
        job.setReducerClass(bigdata.p1UniqueScreenameCount.CountScreennamesReducer.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
