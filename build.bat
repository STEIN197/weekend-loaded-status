@ECHO off
IF EXIST "weekend-loaded-status.jar" (
	DEL weekend-loaded-status.jar
)
ECHO Building a jar archive...
CD bin
jar -cfe ..\weekend-loaded-status.jar weekend.Application .\*
CD ..
ECHO Finished
