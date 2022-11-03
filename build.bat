:: Franklin "True" Martin
::11/1/22
::Side Scroller
::Game.java View.java Controller.java Model.java Pipe.java Json.java Wizard.java Skeleton.java Fireball.java Sprite.java
::@echo off
cd src
javac main/*.java -d ./bin
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java -cp ./bin main.Game
)



