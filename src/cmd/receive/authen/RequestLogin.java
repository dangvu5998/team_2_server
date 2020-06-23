package cmd.receive.authen;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import java.nio.ByteBuffer;

public class RequestLogin extends BaseCmd {
    private String username = "";
    public RequestLogin(DataCmd dataCmd) {
        super(dataCmd);
    }

    public String getUsername() {
        return username;
    }
    
    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            username = readString(bf);
        } catch (Exception e) {
            
        }
    }
    
}
