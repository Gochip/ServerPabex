package executors;

import executors.response.CommandResponse;
import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;
import java.util.ArrayList;
import syntactic.commands.ShowGroupCommand;

/**
 * Muestra los datos de un grupo.
 *
 *
 * SHOW_GROUP id_group : (id_clients, xxx_name, xxx_ip, id_admin, max_num,
 * is_private, error, error_info)
 * <br />
 * TABLA DE ERRORES <br />
 * NOT_EXISTS_GROUP(1)
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.1
 */
public class ShowGroupExecutor extends Executor {

    private ShowGroupCommand cmd;

    public ShowGroupExecutor(ShowGroupCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        CommandResponse cr = new CommandResponse();
        Group grupo = ServerController.getInstance().getGroup(cmd.getIdGroup());
        if (grupo == null) {
            cr.addError(EAW.ERR.NOT_EXISTS_GROUP.getError(), EAW.ERR.NOT_EXISTS_GROUP.getInfo(cmd.getIdGroup()));
            return cr;
        }

        StringBuilder sb = new StringBuilder();
        ArrayList<ClientRunnable> clients = grupo.getClients();
        ClientRunnable c = null;
        for (int i = 0; i < clients.size() - 1; i++) {
            c = clients.get(i);
            sb.append(c.getId()).append(',');
            cr.addOption(c.getId() + "_name", c.getAttributes().getAttribute("name"));
            cr.addOption(c.getId() + "_ip", c.getIP());
        }
        c = clients.get(clients.size() - 1);
        sb.append(c.getId());
        cr.addOption(c.getId() + "_name", c.getAttributes().getAttribute("name"));
        cr.addOption(c.getId() + "_ip", c.getIP());
        cr.addOption("id_admin", grupo.getClientAdmin().getId());
        cr.addOption("max_num", String.valueOf(grupo.getMaxNum()));
        cr.addOption("is_private", String.valueOf(grupo.isPrivate()));
        cr.addOption("id_clients", sb.toString());
        return cr;
    }
}
