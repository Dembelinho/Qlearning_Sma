package Ayou.dev.qlearning.sequential;

import java.util.Arrays;
import java.util.Random;

public class Qlearning {
    private final double ALPHA=0.1;
    private final double GAMMA=0.9;
    private final int MAX_EPOCH=2000;
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
                {1,0,0},
                {-1,0,0},
                {0,0,0}
        };
    }
    private void resetState(){
        stateI=2;
        stateJ=0;
    }
    private int choseAction(double EPS){
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
    private int executeAction(int act){
        stateI=Math.max(0,Math.min(actions[act][0]+stateI,GRID_SIZE-1));
        stateJ=Math.max(0,Math.min(actions[act][1]+stateI,GRID_SIZE-1));
        return stateI*GRID_SIZE+stateJ;
    }
    private boolean isFinished(){
        return grid[stateI][stateJ]==1;
    }
    private void showResults(){
        System.out.println("**** q table ****");
        for (double []line: qTable) {
            System.out.printf("[");
            for (double qvalue:line) {
                System.out.printf(qvalue+",");
            }
            System.out.println("]");
        }
        System.out.println("");
        resetState();
        while (!isFinished()){
            int act=choseAction(0);
            System.out.println("State : "+(stateI*GRID_SIZE+stateJ)+" action : "+act);
            executeAction(act);
        }
        System.out.println("Final  State : "+(stateI*GRID_SIZE+stateJ));
    }
    public void runQlearning(){
        int iter=0;
        int currentState;
        int nextState;
        int act,act1;

        while (iter<MAX_EPOCH){
            resetState();
            while (!isFinished()){
                currentState=stateI*GRID_SIZE+stateJ;
                act = choseAction(0.3);
                nextState = executeAction(act);
                act1 = choseAction(0);
                //System.out.println(stateI+" "+stateJ+" "+grid[stateI][stateJ]);
                qTable[currentState][act]=qTable[currentState][act]+ALPHA*(grid[stateI][stateJ]+GAMMA*qTable[nextState][act1]-qTable[currentState][act]);
            }
            iter++;
        }
        showResults();
    }
}
