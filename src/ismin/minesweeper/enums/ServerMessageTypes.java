package ismin.minesweeper.enums;

/**
 * Defines the different message types for communication between client and server
 */
public enum ServerMessageTypes {
    CONNEXION(0),
    SERVER_DISCONNECTION(1),
    MSG(2),
    START_GAME(3),
    CASE_CLICKED(4),
    MINE_CLICKED(5),
    END_GAME(6),
    CHANGE_NAME(7),
    ALREADY_STARTED(8),
    CLIENT_DISCONNECTION(9),
    CHAT_MSG(10),
    DIRECT_SCORE(11),
    INT(20),
    BOOL(21);

    private int code;

    ServerMessageTypes(int code) {
        this.code = code;
    }

    /**
     * Returns the code value
     * @return code value (int type)
     */
    public int value() {
        return code;
    }
}
