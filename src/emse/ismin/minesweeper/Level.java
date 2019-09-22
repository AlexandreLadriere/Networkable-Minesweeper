package emse.ismin.minesweeper;

/**
 * Defines the levels of difficulty
 */
public enum Level {
    EASY(10, 10, 15),
    MEDIUM(20, 20, 40),
    HARD(30, 30, 150);

    public int dimX;
    public int dimY;
    public int nbMines;

    Level(int dimX, int dimY, int nbMines) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.nbMines = nbMines;
    }
}
