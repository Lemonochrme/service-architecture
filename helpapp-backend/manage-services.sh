#!/bin/bash

# List of services
services=("user-service" "request-service" "volunteer-service" "feedback-service" "administration-service")

# Function to start services
start_services() {
  for service in "${services[@]}"; do
    echo "Starting $service..."
    mvn spring-boot:run -pl $service &
  done
}

# Function to stop services
stop_services() {
  echo "Stopping all services..."
  pkill -f 'mvn spring-boot:run'
  for port in {8080..8085}; do
    echo "Killing process on port $port..."
    fuser -k $port/tcp
  done
}

# Check command line arguments
if [ "$1" == "start" ]; then
  start_services
elif [ "$1" == "stop" ]; then
  stop_services
else
  echo "Usage: $0 {start|stop}"
fi