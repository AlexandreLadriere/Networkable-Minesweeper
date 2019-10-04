SRC_PATH='./src/'
DEST_PATH='../out/'

cd $SRC_PATH

javac -d $DEST_PATH ismin/minesweeper/enums/Level.java
javac -d $DEST_PATH ismin/minesweeper/enums/FileNames.java
javac -d $DEST_PATH ismin/minesweeper/enums/ServerMessageTypes.java

javac -d $DEST_PATH ismin/minesweeper/utils/Field.java
javac -d $DEST_PATH ismin/minesweeper/utils/CustomJOptionPane.java
javac -d $DEST_PATH ismin/minesweeper/utils/Counter.java
javac -d $DEST_PATH ismin/minesweeper/utils/Case.java

javac -d $DEST_PATH ismin/minesweeper/client/Minesweeper.java
javac -d $DEST_PATH ismin/minesweeper/client/Gui.java -Xlint:deprecation
javac -d $DEST_PATH ismin/minesweeper/client/Controller.java
javac -d $DEST_PATH ismin/minesweeper/client/FlagCounter.java

javac -d $DEST_PATH ismin/minesweeper/server/Server.java
javac -d $DEST_PATH ismin/minesweeper/server/ServerGui.java
javac -d $DEST_PATH ismin/minesweeper/server/EchoThread.java

cp -avr img $DEST_PATH

