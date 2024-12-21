#!/bin/bash

source ./db/db.sh

check_env
echo "Removing database 'service-architecture'..."
if ! mariadb -u "$user" -p"$pwd" -e "DROP DATABASE \`service-architecture\`;"
then
  echo -e $RED"Error: Failed to remove database:\n\t- Does the database exists ?\n\t- Have you checked your credentials ?"$RESET
  exit 1
else
  echo -e $GREEN"Database removed successfully!"$RESET
fi
