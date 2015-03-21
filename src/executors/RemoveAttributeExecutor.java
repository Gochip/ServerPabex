/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package executors;

import executors.response.CommandResponse;
import syntactic.commands.RemoveAttributeCommand;

/**
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class RemoveAttributeExecutor extends Executor{
private RemoveAttributeCommand cmd;

    public RemoveAttributeExecutor(RemoveAttributeCommand cmd) {
        this.cmd = cmd;
    }


    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();
        
        String key = cmd.getKey();
        if(super.client.getAttributes().removeAttribute(key)){
            resp.addOption("resp","Se borró correctamente");
        }else{
            resp.addError(EAW.ERR.NOT_EXISTS_KEY.getError(), EAW.ERR.NOT_EXISTS_KEY.getInfo(key));
        }
        return resp;
        
    }

}
