:: Franklin "True" Martin
::11/1/22
::Side Scroller
::Game.java View.java Controller.java Model.java Pipe.java Json.java Wizard.java Skeleton.java Fireball.java Sprite.java
::@echo off
::Unable to load games that were saved during run b/c a recompile is needed to access files in jar
::Use non-jar, regular compile/run batch file to save and then load in same instance
cd src
javac main/*.java -d ./bin -Xlint:unchecked
cd bin
jar cfm0 TimeWizard.jar Manifest.txt main/*.class resources
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java -jar TimeWizard.jar
)



