#!/bin/bash

source ./db.sh

check_env
echo "> Creating database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" -e "CREATE DATABASE IF NOT EXISTS \`service-architecture\`;"
then
  echo -e $RED"Error: Failed to create database, check your .env"$RESET
  exit 1
fi

echo "> Uploading database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" "service-architecture" < db.sql
then
  echo -e $RED"Error: Failed to execute db.sql, contact the maintainers."$RESET
  exit 1
fi

echo "> Populating database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" "service-architecture" < init.sql
then
  echo -e $RED"Error: Failed to execute init.sql, contact the maintainers."$RESET
  exit 1
fi

echo -e $GREEN"Database setup successful!"$RESET
