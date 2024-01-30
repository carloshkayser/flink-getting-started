name := "KafkaWordCount"

version := "1.0"

scalaVersion := "2.12.15"

libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala" % "1.14.6",
  "org.apache.flink" %% "flink-streaming-scala" % "1.14.6",
  "org.apache.flink" %% "flink-connector-kafka" % "1.14.6"
)