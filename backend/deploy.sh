#!/bin/bash

HOME_DIRECTORY=/home/ec2-user
REPOSITORY=$HOME_DIRECTORY/server
cd $REPOSITORY

LOG_FILE=$HOME_DIRECTORY/deploy.log
APP_NAME=CartalogApplication
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]
then
  echo "> 서버가 실행중이지 않습니다" >> $LOG_FILE
else
  echo "> sudo kill -15 $CURRENT_PID" >> $LOG_FILE
  kill -15 "$CURRENT_PID" >> $LOG_FILE 2>&1
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH >> $LOG_FILE 2>&1 &

echo "[$(date)] server deploy" >> $LOG_FILE
