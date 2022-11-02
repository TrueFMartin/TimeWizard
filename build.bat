:: Franklin "True" Martin
::11/1/22
::Side Scroller

::@echo off
cd src
javac Game.java View.java Controller.java Model.java Pipe.java Json.java Wizard.java Skeleton.java Fireball.java Sprite.java -d ../bin
cd ..
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	cd bin
	java Game
)



