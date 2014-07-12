package executors;

import com.difusion.ImageDifusion;
import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;
import executors.response.CommandResponse;

/**
 * :(count,group_size,error,error_info)
 * 
 * TABLA DE ERRORES:
 * NOT_EXISTS_GROUP(1)
 * 
 * KEYS:
 * count : Cantidad de clientes que se le envio la imagen.
 * group_size : Cantidad de clientes del grupo.
 * 
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.0
 */
public class ImageDifusionExecutor extends Executor {

    private ImageDifusion image;

    public ImageDifusionExecutor(ImageDifusion img) {
        this.image = img;
    }

    @Override
    public Response execute() {
        int count = 0;
        CommandResponse cr = new CommandResponse();
        Group g = ServerController.getInstance().getGroup(image.getIdGroup());
        if (g == null) {
            //No existe el grupo
            cr.addError(EAW.ERR.NOT_EXISTS_GROUP.getError(), EAW.ERR.NOT_EXISTS_GROUP.getInfo(image.getIdGroup()));
            cr.addOption("group", "0");
        } else {
            //Existe el grupo
            for (ClientRunnable c : g.getClients()) {
                if (!c.equals(client)) {
                    c.send(image);
                    count++;
                }
            }
            cr.addOption("group_size", String.valueOf(g.getClients().size()));
            ServerController.getInstance().incrementCountSentImages();
        }
        cr.addOption("count", String.valueOf(count));
        return cr;
    }
}
