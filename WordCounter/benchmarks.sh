#!/bin/bash

mvn package -f ../pom.xml -pl WordCounter -am  -DskipTests=true
find src/main/resources/ -type f -name '*.txt' | grep -v '[Ee]mpty' | xargs -L1 -I '{}' sh -c 'printf "\n\n=======\n\nTesting {}\n------\n"; java -jar target/WordCounter-1.0-SNAPSHOT-jar-with-dependencies.jar --file {}'