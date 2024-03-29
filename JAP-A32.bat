:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: SCRIPT W01 - CST8221 - Fall 2022
:: ---------------------------------------------------------------------
:: Begin of Script (A32 - F22)
:: ---------------------------------------------------------------------


CLS
:: LOCAL VARIABLES .....................................................
SET SRCDIR=src
SET BINDIR=bin
SET BINOUT=game-javac.out
SET BINERR=game-javac.err
SET JARNAME=GameGUI.jar
SET JAROUT=game-jar.out
SET JARERR=game-jar.err
SET DOCPACK=game
SET DOCDIR=doc
SET DOCOUT=game-javadoc.out
SET DOCERR=game-javadoc.err
SET MAINCLASSSRC=src/game/GameApp.java
SET MAINCLASSBIN=game.GameApp
@echo off

ECHO " _________________________________ "
ECHO "|     __    _  ___    ___  _      |"
ECHO "|    |  |  / \ \  \  /  / / \     |"
ECHO "|    |  | /   \ \  \/  / /   \    |"
ECHO "|    |  |/  _  \ \    / /  _  \   |"
ECHO "|  __|  |  / \  \ \  / /  / \  \  |"
ECHO "|  \____/_/   \__\ \/ /__/   \__\ |"
ECHO "|                                 |"
ECHO "| .. ALGONQUIN COLLEGE - 2022F .. |"
ECHO "|_________________________________|"
ECHO "                                   "
ECHO "[ASSIGNMENT SCRIPT ---------------------]"

ECHO "1. Compiling ............................."
javac -Xlint -cp ".;%SRCDIR%;" %MAINCLASSSRC% -d %BINDIR% > %BINOUT% 2> %BINERR%

ECHO "2. Creating Jar .........................."
cd bin
jar cvfe %JARNAME% %MAINCLASSBIN% . > %JAROUT% 2> %JARERR%

ECHO "3. Creating Javadoc ......................"
cd ..
javadoc -cp ".;%BINDIR%;" -d %DOCDIR% -sourcepath %SRCDIR% -subpackages %DOCPACK%> %DOCOUT% 2> %DOCERR%

cd bin

ECHO "4. Running Jar ..........................."
start java -jar %JARNAME%

ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
cd ..


@echo on

:: ---------------------------------------------------------------------
:: End of Script (Assignments - F22)
:: ---------------------------------------------------------------------