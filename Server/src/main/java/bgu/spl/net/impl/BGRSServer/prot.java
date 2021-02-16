package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;


public class prot implements MessagingProtocol<Command> {

    private boolean shouldTerminate = false;
    private User loggedInUser = null;
    
    @Override
    public Command process(Command msg) {
        return msg.act(this);
    }
    
    public User getLoggedInUser() {
        return loggedInUser;
    }
    
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    public void terminate(){
        shouldTerminate = true;
    }
    
    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
