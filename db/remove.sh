#!/bin/bash

# Checks the .env file
if [ -f .env ]; then
  source .env
  user="$SERVICE_ARCHITECTURE_USER"
  pwd="$SERVICE_ARCHITECTURE_PASSWORD"
else
  echo ".env file not found. Please create one with USER and PASSWORD variables."
  exit 1
fi


echo "Removing database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" -e "DROP DATABASE \`service-architecture\`;"
then
  echo "Error: Failed to create database, check your .env"
  exit 1
else
  echo "Database removed successfully!"
fi
