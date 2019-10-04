package ismin.minesweeper.enums;

public enum FileNames {
    SCORE_FILENAME("scores.dat"),
    STATS_FILENAME("stats.dat");

    private String fileName;

    FileNames(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return fileName;
    }
}