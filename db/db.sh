#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
RESET='\033[0m' # No Color

source_env_file()
{
  source .env
  user="$SERVICE_ARCHITECTURE_USER"
  pwd="$SERVICE_ARCHITECTURE_PASSWORD"
}

create_env_file()
{
  echo -e "SERVICE_ARCHITECTURE_USER=$1\nSERVICE_ARCHITECTURE_PASSWORD=$2" > .env
}

check_env()
{
    if [ -f .env ]; then
    source_env_file
    else
    echo -e $YELLOW"Warning: No '.env' file found."$RESET
    echo "Enter the db username:"
    read db_user
    echo "Enter the db password:"
    read -s db_pwd
    if ! mariadb -u"$db_user" -p"$db_pwd" -e "exit" > /dev/null 2>&1
    then
        echo -e $RED"Error: Could not connect to MYSQL with the following ids."$RESET
        exit 1
    else
        create_env_file $db_user $db_pwd
        source_env_file
    fi
    fi
}

