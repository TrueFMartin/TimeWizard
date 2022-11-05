#!/usr/bin/bash
#You may have to change your bash path from what is listed above
cd src
javac main/*.java -d ./bin
java -cp ./bin main.Game  
