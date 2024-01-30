FROM flink:1.14.6

# Install Python and other dependencies
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

# Set Python3 as default Python interpreter
RUN ln -s /usr/bin/python3 /usr/bin/python

# Install any Python dependencies
# RUN pip install <your_python_dependencies>

# Set the working directory
WORKDIR /opt/flink
