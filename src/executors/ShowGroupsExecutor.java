package executors;

import executors.response.CommandResponse;
import java.util.Collection;
import com.server.Group;
import com.server.ServerController;

/**
 * Muestra los grupos actuales en el servidor.
 *
 *
 * SHOW_GROUPS : (id_groups, xxx_group_name, xxx_id_admin, xxx_max_num,
 * xxx_is_private, xxx_nocc)
 *
 * Referencias: xxx_nocc: Number_of_connected_clients
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.2
 */
public class ShowGroupsExecutor extends Executor {

    @Override
    public Response execute() {
        CommandResponse response = new CommandResponse();
        Collection<Group> col = ServerController.getInstance().getGroupsList();

        StringBuilder sbIds = new StringBuilder();
        for (Group g : col) {
            sbIds.append(g.getId()).append(',');
            response.addValue(g.getId() + "_group_name", g.getGroupName());
            response.addValue(g.getId() + "_id_admin", g.getClientAdmin().getId());
            response.addValue(g.getId() + "_max_num", String.valueOf(g.getMaxNum()));
            response.addValue(g.getId() + "_is_private", String.valueOf(g.isPrivate()));
            response.addValue(g.getId() + "_nocc", String.valueOf(g.getClients().size()));
        }
        if (col.isEmpty()) {
            response.addValue("id_groups", null);
        } else {
            String ids = sbIds.substring(0, sbIds.length()-1);
            response.addValue("id_groups", ids);
        }
        return response;
    }
}
