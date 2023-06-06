package Ayou.dev.qlearning.sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;

public class AgentContainer {
    public static void main(String[] args)  throws Exception {

        Runtime runtime= Runtime.instance();
        ProfileImpl profile= new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        jade.wrapper.AgentContainer agentContainer=runtime.createAgentContainer(profile);
        AgentController agentServer=agentContainer.createNewAgent("QLearningAgent",
                "Ayou.dev.qlearning.sma.QLearningAgent",new Object[]{});
        agentServer.start();
    }
}
