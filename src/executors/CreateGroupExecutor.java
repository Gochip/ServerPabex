package executors;

import executors.response.CommandResponse;
import syntactic.commands.CreateGroupCommand;
import com.server.Group;
import com.server.ServerController;

/**
 * <code>CREATE_GROUP 'nombre_grupo' <-p 'clave'> <-n 'numero_maxímo'> <-f 'yes|no'>: (id_group)</code>
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.1
 */
public class CreateGroupExecutor extends Executor {

    private CreateGroupCommand cmd;

    public CreateGroupExecutor(CreateGroupCommand cmd) {
        this.cmd = cmd;
    }

    /**
     *
     * @return un objeto Response con el id del grupo creado
     */
    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();
        String groupName = cmd.getGroupName();
        Group group = new Group(groupName, client);

        // Forza a eliminar al abandonar el admin
        group.setForceToDelete(cmd.isForceToDelete());

        //Password
        String pass = cmd.getPassword();
        group.setPassword(pass);

        //Máximo número de cliente
        Integer maxNun = cmd.getMaxNum();
        if (maxNun != null) {
            group.setMaxNum(maxNun);
        }

        ServerController.getInstance().addGroup(group);
        client.addGroup(group);

        resp.addValue("id_group", group.getId());
        return resp;
    }
}
