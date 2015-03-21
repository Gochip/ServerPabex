package terminal;

import java.util.Collection;
import java.util.Scanner;
import com.server.ClientRunnable;
import com.server.Group;
import com.server.ServerController;

/**
 *
 * @author Parisi Germán y  Bertola Federico
 * @version 1.0
 */
public class TerminalOld implements Runnable {

    private ServerController sc;

    public TerminalOld(ServerController sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        
        System.out.println("Bienvenido a la terminal del servidor PaBex");
        String cmd;
        while (true) {
            System.out.print("\n# ");
            cmd = s.nextLine();
            if (cmd.equalsIgnoreCase("help")) {
                System.out.println("groups <id_group>");
                System.out.println("clients <id_client>");
                System.out.println("count <clients|groups>");
                System.out.println("counth");
                continue;
            }
            String a[] = cmd.split(" ");
            switch (a[0]) {
                case "groups":
                    if (a.length == 1) {
                        // Mostrar todos los grupos.
                        Collection<Group> groups = sc.getGroupsList();
                        System.out.println("");
                        System.out.printf("%-20s   %-10s   %-10s   %-10s   %-20s\n", "Nombre Grupo", "Id Grupo", "Id Admin", "Max núm", "Clave");
                        System.out.println("---------------------------------------------------------------------------------");
                        for (Group g : groups) {
                            System.out.printf("\n%-20s   %-10s   %-10s   %-10d   %-20s", g.getGroupName(), g.getId(), g.getClientAdmin().getId(), g.getMaxNum(), g.getPassword());
                        }
                        System.out.println("");
                    } else if (a.length > 1) {
                        Group g = sc.getGroup(a[1]);
                        if (g == null) {
                            System.out.println("El grupo " + a[1] + " no existe.");
                        } else {
                            System.out.println("Nombre grupo: " + g.getGroupName());
                            System.out.println("Id grupo: " + g.getId());
                            System.out.println("Id admin: " + g.getClientAdmin().getId());
                            System.out.println("Max núm: " + g.getMaxNum());
                            System.out.println("Clave: " + g.getPassword());
                            System.out.println("---------------------------------");
                            System.out.println("Cliente\t\tIP");
                            for (ClientRunnable cr : g.getClients()) {
                                System.out.println(cr);
                            }
                        }
                    }
                    break;
                case "clients":
                    if (a.length == 1) {
                        // Mostrar todos los grupos.
                        System.out.println("Cliente\t\tIP");
                        for (ClientRunnable cr : sc.getClientList()) {
                            System.out.println(cr);
                        }
                    } else if (a.length > 1) {
                        ClientRunnable cr = sc.getClient(a[1]);
                        System.out.println();
                        if (cr == null) {
                            System.out.println("El cliente " + a[1] + " no existe.");
                        } else {
                            System.out.println("\tId Cliente: " + cr.getId());
                            System.out.println("\tNombre: "+ cr.getAttributes().getAttribute("name"));
                            System.out.println("\tIP: "+cr.getIP());
                            System.out.println("\t\tGrupos:");
                            for (Group g : cr.getGroups()) {
                                System.out.print("\t\t" + g.getId() + " " + g.getGroupName());
                                if (g.getClientAdmin().equals(cr)) {
                                    System.out.println(" soy admin.");
                                } else {
                                    System.out.println("");
                                }
                            }
                        }
                        System.out.println();
                    }
                    break;
                case "count":
                    if (a.length == 1) {
                        System.out.println("Clientes actuales: " + sc.getClientList().size());
                        System.out.println("Grupos actuales: " + sc.getGroupsList().size());
                    } else if (a.length > 1) {
                        switch (a[1]) {
                            case "clients":
                                System.out.println(sc.getClientList().size());
                                break;
                            case "groups":
                                System.out.println(sc.getGroupsList().size());
                                break;
                            default:
                                System.out.println("Comando inválido.");
                        }
                    } else {
                        System.out.println("Comando inválido.");
                    }
                    break;
                case "counth":
                    ServerController sc = ServerController.getInstance();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Actuales: ");
                    sb.append(sc.getClientList().size());
                    sb.append("\tAceptados: ");
                    sb.append(sc.getCountAcceptedClients());
                    sb.append("\tRechazados: ");
                    sb.append(sc.getCountIgnoredClients());
                    sb.append("\tTotal: ");
                    sb.append(sc.getCountTotalClients());
                    sb.append("\n");
                    System.out.println(sb.toString());
                    break;
                default:
                    System.out.println("Comando inválido.");
            }
        }
    }
}
