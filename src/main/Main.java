package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.server.Server;
import terminal.Terminal;

/**
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        try {
            com.server.Server server = new Server("123", 20202);
            Terminal terminal = new Terminal(server.getFachada());
            Thread th = new Thread(terminal);
            th.start();
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
