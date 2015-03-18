package executors;

import com.server.ClientRunnable;
import executors.response.CommandResponse;
import syntactic.commands.JoinCommand;
import com.server.Group;
import com.server.ServerController;
import executors.response.InformationResponse;

/**
 * JOIN 'id_group' <-p 'clave'>: (id_group, error, error_info)
 *
 *
 * Si el comando tuvo éxito entonces la clave error y error_info es null.
 *
 * TABLA DE ERRORES: NOT_EXISTS_GROUP (1): No existe el grupo indicado.
 * GROUP_IS_FULL (3): El grupo está lleno. DUPLICATE_IN_GROUP (4): El cliente ya
 * está conectado al grupo. NOT_PASSWORD (5): Falta determinar la clave.
 * INVALID_KEY (6): La clave es incorrecta.
 *
 * KEYS: <code>id_group</code>: Id del grupo al que te uniste.
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.0
 */
public class JoinExecutor extends Executor {

    private JoinCommand cmd;

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
            group.addClient(client);
            client.addGroup(group);
            InformationResponse infResp = new InformationResponse();
            infResp.addId(client.getId());
            infResp.addSource("join");
            infResp.addValue("id_group", idGroup);
            for (ClientRunnable c : group.getClients()) {
                if(!c.equals(client)){
                    c.send(infResp);
                }
            }
            resp.addValue("id_group", group.getId());
            resp.addValue("id_admin", group.getClientAdmin().getId());
        }
        return resp;
    }
}
