package com.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parisi Germán y Barrionuevo Diego
 * @version 1.0
 */
public class ClientRunnableUDP implements Runnable {

    private static ClientRunnableUDP me;
    private DatagramSocket datagram;
    public static int LISTEN_PORT = 6001;

    private ClientRunnableUDP() {
        try {
            datagram = new DatagramSocket(LISTEN_PORT);
        } catch (SocketException ex) {
            Logger.getLogger(ClientRunnableUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ClientRunnableUDP getInstance() {
        if (me == null) {
            me = new ClientRunnableUDP();
        }
        return me;
    }

    public void sendPacket(String data, InetAddress clientAddress, int port) {
        try {
            byte[] buffer = new byte[256];
            byte[] bAux = data.getBytes(Charset.forName("UTF-8"));
            System.arraycopy(bAux, 0, buffer, 0, bAux.length);
            for (int i = bAux.length; i < 256; i++) {
                buffer[i] = 0;
            }
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientAddress, port);
            datagram.send(packet);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientRunnableUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnableUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receivedPacket() {
        try {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagram.receive(packet);
            String msg = new String(buffer, Charset.forName("UTF-8"));
            String partes[] = msg.split("::");
            String idGroup = partes[0];
            String idClient = partes[1];
            String data = partes[2];
            ServerController sc = ServerController.getInstance();
            for (ClientRunnable cr : sc.getGroup(idGroup).getClients()) {
                if (!cr.getId().equals(idClient)) {
                    cr.getClientUDP().sendPacket(data, cr.getIPAddress(), cr.getSendPortUDP());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnableUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            receivedPacket();
        }
    }
}
