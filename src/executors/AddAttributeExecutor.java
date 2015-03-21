package executors;

import com.server.ClientRunnable;
import com.server.Group;
import executors.response.CommandResponse;
import executors.response.InformationResponse;
import syntactic.commands.AddAttributeCommand;

/**
 * ADD_ATTRIBUTE 'key1, key2, key3' 'value1, value2, value3' 'YES|NO'
 *
 * YES: Avisar a los demás. NO: No avisar a los demás.
 *
 *
 * @author Germán Parisi y Bertola Federico
 * @version 1.1
 */
public class AddAttributeExecutor extends Executor {

    private AddAttributeCommand cmd;

    public AddAttributeExecutor(AddAttributeCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();
        String keys = cmd.getKey();
        String arrayKeys[] = keys.split(",");
        String values = cmd.getValue();
        String arrayValues[] = values.split(",");

        if (arrayKeys.length == arrayValues.length) {
            for (int i = 0; i < arrayValues.length; i++) {
                super.client.getAttributes().addAttribute(arrayKeys[i].trim(), arrayValues[i].trim());
            }
            resp.addOption("resp", "Se agregó correctamente.");
            if (cmd.isVerbose()) {
                for (Group g : super.client.getGroups()) {
                    InformationResponse infResp = new InformationResponse();
                    infResp.addId(super.client.getId());
                    infResp.addSource("add_attribute");
                    infResp.addValue("id_group", g.getId());
                    infResp.addValue("key", keys);
                    infResp.addValue("value", values);
                    for (ClientRunnable cr : g.getClients()) {
                        if (!cr.equals(super.client)) {
                            cr.send(infResp);
                        }
                    }
                }
            }
        } else {
            resp.addError(EAW.ERR.INCORRECT_LENGTH_KEY_VALUE.getError(), EAW.ERR.INCORRECT_LENGTH_KEY_VALUE.getInfo());
        }
        return resp;
    }
}
