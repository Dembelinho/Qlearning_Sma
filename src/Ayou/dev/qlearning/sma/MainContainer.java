package Ayou.dev.qlearning.sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class MainContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime= Runtime.instance(); //qui va executer le framework jade
        //Profile Jade Configuration
        ProfileImpl profile= new ProfileImpl(); //pour specifier les info de l'application
        profile.setParameter(ProfileImpl.GUI,"true");
        AgentContainer mainContainer=runtime.createMainContainer(profile);
        mainContainer.start();

    }
}
