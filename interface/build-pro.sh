#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true

docker build -t 39.96.59.248:5000/pinyouma/interface .

docker push 39.96.59.248:5000/pinyouma/interface

time=$(date "+%Y-%m-%d %H:%M:%S")

echo "Pushed time: ${time}"