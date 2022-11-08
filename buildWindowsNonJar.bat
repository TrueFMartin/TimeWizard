::This build script is able to save and load that same save without exiting, unlike jar build script
cd src
javac main/*.java -d ./bin -Xlint:unchecked
cd bin
java main.Game