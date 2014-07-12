package executors;

import com.difusion.ObjectDifusion;
import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;
import executors.response.CommandResponse;

/**
 *
 * @author Parisi Germán &  Barrionuevo Diego
 * @version 1.0
 */
public class ObjectDifusionExecutor extends Executor{
    private ObjectDifusion objectDifusion;
    public ObjectDifusionExecutor(ObjectDifusion objectDifusion){
        this.objectDifusion = objectDifusion;
    }

    @Override
    public Response execute() {
        int count = 0;
        CommandResponse cr = new CommandResponse();
        Group g = ServerController.getInstance().getGroup(objectDifusion.getGroupId());
        if (g == null) {
            //No existe el grupo
            cr.addError(EAW.ERR.NOT_EXISTS_GROUP.getError(), EAW.ERR.NOT_EXISTS_GROUP.getInfo(objectDifusion.getGroupId()));
//            cr.addOption("group", "0");
        } else {
            //Existe el grupo
            for (ClientRunnable c : g.getClients()) {
                if (!c.equals(client)) {
                    c.send(objectDifusion);
                    count++;
                }
            }
            cr.addOption("group_size", String.valueOf(g.getClients().size()));
            ServerController.getInstance().incrementCountSentObject();
        }
        cr.addOption("count", String.valueOf(count));
        return cr;
    }
}
