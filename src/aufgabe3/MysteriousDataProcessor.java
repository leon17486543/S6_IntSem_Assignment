package aufgabe3;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;


public class MysteriousDataProcessor {

    public static class MyMapper extends Mapper<Object, Text, Text, Text> {
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String input = value.toString().toLowerCase();
            for (int index = 0; index < input.length() - 1; index++) {
                Text substr = new Text(input.substring(index, index + 2));
                context.write(value, substr);
            }
        }
    }
    public static class MyReducer extends Reducer<Text, Text, Text, IntWritable> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            HashSet<String> set = new HashSet<String>();
            for (Text substr : values) {
                set.add(substr.toString());
            }
            context.write(key, new IntWritable(set.size()));
        }
    }

    public static void main(String[] args) {
        String mapOut = "";

        HashSet<String> set = new HashSet<>();

        String values[] = {"Leon Kolb", "Plankstadt nora Systems GmbH"};

        mapOut += "Map: [";

        for(int i = 0; i< 2; i++){
            String input = values[i].toString().toLowerCase();
            for (int index = 0; index < input.length() - 1; index++) {
                String key = input.substring(index, index + 2);
                mapOut += "{" + values[i] + ": " + key + "}, ";

                set.add(key);
            }
        }
        mapOut += "]";

        System.out.println(mapOut);

        System.out.println(set + " " + set.size());




    }
}