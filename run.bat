@echo off
setlocal EnableDelayedExpansion
cd /d "%~dp0"

where javac >nul 2>&1
if errorlevel 1 (
    echo JDK compiler not found. Install a JDK and make sure javac is on PATH.
    pause
    exit /b 1
)

if not exist out mkdir out
set "sources="
for %%F in (src\*.java) do set "sources=!sources! %%F"

javac -d out !sources!
if errorlevel 1 (
    echo Compilation failed. Check the messages above.
    pause
    exit /b 1
)

java -cp out Main
pause
