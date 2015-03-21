package executors;

import executors.response.DisconnectResponse;
import java.util.ArrayList;
import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;
import executors.response.InformationResponse;
import java.util.List;

/**
 * Desconecta a un cliente del servidor.
 * 
 * Si el cliente era integrante del algún grupo entonces abandona el grupo.
 * 
 * @author Parisi Germán y  Bertola Federico
 * @version 1.1
 */
public class DisconnectExecutor extends Executor {

    public DisconnectExecutor() {

    }

    @Override
    public Response execute() {
        super.client.disconnect();
        DisconnectResponse resp = new DisconnectResponse();
        resp.addValue("msg_info", "Te desconectaste.");
        ArrayList<Group> groups = super.client.getGroups();
        ServerController sc = ServerController.getInstance();
        for (Group g : groups) {
            if (g.getClientAdmin().equals(super.client)) {
                g.removeClient(super.client);
                InformationResponse infoResp = new InformationResponse();
                infoResp.addId(super.client.getId());
                infoResp.addSource("disconnect");
                infoResp.addValue("id_group", g.getId());
                List<ClientRunnable> clients = g.getClients();
                if (g.isForceToDelete() || clients.isEmpty()) {
                    for (ClientRunnable cr : clients) {
                        cr.send(infoResp);
                        cr.removeGroup(g);
                    }
                    sc.removeGroup(g.getId());
                } else {
                    for (ClientRunnable cr : clients) {
                        cr.send(infoResp);
                    }
                    g.setNextClientAdmin();
                }
            } else {
                g.removeClient(super.client);
                InformationResponse infoResp = new InformationResponse();
                infoResp.addId(super.client.getId());
                infoResp.addSource("disconnect");
                infoResp.addValue("id_group", g.getId());
                ArrayList<ClientRunnable> clients = g.getClients();
                for (ClientRunnable cr : clients) {
                    cr.send(infoResp);
                }
            }
        }
        ServerController.getInstance().removeClient(client.getId());

        return resp;
    }
}
