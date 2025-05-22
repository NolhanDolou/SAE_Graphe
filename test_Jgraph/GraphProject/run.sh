#!/bin/bash
echo "Compilation avec JGraphT..."
mkdir -p bin
javac -cp "lib/*" -d bin src/graph/*.java src/json/*.java src/pdf/*.java src/jgrapht/*.java src/Main.java

if [ $? -eq 0 ]; then
    echo "Compilation réussie!"
    echo "Execution..."
    java -cp "bin:lib/*" Main
else
    echo "Erreur de compilation!"
fi
