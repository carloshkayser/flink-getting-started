# Apache Flink Getting Started

## Prerequisites

- Docker
- Docker Compose

## Flink Deployment

```bash
docker compose up -d --scale taskmanager=3
```

<!-- docker exec -it jobmanager bash

cd /examples

flink run -py word_count.py -->

## Flink Built-in Examples

> *Coming soon*

## Redpanda Tutorial

```bash
docker exec -it redpanda-1 rpk cluster info

# creating a topic in Redpanda
docker exec -it redpanda-1 rpk topic create words

# describe the topic
docker exec -it redpanda-1 rpk topic describe words

# produce a message to the topic
docker exec -it redpanda-1 rpk topic produce words

# consume a message from the topic
docker exec -it redpanda-1 rpk topic consume words

# create a topic for the word count
docker exec -it redpanda-1 rpk topic create words-count

# execute job
docker exec -it jobmanager flink run -py /examples/redpanda_word_count.py

# execute scala job
docker exec -it jobmanager flink run -c io.github.carloshkayser.KafkaWordCount /examples/scala/kafkawordcount/target/scala-2.12/KafkaWordCount-assembly-1.0.jar

# alternatively, you can run the job from the container
docker exec -it jobmanager bash
flink run -c io.github.carloshkayser.KafkaWordCount /examples/scala/kafkawordcount/target/scala-2.12/KafkaWordCount-assembly-1.0.jar

# consume the word count
docker exec -it redpanda-1 rpk topic consume words-count
```

### Reference:
- https://redpanda.com/blog/apache-flink-redpanda-real-time-word-count-application
