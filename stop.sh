if [ -e mvn.start.pid ]; then
    pid=`cat mvn.start.pid`
    kill -9 $pid
    rm mvn.start.pid
fi
