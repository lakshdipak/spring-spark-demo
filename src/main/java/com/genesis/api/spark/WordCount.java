package com.genesis.api.spark;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

@Component
public class WordCount {

    @Autowired
    private SparkSession sparkSession;

    public List<Count> count() {
        String input = "hello world hello hello hello kaya hal hai chacha ji ji";
        String[] _words = input.split(" ");
        List<Word> words = Arrays.stream(_words).map(Word::new).collect(Collectors.toList());
        Dataset<Row> dataFrame = sparkSession.createDataFrame(words, Word.class);
        //dataFrame.show();
      /*  dataFrame.selectExpr("CAST(word AS STRING) AS key", "to_json(struct(*)) AS value")
                .writeStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("topic", "josn_data_topic")
                .start()
                .stop();*/

        //StructType structType = dataFrame.schema();

        RelationalGroupedDataset groupedDataset = dataFrame.groupBy(col("word"));
        // groupedDataset.count().show();
        List<Row> rows = groupedDataset.count().collectAsList();
        //JavaConversions.asScalaBuffer(words)).count();
        return rows.stream().map(new Function<Row, Count>() {
            @Override
            public Count apply(Row row) {
                return new Count(row.getString(0), row.getLong(1));
            }
        }).collect(Collectors.toList());
    }
}