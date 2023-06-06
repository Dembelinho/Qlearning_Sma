package Ayou.dev.qlearning.sequential;

import java.util.Random;

public class Qlearning {
    private final double ALPHA=0.1;
    private final double GAMMA=0.9;
    private final double EPS=0.4;
    private final int MAX_EPOCH=200;
    private final int GRID_SIZE=3;
    private final int ACTION_SIZE=4;

    private int [][]grid;
    private double [][]qTable=new double[GRID_SIZE*GRID_SIZE][ACTION_SIZE];
    public int [][] actions;
    private int stateI;
    private int stateJ;
    public Qlearning() {
        actions= new int[][]{
                {0,-1},//à gauche
                {0,1},//à droite
                {1,0},//en bas
                {-1,0} //en haut
        };
        grid=new int[][]{
                {0,0,1},
                {0,-1,0},
                {0,0,0}
        };
    }
    private void resetState(){
        stateI=2;
        stateJ=0;
    }
    private int choseAction(){
        Random random=new Random();
        double bestQ=0;
        int actIndex=0;
        if(random.nextDouble()<EPS){
            //exploration
            actIndex=random.nextInt(ACTION_SIZE);

        }else {
            //exploitation
            int st=stateI*GRID_SIZE+stateJ;
            for (int i=0;i< ACTION_SIZE;i++){
                if (qTable[st][i]>bestQ){
                    bestQ=qTable[st][i];
                    actIndex=i;
                }
            }
        }
        return actIndex;
    }
    private void executeAction(int act){
        stateI=Math.max(0,Math.min(actions[act][0]+stateI,2));
        stateJ=Math.max(0,Math.min(actions[act][1]+stateI,2));
    }
    public void runQlearning(){
        int iter=0;
        resetState();
        while (iter<3){
            int act = choseAction();
            executeAction(act);
            System.out.println(stateI+" "+stateJ+" "+grid[stateI][stateJ]);
        }

    }
}
