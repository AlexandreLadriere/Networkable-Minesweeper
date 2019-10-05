CLIENT_ENTRY_POINT='ismin.minesweeper.client.Minesweeper'
SERVER_ENTRY_POINT='ismin.minesweeper.server.Server'
UTILS_CLASS='ismin/minesweeper/utils/*.class'
ENUMS_CLASS='ismin/minesweeper/enums/*.class'
CLIENT_CLASS='ismin/minesweeper/client/*.class'
SERVER_CLASS='ismin/minesweeper/server/*.class'


cd out/

# Building the client (i.e. solo) .jar file:
jar cfe Client.jar $CLIENT_ENTRY_POINT $UTILS_CLASS $ENUMS_CLASS $CLIENT_CLASS img

# Building the server .jar file:
jar cfe Server.jar $SERVER_ENTRY_POINT $UTILS_CLASS $ENUMS_CLASS $SERVER_CLASS

cp -avr Client.jar Server.jar ./../
