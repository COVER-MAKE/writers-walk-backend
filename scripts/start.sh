#!/bin/bash
PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE=$(ls $PROJECT_ROOT/*.jar)

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG

nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

echo "$TIME_NOW > 실행 완료" >> $DEPLOY_LOG