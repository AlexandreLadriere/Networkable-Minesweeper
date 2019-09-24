SRC_PATH='./src/'
DEST_PATH='../out/production/Minesweeper/'

cd $SRC_PATH

javac -d $DEST_PATH emse/ismin/minesweeper/Level.java
javac -d $DEST_PATH emse/ismin/minesweeper/Field.java
javac -d $DEST_PATH emse/ismin/minesweeper/Minesweeper.java
javac -d $DEST_PATH emse/ismin/minesweeper/Gui.java -Xlint:deprecation
javac -d $DEST_PATH emse/ismin/minesweeper/Controller.java
javac -d $DEST_PATH emse/ismin/minesweeper/Counter.java
javac -d $DEST_PATH emse/ismin/minesweeper/FlagCounter.java
javac -d $DEST_PATH emse/ismin/minesweeper/Case.java

cp -avr img $DEST_PATH

