package emse.ismin.minesweeper;

public enum ServerMessageTypes {
    CONNEXION(0),
    DISCONNECTION(1),
    MSG(2),
    START_GAME(3),
    CASE_CLICKED(4),
    INT(20),
    BOOL(21);

    private int code;

    ServerMessageTypes(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}