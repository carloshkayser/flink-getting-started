package io.github.carloshkayser

import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.sink.KafkaSink
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.streaming.api.scala._

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.connector.kafka.sink.{KafkaRecordSerializationSchema, KafkaSink}
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.streaming.api.scala._

import java.time.Duration
import java.util.Properties

object KafkaWordCount {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // env.setParallelism(1)

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "redpanda-1:29092")
    properties.setProperty("group.id", "test")

    // Adding KafkaSource
    val source = KafkaSource
      .builder[String]
      .setBootstrapServers("redpanda-1:29092")
      .setTopics("words")
      // .setStartingOffsets(OffsetsInitializer.earliest())
      .setStartingOffsets(OffsetsInitializer.latest())
      .setValueOnlyDeserializer(new SimpleStringSchema)
      .build()

    val stream = env.fromSource(source, WatermarkStrategy.noWatermarks[String](), "Redpanda Source")

    val counts = stream
      .flatMap(_.toLowerCase.split("\\W+"))
      .map((_, 1))
      .keyBy(0)
      .sum(1)

    // counts.print()

    // Before sending Kafka we need to seralize our value
    val serializer = KafkaRecordSerializationSchema.builder()
    .setValueSerializationSchema(new SimpleStringSchema())
    .setTopic("words-count")
    .build()

    // Adding KafkaSink
    val kafkaSink = KafkaSink.builder()
    .setBootstrapServers("redpanda-1:29092")
    .setRecordSerializer(serializer)
    .build()

    counts
      .map(_.toString)
      .sinkTo(kafkaSink)

    env.execute("Kafka WordCount")
  }
}