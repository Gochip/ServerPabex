package executors;

import com.server.ClientRunnable;
import executors.response.CommandResponse;
import syntactic.commands.JoinCommand;
import com.server.Group;
import com.server.ServerController;
import executors.response.InformationResponse;

/**
 * JOIN 'id_group' [-p 'clave']: (id_group, error, error_info)
 *
 *
 * Si el comando tuvo éxito entonces la clave error y error_info es null.
 *
 * TABLA DE ERRORES:
 * <ul>
 *      <li>(1) NOT_EXISTS_GROUP: No existe el grupo indicado.</li>
 *      <li>(3) GROUP_IS_FULL: El grupo está lleno.</li>
 *      <li>(4) DUPLICATE_IN_GROUP: El cliente ya está conectado al grupo.</li>
 *      <li>(5) NOT_PASSWORD: Falta determinar la clave.</li>
 *      <li>(6) INVALID_KEY: La clave es incorrecta.</li>
 * </ul>
 * 
 * KEYS: <code>id_group</code>: Id del grupo al que te uniste.
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.1
 */
public class JoinExecutor extends Executor {

    private final JoinCommand cmd;

    public JoinExecutor(JoinCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();

        String idGroup = cmd.getIdGroup();
        Group group = ServerController.getInstance().getGroup(idGroup);

        boolean ok = false;

        if (group == null) {
            resp.addError(EAW.ERR.NOT_EXISTS_GROUP.getError(), EAW.ERR.NOT_EXISTS_GROUP.getInfo(idGroup));
        } else {
            if (group.isFull()) {
                resp.addError(EAW.ERR.GROUP_IS_FULL.getError(), EAW.ERR.GROUP_IS_FULL.getInfo(idGroup));
            } else {
                if (group.existsClient(client)) {
                    resp.addError(EAW.ERR.DUPLICATE_IN_GROUP.getError(), EAW.ERR.DUPLICATE_IN_GROUP.getInfo());
                } else {
                    if (group.isPrivate()) {
                        String pass = cmd.getPassword();
                        if (pass == null) {
                            resp.addError(EAW.ERR.NOT_PASSWORD.getError(), EAW.ERR.NOT_PASSWORD.getInfo());
                        } else {
                            if (!group.isValidPassword(pass)) {
                                resp.addError(EAW.ERR.INVALID_KEY.getError(), EAW.ERR.INVALID_KEY.getInfo());
                            } else {
                                ok = true;
                            }
                        }
                    } else {
                        ok = true;
                    }
                }
            }
        }

        /*EJECUCIÓN*/
        if (ok) {
            InformationResponse infResp = new InformationResponse();
            infResp.addId(client.getId());
            infResp.addSource("join");
            infResp.addValue("id_group", idGroup);
            for (ClientRunnable c : group.getClients()) {
                c.send(infResp);
            }
            group.addClient(client);
            client.addGroup(group);
            resp.addValue("id_group", group.getId());
            resp.addValue("id_admin", group.getClientAdmin().getId());
        }
        return resp;
    }
}
