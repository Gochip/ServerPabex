package executors;

import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;
import executors.response.CommandResponse;
import executors.response.InformationResponse;
import java.util.List;
import syntactic.commands.LeaveGroupCommand;

/**
 * LEAVE_GROUP 'id_group'
 * 
 * @author Parisi Germán
 * @version 1.0
 */
public class LeaveGroupExecutor extends Executor {

    private LeaveGroupCommand cmd;

    public LeaveGroupExecutor(LeaveGroupCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();

        String idGroup = cmd.getIdGroup();
        Group group = ServerController.getInstance().getGroup(idGroup);

        if (group == null) {
            resp.addError(EAW.ERR.NOT_EXISTS_GROUP.getError(), EAW.ERR.NOT_EXISTS_GROUP.getInfo(idGroup));
        } else {
            if (!group.existsClient(client)) {
                resp.addError(EAW.ERR.NOT_EXISTS_CLIENT_IN_GROUP.getError(), EAW.ERR.NOT_EXISTS_CLIENT_IN_GROUP.getInfo(super.client.getId(), idGroup));
            } else {
                group.removeClient(super.client);
                super.client.removeGroup(group);
                InformationResponse infoResp = new InformationResponse();
                infoResp.addId(super.client.getId());
                infoResp.addSource("leave_group");
                infoResp.addValue("id_group", group.getId());
                resp.addOption("msg_info", "Abandonaste el grupo");
                List<ClientRunnable> clients = group.getClients();
                if (super.client.equals(group.getClientAdmin())) {
                    if (group.isForceToDelete() || clients.isEmpty()) {
                        infoResp.addValue("deleted_group", "true");
                        for (ClientRunnable cr : clients) {
                            cr.send(infoResp);
                            cr.removeGroup(group);
                        }
                        ServerController.getInstance().removeGroup(idGroup);
                    } else {
                        infoResp.addValue("deleted_group", "false");
                        for (ClientRunnable cr : clients) {
                            cr.send(infoResp);
                        }
                        group.setNextClientAdmin();
                    }
                } else {
                    infoResp.addValue("deleted_group", "false");
                    for (ClientRunnable cr : clients) {
                        cr.send(infoResp);
                    }
                    group.setNextClientAdmin();
                }
            }
        }
        return resp;
    }
}
