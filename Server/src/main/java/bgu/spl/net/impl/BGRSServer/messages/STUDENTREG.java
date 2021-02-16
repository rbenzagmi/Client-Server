package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

public class STUDENTREG extends Command {

    public STUDENTREG() {
        super(2);
    }
    String userName;
    String password;

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+": "+this.userName+", "+password;
    }
    @Override
    public Command act(prot protocol) {
        User user = protocol.getLoggedInUser();

        if (user!=null)
            return new ERROR(this);
    
        if (db.try_Register(this.userName, this.password, false) )
            return new ACK(this);

        return new ERROR(this);
    }
}
