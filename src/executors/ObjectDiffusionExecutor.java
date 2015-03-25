package executors;

import com.difusion.ObjectDiffusion;
import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;
import executors.response.CommandResponse;

/**
 *
 * @author Parisi Germán y Barrionuevo Diego
 * @version 1.0
 */
public class ObjectDiffusionExecutor extends Executor{
    private ObjectDiffusion objectDifusion;
    public ObjectDiffusionExecutor(ObjectDiffusion objectDifusion){
        this.objectDifusion = objectDifusion;
    }

    @Override
    public Response execute() {
        int count = 0;
        CommandResponse cr = new CommandResponse();
        Group g = ServerController.getInstance().getGroup(objectDifusion.getIdGroup());
        if (g == null) {
            //No existe el grupo
            cr.addError(EAW.ERR.NOT_EXISTS_GROUP.getError(), EAW.ERR.NOT_EXISTS_GROUP.getInfo(objectDifusion.getIdGroup()));
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
