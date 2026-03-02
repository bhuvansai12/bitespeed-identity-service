#!/usr/bin/env bash
apt-get update
apt-get install -y openjdk-17-jdk
chmod +x mvnw
./mvnw clean package