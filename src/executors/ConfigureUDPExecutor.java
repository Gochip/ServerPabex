package executors;

import executors.response.CommandResponse;
import syntactic.commands.ConfigureUDPCommand;

/**
 * CONFIGURE_UDP 'port'
 *
 * Este comando se usa para establecer el puerto de escucha UDP.
 *
 * @author Germán Parisi
 * @version 1.0
 */
public class ConfigureUDPExecutor extends Executor {

    private ConfigureUDPCommand cmd;

    public ConfigureUDPExecutor(ConfigureUDPCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();
        int port = cmd.getPort();
        if(port <= 0 || port >= 65536){
            resp.addError(EAW.ERR.INCORRECT_PORT.getError(), EAW.ERR.INCORRECT_PORT.getInfo(port));
        }else{
            client.setSendPortUDP(port);
            resp.addOption("msg_info", "Puerto UDP configurado correctamente");
        }
        return resp;
    }
}
