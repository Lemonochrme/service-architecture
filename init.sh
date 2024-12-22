#!/bin/bash

source ./db/db.sh

generate_properties()
{
    url="$1/src/main/resources/application.properties"
    mkdir -p "$1/src/main/resources"
    echo -e "server.port=$port
spring.datasource.url=jdbc:mysql://localhost:3306/service-architecture
spring.datasource.username=$user
spring.datasource.password=$pwd
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect" > "$url"
    ((port++))
}

## CREATING DATABASE
check_env
echo "> Creating database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" -e "CREATE DATABASE IF NOT EXISTS \`service-architecture\`;"
then
  echo -e $RED"Error: Failed to create database, check your .env"$RESET
  exit 1
fi

echo "> Uploading database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" "service-architecture" < db/db.sql
then
  echo -e $RED"Error: Failed to execute db.sql, contact the maintainers."$RESET
  exit 1
fi

echo "> Populating database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" "service-architecture" < db/init.sql
then
  echo -e $RED"Error: Failed to execute init.sql, contact the maintainers."$RESET
  exit 1
fi
echo -e $GREEN"Database setup successful!"$RESET

## CREATING application.properties
services=()
port=8081
echo "> Checking Microservices..."
for dir in backend/*-service; do
  if [[ -d "$dir" ]]; then
    services+=("$dir")
  fi
done
if [[ ${#services[@]} -gt 0 ]]; then
    echo -e $GREEN"Found ${#services[@]} Microservices!"$RESET
else
    echo -e $RED"Error: Found no microservices:\nAre you in the right directory ?"$RESET
    exit 1
fi

count=0
for service in "${services[@]}"; do
    ((count++))
    echo -ne "\r> Generating 'application.properties'... [$count/${#services[@]}]"
    generate_properties "$service"
done

port=8081
generated_js="/* !! THIS IS A GENERATED FILE, DO NOT MODIFY !! */\n\n"
echo -e "\n> Generating 'generated.js'..."
for service in "${services[@]}"; do
    service_name=$(basename "$service")        # Remove the last '/'
    modified_name="${service_name%-service}"   # Remove the "-service"
    modified_name="${modified_name^^}_PORT"    # Uppercase and add '_PORT'
    generated_js+="const $modified_name=$port\n"
    ((port++))
done
echo -e "$generated_js" > frontend/generated.js
echo -e $GREEN"Setup successfull for each microservice !"
