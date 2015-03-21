package terminal;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.ArgumentCompletor;
import jline.ConsoleReader;
import jline.History;
import jline.SimpleCompletor;
import com.server.ClientRunnable;
import com.server.Fachada;
import com.server.Group;
import com.server.ServerController;
import drawingconsole.DrawingPabex;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Parisi Germán y  Bertola Federico
 * @version 1.0
 */
public class Terminal implements Runnable {

    private Fachada sc;
    private DrawingPabex drawing;
    private ConsoleReader console;
    private String PROMPT;
    private Language lang;

    public Terminal(Fachada fachada) {
        this.sc = fachada;
        //drawing = DrawingPabex.getInstance();
        //PROMPT = ANSICodes.GREEN + "PaBex$ " + ANSICodes.OFF;
        PROMPT = "PaBex$ ";
        this.lang = new Language();
    }

    @Override
    public void run() {
        try {
            console = new ConsoleReader();
            console.setBellEnabled(true);
            console.flushConsole();
            List cmds = new LinkedList();
            cmds.add(new SimpleCompletor(new String[]{"help", "groups", "clients", "count", "counth", "clear", "draw", "countimg", "meminfo"}));
            console.addCompletor(new ArgumentCompletor(cmds));

            History history = new History();
            history.setMaxSize(30);

            // draw();
            clear();

            //   System.out.println("\n\n\033[00;00mBienvenido a la terminal del servidor PaBex");
            String titulo = "Bienvenido a la terminal del servidor PaBex";
            //int col = 169 - ((170 - titulo.length()) / 2);
            int col = 80 - ((80 - titulo.length()) / 2);
            System.out.printf("\n\n%" + col + "s\n", titulo);
            String cmd;
            while (true) {
                cmd = console.readLine(PROMPT).trim();
                history.addToHistory(cmd);
                if (cmd.equalsIgnoreCase("help")) {
                    System.out.println("clear");
                    System.out.println("clients <id_client>");
                    System.out.println("cmd");
                    System.out.println("count <clients|groups>");
                    System.out.println("counth");
                    System.out.println("countimg");
                    System.out.println("draw");
                    System.out.println("groups <id_group>");
                    continue;
                }
                if (cmd.trim().equals("")) {
                    continue;
                }
                String a[] = cmd.split(" ");
                switch (a[0]) {
                    case "groups":
                        if (a.length == 1) {
                            // Mostrar todos los grupos.
                            Collection<Group> groups = sc.getGroupsList();
                            System.out.println("");
                            System.out.printf("%-20s   %-10s   %-10s   %-10s   %-20s\n", lang.getGroupName(), lang.getGroupId(), lang.getAdminId(), lang.getMaxNum(), lang.getPassword());
                            System.out.println("---------------------------------------------------------------------------------");
                            for (Group g : groups) {
                                System.out.printf("\n%-20s   %-10s   %-10s   %-10d   %-20s", g.getGroupName(), g.getId(), g.getClientAdmin().getId(), g.getMaxNum(), g.getPassword());
                            }
                            System.out.println("");
                        } else if (a.length > 1) {
                            Group g = sc.getGroup(a[1]);
                            if (g == null) {
                                System.out.println("El grupo " + a[1] + " " + lang.getNotExists());
                            } else {
                                System.out.println(lang.getGroupName() + ": " + g.getGroupName());
                                System.out.println(lang.getGroupId() + ": " + g.getId());
                                System.out.println(lang.getAdminId() + ": " + g.getClientAdmin().getId());
                                System.out.println(lang.getMaxNum() + ": " + g.getMaxNum());
                                System.out.println(lang.getPassword() + ": " + g.getPassword());
                                System.out.println("---------------------------------");
                                System.out.println(lang.getClient() + "\t\t" + lang.getIP());
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
                                System.out.println(lang.getClient() + " " + a[1] + " " + lang.getNotExists());
                            } else {
                                System.out.println("\tId Cliente: " + cr.getId());
                                System.out.println("\t" + lang.getName() + ": " + cr.getAttributes().getAttribute("name"));
                                System.out.println("\t" + lang.getIP() + ": " + cr.getIP());
                                System.out.println("\t\t" + lang.getGroups() + ":");
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
                            System.out.println(lang.getCurrentClients() + ": " + sc.getClientList().size());
                            System.out.println(lang.getCurrentGroups() + ": " + sc.getGroupsList().size());
                        } else if (a.length > 1) {
                            switch (a[1]) {
                                case "clients":
                                    System.out.println(sc.getClientList().size());
                                    break;
                                case "groups":
                                    System.out.println(sc.getGroupsList().size());
                                    break;
                                default:
                                    System.out.println(lang.getInvalidCommand());
                            }
                        } else {
                            System.out.println(lang.getInvalidCommand());
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
                    case "clear":
                        clear();
                        break;
                    case "countimg":
                        showCountSendImages();
                        break;
                    case "draw":
                        draw();
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        execCommand(cmd);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showCountSendImages() {
        long count = sc.getCountSendImages();
        System.out.printf("\n   Cantidad de imágenes enviadas: %d\n", count);
    }

    private void draw() {
        // clear();
        //System.out.println(drawing.getDrawing().toString());

    }

    /**
     * Limpiar la pantalla.
     */
    private void clear() {
        try {
            console.setDefaultPrompt(""); // Se cambia el prompt para que lo muestre una sola vez.
            console.clearScreen();
            draw();
        } catch (IOException ex) {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void execCommand(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            if (p == null) {
                return;
            }

            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String cadena = br.readLine();
            while (cadena != null) {
                System.out.println(cadena);
                cadena = br.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Comando inválido...");
        }
    }
}
