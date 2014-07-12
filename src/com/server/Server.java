package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal del servidor. Se usa al comienzo de la conexión de un
 * cliente.
 *
 * Esta clase posee una clase interna que verifica la clave para establecer la
 * conexión.
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.0
 */
public class Server {

    /**
     * El puerto por el cual el server escucha.
     */
    private final int PORT;

    /**
     * Es el controlador del server.
     */
    private final ServerController serverController = ServerController.getInstance();

    /**
     * Es la clave del framework que permite establecer la conexión la primer
     * vez.
     */
    private final String PASSWORD;

    public Server(String pass, int puerto) {
        this.PASSWORD = pass;
        this.PORT = puerto;
    }

    public void start() throws IOException {
        ServerSocket ss = new ServerSocket(PORT);

        while (true) {
            Socket s = ss.accept();
            AcceptClient ac = new AcceptClient(s);
            Thread tt = new Thread(ac);
            tt.start();
        }
    }

    public Fachada getFachada() {
        return serverController;
    }

    /**
     * Esta clase determina si se debe aceptar a un cliente o no teniendo en
     * cuenta la clave de framework.
     *
     * Si la clave es correcta entonces lo agrega en el ServerController. Caso
     * contrario, rechaza la conexión.
     */
    class AcceptClient implements Runnable {

        Socket s;

        AcceptClient(Socket s) {
            this.s = s;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                String p = ois.readUTF();
                if (p.equals(PASSWORD)) {
                    ClientRunnable cr = new ClientRunnable(s, ois);
                    serverController.incrementCountAcceptedClients();
                    serverController.addClient(cr);

                    Thread t = new Thread(cr);
                    t.start();
                } else {
                    serverController.incrementCountIgnoredClients();
                    s.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
