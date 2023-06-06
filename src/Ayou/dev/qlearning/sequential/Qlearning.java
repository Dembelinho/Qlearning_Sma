package Ayou.dev.qlearning.sequential;

public class Qlearning {
    private final double ALPHA=0.1;
    private final double GAMMA=0.9;
    private final double EPS=0.4;
    private final int MAX_EPOCH=200;
    private final int GRID_SIZE=3;
    private final int ACTION_SIZE=4;

    private int [][]grid=new int[GRID_SIZE][GRID_SIZE];
    private double [][]qTable=new double[GRID_SIZE*GRID_SIZE][ACTION_SIZE];
}
