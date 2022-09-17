#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true

#docker login --username=845979441@qq.com registry.cn-beijing.aliyuncs.com

docker build -t registry.cn-beijing.aliyuncs.com/pinyouma/interface .

docker push registry.cn-beijing.aliyuncs.com/pinyouma/interface

time=$(date "+%Y-%m-%d %H:%M:%S")

echo "Pushed time: ${time}"