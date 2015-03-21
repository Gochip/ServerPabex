package executors;

import executors.response.CommandResponse;
import executors.response.MessageResponse;
import java.util.HashSet;
import syntactic.commands.MessageGroupCommand;
import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;

/**
 * Ejecutor del comando MESSAGE_GROUP 'id_group' 'mensaje' [-e 'excluido1,
 * excluido2, ..., excluidoN']: (error, error_info, warning, warning_info,
 * count)
 *
 * Manda el mensaje a todos los integrantes del grupo excluyéndose a sí mismo u
 * opcionalmente a otros.
 *
 * <h2>TABLA DE ERRORES:</h2>
 * <p>NOT_EXISTS_GROUP (1): El grupo indicado no existe.</p>
 * <p>NOT_CLIENT_IN_GROUP (2): El cliente que ejecutó este comando no pertenece
 * al grupo.</p>
 *
 * <h2>TABLA DE PELIGROS:</h2>
 * <p>INVALID_CLIENT (1): Un cliente excluído no existe.</p>
 * <p>NOT_EXISTS_CLIENT_IN_GROUP (2): Un cliente excluído no pertenece al
 * grupo.</p>
 *
 * <h2>KEYS:</h2>
 * <p><code>error</code></p>
 * <p><code>error_info</code></p>
 * <p><code>warning</code></p>
 * <p><code>warning_info</code></p>
 * <p><code>count</code>: Cantidad de destinarios del mensaje.</p>
 *
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class MessageGroupExecutor extends Executor {

    private MessageGroupCommand cmd;
    private CommandResponse resp;

     public MessageGroupExecutor(MessageGroupCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        resp = new CommandResponse();
        resp.addOption("count", null);
        String idGroup = cmd.getIdGroup();
        Group group = ServerController.getInstance().getGroup(idGroup);
        HashSet<ClientRunnable> clientsToExclude = new HashSet<>();
        if (group != null) {
            if (group.existsClient(client)) {
                String excludes = cmd.getExcludedClients();
                if (excludes == null) {
                    // Exec sin excluir a nadie.
                } else {
                    String a[] = excludes.split(",");
                    for (int i = 0; i < a.length; i++) {
                        a[i] = a[i].trim();
                        ClientRunnable c = ServerController.getInstance().getClient(a[i]);
                        if (c == null) {
                            resp.appendWarning(EAW.WAR.INVALID_CLIENT.getWarning(), "No existe el cliente " + a[i]);
                        } else {
                            if (group.existsClient(c)) {
                                clientsToExclude.add(c);
                            } else {
                                resp.appendWarning(EAW.WAR.NOT_EXISTS_CLIENT_IN_GROUP.getWarning(), EAW.WAR.NOT_EXISTS_CLIENT_IN_GROUP.getInfo(a[i], idGroup));
                            }
                        }
                    }
                }
            } else {
                resp.addError(EAW.ERR.NOT_CLIENT_IN_GROUP.getError(), EAW.ERR.NOT_CLIENT_IN_GROUP.getInfo(client.getId(), idGroup));
            }
        } else {
            resp.addError(EAW.ERR.NOT_EXISTS_GROUP.getError(), EAW.ERR.NOT_EXISTS_GROUP.getInfo(idGroup));
            return resp;
        }

        /**
         * ************EJECUCIÓN****************
         */
        clientsToExclude.add(client);
        int cantidad = group.getClients().size() - clientsToExclude.size();
        if (cantidad > 0) {
            resp.addOption("count", String.valueOf(cantidad));
            MessageResponse mr = new MessageResponse();
            mr.addText(cmd.getText());
            mr.addId(client.getId());
            for (ClientRunnable cr : group.getClients()) {
                if (!clientsToExclude.contains(cr)) {
                    cr.send(mr);
                }
            }
        }
        return resp;
    }
}
