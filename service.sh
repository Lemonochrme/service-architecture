#!/bin/bash

source ./db/db.sh

SCRIPT_PATH=$(realpath "$0")
SCRIPT_DIR=$(dirname "${SCRIPT_PATH}")
services=()
ports=()

list_services() {
  base_port=8081
  cd "$SCRIPT_DIR/helpapp-backend"
  for dir in *-service; do
    if [[ -d "$dir" ]]; then
      services+=("$dir")
      ports+=("$base_port")
      ((base_port++))
    fi
  done
  if [[ ${#services[@]} -eq 0 ]]; then
      echo -e $RED"Error: Found no microservices:\nAre you in the right directory ?"$RESET
      exit 1
  fi
}

check_services() {
  while true; do
    all_ports_ready=true
    for port in "${ports[@]}"; do
      if ! nc -z localhost "$port"; then
        all_ports_ready=false
        break
      fi
    done

    if $all_ports_ready; then
      break
    fi

    sleep 1
  done
}

# Function to start services
start_services() {
  echo "" > ../process.tmp
  cd "$SCRIPT_DIR/helpapp-backend" || exit 1
  for service in "${services[@]}"; do
    echo "> Starting $service..."
    mvn spring-boot:run -pl "$service" > /dev/null 2>&1 &
    echo -e "$!" >> ../process.tmp
  done
  echo "> Waiting for the services to establish connection..."
  check_services
  echo -e $GREEN"All services are now running!"$RESET
}

# Function to stop services
stop_services() {
  echo "> Stopping all services..."
  pkill -f 'mvn spring-boot:run'
  for port in "${ports[@]}"; do
    echo -e "\t> Killing process on port $port..."
    fuser -k $port/tcp
  done
}

# Check command line arguments
if [ "$1" == "start" ]; then
  list_services
  start_services
elif [ "$1" == "stop" ]; then
  list_services
  stop_services
elif [ "$1" == "compile" ]; then
  cd "$SCRIPT_DIR/helpapp-backend" || exit 1
  mvn clean install
else
  echo -e $RED"Usage: $0 {compile|start|stop}"$RESET
fi