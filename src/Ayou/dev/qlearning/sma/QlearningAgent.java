package Ayou.dev.qlearning.sma;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;

import java.util.Random;

public class QlearningAgent extends Agent {
    static final int MAX_EPOCH = 1000; //iterations number
    static final double ALPHA = 0.1; // Learning rate
    static final double GAMMA = 0.9; // Discount factor
    static final int GRID_SIZE=3;
    static final int ACTION_SIZE=4;
    private int[][] grid;
    private double[][] qTable = new double[GRID_SIZE * GRID_SIZE][ACTION_SIZE];
    private int[][] actions;
    private int stateI;
    private int stateJ;

    //private QLearningApp qLearningInterface;

    protected void setup() {
        actions = new int[][]{
                {0, -1}, // left
                {0, 1},  // right
                {1, 0},  // down
                {-1, 0}  // up
        };

        grid = new int[][]{
                {0, 0, 1},
                {-1, 0,-1},
                {0, 0, 0}
        };

        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour();
        sequentialBehaviour.addSubBehaviour(new ResetStateBehaviour());
        sequentialBehaviour.addSubBehaviour(new QLearningBehaviour(this,1));
        sequentialBehaviour.addSubBehaviour(new ShowResultsBehaviour());

        addBehaviour(sequentialBehaviour);

        // Launch the JavaFX application
       // QLearningApp.launch(QLearningApp.class);
    }

    private class ResetStateBehaviour extends OneShotBehaviour {
        public void action() {
            stateI = 2;
            stateJ = 0;
        }
    }

    private class QLearningBehaviour extends TickerBehaviour {
        private int iter;
        private int currentState;
        private int nextState;
        private int act;
        private int act1;

        public QLearningBehaviour(Agent agent, long period) {
            super(agent, period);
            iter = 0;
        }

        public void onTick() {
            if (iter >= MAX_EPOCH) {
                stop();
                return;
            }
            currentState = stateI * GRID_SIZE + stateJ;

            act = choseAction(0.3);
            nextState = executeAction(act);
            act1 = choseAction(0);

            qTable[currentState][act] = qTable[currentState][act] + ALPHA *
                    (grid[stateI][stateJ] + GAMMA * qTable[nextState][act1] - qTable[currentState][act]);

            iter++;
            // Update l'interface graphique avec la nouvelle position de l'agent
            // Platform.runLater(() -> QLearningApp.updateAgentPosition(stateI, stateJ));
            //QLearningApp.updateAgentPosition(stateI, stateJ);
        }
    }

    private class ShowResultsBehaviour extends OneShotBehaviour {
        public void action() {
//            System.out.println("**** q table ****");
//            for (double[] line : qTable) {
//                System.out.print("[");
//                for (double qvalue : line) {
//                    System.out.print(qvalue + ",");
//                }
//                System.out.println("]");
//            }
//
//            System.out.println("");
//            new ResetStateBehaviour();

            while (!isFinished()) {
                int act = choseAction(0);

                //System.out.println("State: " + (stateI * GRID_SIZE + stateJ) + " action: " + act);
                executeAction(act);
            }
            //System.out.println("Final State: " + (stateI * GRID_SIZE + stateJ));
            // Send the result to the MasterAgent
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(new AID("MasterAgent", AID.ISLOCALNAME));
            message.setContent(String.valueOf(stateI * GRID_SIZE + stateJ));
            send(message);
        }
    }

    private int choseAction(double EPS) {
        Random random = new Random();
        double bestQ = 0;
        int actIndex = 0;

        if (random.nextDouble() < EPS) {
            // exploration
            actIndex = random.nextInt(ACTION_SIZE);
        } else {
            // exploitation
            int st = stateI * GRID_SIZE + stateJ;
            for (int i = 0; i < ACTION_SIZE; i++) {
                if (qTable[st][i] > bestQ) {
                    bestQ = qTable[st][i];
                    actIndex = i;
                }
            }
        }
        return actIndex;
    }

    private int executeAction(int act) {
        stateI = Math.max(0, Math.min(actions[act][0] + stateI, GRID_SIZE - 1));
        stateJ = Math.max(0, Math.min(actions[act][1] + stateJ, GRID_SIZE - 1));

        // Mettez à jour l'interface graphique avec la nouvelle position de l'agent
        //Platform.runLater(() -> QLearningApp.updateAgentPosition(stateI, stateJ));

        return stateI * GRID_SIZE + stateJ;
    }
    private boolean isFinished() {
        return grid[stateI][stateJ] == 1;
    }
}

