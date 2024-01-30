import json
import random
import time
from kafka import KafkaProducer

words = [
    "apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew", "imbe", "jackfruit", "kiwi", "lemon", "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "ugli", "vanilla", "watermelon", "xigua", "yellow watermelon", "zucchini"
]

# Kafka producer
producer = KafkaProducer(bootstrap_servers='localhost:9092')

# generate random data
while True:
    print('sending data')
    producer.send('words', value=str.encode(random.choice(words)))

    # time.sleep(1)


# from kafka import KafkaProducer
# from kafka.errors import KafkaError

# producer = KafkaProducer(
#     bootstrap_servers = "localhost:9092"
# )

# topic = "words"

# def on_success(metadata):
#   print(f"Message produced to topic '{metadata.topic}' at offset {metadata.offset}")

# def on_error(e):
#   print(f"Error sending message: {e}")

# # Produce asynchronously with callbacks
# for i in range(1, 11):
#   msg = f"Order with id #{i}"
#   future = producer.send(
#     topic,
#     value=str.encode(msg)
#   )
#   future.add_callback(on_success)
#   future.add_errback(on_error)

# producer.flush()
# producer.close()