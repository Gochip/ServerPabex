package executors;

import executors.response.CommandResponse;
import syntactic.commands.GetAttributeCommand;

/**
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class GetAttributeExecutor extends Executor {

    private GetAttributeCommand cmd;

    public GetAttributeExecutor(GetAttributeCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();
        String value = super.client.getAttributes().getAttribute(cmd.getKey());
        if(value == null){
            resp.addError(EAW.ERR.NOT_EXISTS_KEY.getError(),EAW.ERR.NOT_EXISTS_KEY.getInfo(cmd.getKey()));
        }else{
            resp.addOption("value", value);
        }
        return resp;
    }
}
