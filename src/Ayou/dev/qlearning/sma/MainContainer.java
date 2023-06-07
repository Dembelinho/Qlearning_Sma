package Ayou.dev.qlearning.sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class MainContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.GUI, "true");
        AgentContainer mainContainer = runtime.createMainContainer(profile);
        mainContainer.start();

        try {
            AgentController masterAgent = mainContainer.createNewAgent("MasterAgent",
                    "Ayou.dev.qlearning.sma.MasterAgent", new Object[]{});
            masterAgent.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

