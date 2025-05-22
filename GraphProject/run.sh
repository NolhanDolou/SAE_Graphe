#!/bin/bash
echo "Compilation..."
mkdir -p bin
javac -cp "lib/*" -d bin src/graph/*.java src/json/*.java src/pdf/*.java src/Main.java

echo "Execution..."
java -cp "bin:lib/*" Main
