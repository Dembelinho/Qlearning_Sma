package Ayou.dev.qlearning.sma;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Random;

public class QLearningAgent extends Agent {
    private int currentState;
    private int numStates;
    private int numActions;
    private double[][] qTable;
    private double alpha;
    private double gamma;
    private double epsilon;
    private Random rand;

    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length == 4) {
            numStates = (int) args[0];
            numActions = (int) args[1];
            alpha = (double) args[2];
            gamma = (double) args[3];
            qTable = new double[numStates][numActions];
            rand = new Random();
            addBehaviour(new QLearningBehaviour());
        } else {
            System.out.println("Invalid arguments");
            doDelete();
        }
    }

    private class QLearningBehaviour extends Behaviour {
        private int action;
        private int nextState;
        private double reward;

        public void action() {
            if (rand.nextDouble() < epsilon) {
                action = rand.nextInt(numActions);
            } else {
                action = getBestAction(currentState);
            }
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent(Integer.toString(action));
            msg.addReceiver(getRandomAgent());
            send(msg);
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage reply = receive(mt);
            if (reply != null) {
                nextState = Integer.parseInt(reply.getContent());
                reward = getReward(currentState, action, nextState);
                qTable[currentState][action] += alpha * (reward + gamma * getMaxQValue(nextState) - qTable[currentState][action]);
                currentState = nextState;
            } else {
                block();
            }
        }

        public boolean done() {
            return false;
        }
    }

    private int getBestAction(int state) {
        int bestAction = 0;
        double bestValue = qTable[state][0];
        for (int i = 1; i < numActions; i++) {
            if (qTable[state][i] > bestValue) {
                bestAction = i;
                bestValue = qTable[state][i];
            }
        }
        return bestAction;
    }

    private double getMaxQValue(int state) {
        double maxQValue = qTable[state][0];
        for (int i = 1; i < numActions; i++) {
            if (qTable[state][i] > maxQValue) {
                maxQValue = qTable[state][i];
            }
        }
        return maxQValue;
    }

    private double getReward(int state, int action, int nextState) {
        // Define your reward function here
        return 0;
    }

    private AID getRandomAgent() {
        // Define your agent selection strategy here
        return null;
    }

}
