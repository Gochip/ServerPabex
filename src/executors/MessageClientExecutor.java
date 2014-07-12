/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executors;

import executors.response.MessageResponse;
import executors.response.CommandResponse;
import syntactic.commands.MessageClientCommand;
import com.server.ClientRunnable;
import com.server.ServerController;

/**
 * MESSAGE_CLIENT 'id_client' 'texto': (error, error_info)
 *
 * TABLA DE ERRORES NOT_EXISTS_CLIENT(7): No existe el cliente destinatario
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.0
 */
public class MessageClientExecutor extends Executor {

    private MessageClientCommand cmd;

    public MessageClientExecutor(MessageClientCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public Response execute() {
        CommandResponse resp = new CommandResponse();
        String idClient = cmd.getIdClient();
        ClientRunnable cr = ServerController.getInstance().getClient(idClient);
        if (cr == null) {
            resp.addError(EAW.ERR.NOT_EXISTS_CLIENT.getError(), EAW.ERR.NOT_EXISTS_CLIENT.getInfo(idClient));
        } else {
            MessageResponse respDestinatario = new MessageResponse();
            /*Acá se analizaría los privilegios.*/
            respDestinatario.addId(super.client.getId());
            respDestinatario.addText(cmd.getText());
            cr.send(respDestinatario);
        }
        return resp;
    }
}
