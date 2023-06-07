package Ayou.dev.qlearning.sma;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;

public class MasterAgent extends Agent {
    private final int solution= 2;
    private int numReceivedResults = 0;

    protected void setup() {
        // Create 4 instances of QlearningAgent
        try {
            for (int i = 1; i <= 4; i++) {
                String agentName = "QlearningAgent-" + i;
                AgentController agentController = getContainerController().createNewAgent(agentName,
                        "Ayou.dev.qlearning.sma.QlearningAgent", new Object[]{});
                agentController.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add behaviour to receive results from QlearningAgents
        addBehaviour(new ReceiveResultBehaviour());
    }

    private class ReceiveResultBehaviour extends CyclicBehaviour {
        public void action() {
            System.out.println("Solution State: " + solution);
            MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage message = receive(template);

            if (message != null) {
                numReceivedResults++;
                int result = Integer.parseInt(message.getContent());
                if (result == solution) {
                    System.out.println("-- message number : "+numReceivedResults+" -- result: "+result +
                            " -- from agent "+message.getSender().getName());
                    // Terminate the agent
                    doDelete();
                }

            } else {
                block();
            }
        }
    }
}
