package emse.ismin.minesweeper;

import java.util.Random;

/**
 * This class represents the mine field
 */
public class Field {
    private static final int DIM_EASY = 10;
    private static final int DIM_MEDIUM = 20;
    private static final int DIM_HARD = 30;

    private Level level;
    private int dimX;
    private int dimY;
    private int nbMines;
    private boolean[][] tabMines;
    private Random alea = new Random();

    /**
     * Default constructor is setting the difficulty to <code>EASY</code>
     */
    public Field() {
        this(Level.EASY);
    }

    /**
     * Creates a Field object with the given difficulty
     * @param level: level of difficulty
     */
    public Field(Level level) {
        this.level = level;
        newGame(this.level);
    }

    /**
     * Creates a Field object with the given dimensions
     * @param dimX number of columns
     * @param dimY number of rows
     */
    public Field(int dimX, int dimY) {
        iniField(dimX, dimY, dimX+dimY);
    }

    /**
     * Creates a Field object with the given dimensions and the given number of mines
     * @param dimX number of columns
     * @param dimY number of rows
     * @param nbMines number of mines in the field
     */
    public Field(int dimX, int dimY, int nbMines) { iniField(dimX, dimY, nbMines); }

    /**
     * Initializes the field by setting the dimensions, the number of mines and placing the mines
     * @param dimX number of columns
     * @param dimY number of rows
     */
    private void iniField(int dimX, int dimY, int nbMines) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.nbMines = nbMines;
        this.tabMines  = new boolean[dimY][dimX];
        placeMines();
    }

    /**
     * Counts nearby mines of the given position (x, y)
     * @param x column position
     * @param y row position
     * @return the number of nearby mines or -1 if the cell is a mine
     */
    public int countNearbyMines(int x, int y) {
        int nbNearbyMines = 0;
        for(int i = -1; i<=1; i++) {
            for(int j = -1; j<=1; j++) {
                if(!outsideField(i+x, j+y)) {
                    if(tabMines[i+x][j+y])
                        nbNearbyMines+=1;
                }
            }
        }
        if(tabMines[x][y]) {
            nbNearbyMines = -1;
        }
        return nbNearbyMines;
    }

    /**
     * Indicates if a cell is outside field boundaries
     * @param x: column position
     * @param y: row position
     * @return a boolean
     */
    public boolean outsideField(int x, int y) {
        return (x<0 || y<0 || x>=tabMines.length || y>=tabMines[0].length);
    }

    /**
     * Places the mines in the field
     */
    void placeMines() {
        this.iniTabMines();
        for(int nbMinesRestantes = nbMines; nbMinesRestantes > 0;) {
            int x = alea.nextInt(tabMines.length);
            int y = alea.nextInt(tabMines[0].length);

            if(!tabMines[x][y]) {
                tabMines[x][y] = true;
                nbMinesRestantes--;
            }
        }
    }

    /**
     * Initializes the mine field to false for each cell
     */
    private void iniTabMines() {
        for(int i = 0; i<tabMines.length; i++) {
            for(int j=0; j<tabMines[0].length; j++) {
                this.tabMines[i][j] = false;
            }
        }
    }

    /**
     * Directly prints the mine field
     */
    public void affTextField() {
        for(int i = 0; i< tabMines.length; i++) {
            for(int j = 0; j<tabMines[0].length; j++) {
                if(tabMines[i][j]) {
                    System.out.print("X ");
                }
                else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Directly prints the mine field with nearby mines count
     * @see #affTextField()
     */
    public void affTextNearbyMines() {
        for(int i = 0; i< tabMines.length; i++) {
            for(int j = 0; j<tabMines[0].length; j++) {
                System.out.print(countNearbyMines(i, j)+" ");
            }
            System.out.println();
        }
    }

    /**
     * Overrides <code>toString</code> for the mine field, in order to display mines
     * @return string representation of mine field
     * @see #affTextField()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i< tabMines.length; i++) {
            for(int j = 0; j<tabMines[0].length; j++) {
                if(tabMines[i][j]) {
                    sb.append("X ");
                }
                else {
                    sb.append("O ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Initialize the field with a new configuration according to the given level
     * @param level Difficulty level
     */
    public void newGame(Level level) {
        this.level = level;
        switch(level) {
            case EASY:
                iniField(DIM_EASY, DIM_EASY, 2*DIM_EASY);
                break;
            case MEDIUM:
                iniField(DIM_MEDIUM, DIM_MEDIUM, 2*DIM_MEDIUM);
                break;
            case HARD:
                iniField(DIM_HARD, DIM_HARD, 2*DIM_HARD);
                break;
        }
    }

    /**
     * Getter for <code>dimX</code>
     * @return The number of columns
     */
    public int getDimX() {
        return dimX;
    }

    /**
     * Getter for <code>dimY</code>
     * @return The number of rows
     */
    public int getDimY() {
        return dimY;
    }

    /**
     * Getter for the level difficulty
     * @return <code>level</code>
     */
    public Level getLevel() {
        return level;
    }
}

