#!/bin/sh

pid_file_mvn="mvn.start.pid"

if [ -e $pid_file_mvn ]; then
    echo "$pid_file_mvn file found, there's probably already a turd running."
    exit
fi

nohup mvn clean compile exec:java &> ~/mvn.log
echo "$!" > $pid_file_mvn
