package com.server;

import com.difusion.ImageDifusion;
import com.difusion.ObjectDiffusion;
import executors.DisconnectExecutor;
import executors.EAW;
import executors.response.CommandResponse;
import executors.Executor;
import executors.ImageDifusionExecutor;
import executors.ObjectDiffusionExecutor;
import executors.response.MessageResponse;
import executors.Response;
import executors.response.InformationResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import syntactic.Command;
import syntactic.Commands;

/**
 * Mantiene la conexión con el cliente.
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.1
 */
public class ClientRunnable implements Runnable {

    private Socket s;
    private String clientId;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean disconnected = false;
    private ArrayList<Group> groups;
    private Attributes attributes;
    private ClientRunnableUDP udp;
    private int sendPortUDP;

    /**
     *
     * @param s es el socket del cliente.
     * @param ois se pasa porque ya es creado antes.
     */
    public ClientRunnable(Socket s, ObjectInputStream ois) {
        try {
            this.s = s;
            this.clientId = null;
            oos = new ObjectOutputStream(s.getOutputStream());
            this.ois = ois;
            this.groups = new ArrayList<>();
            this.attributes = new Attributes();
            this.udp = ClientRunnableUDP.getInstance();
            new Thread(udp).start();
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getId() {
        return clientId;
    }

    @SuppressWarnings({"null", "ConstantConditions"})
    @Override
    public void run() {
        try {
            oos.writeUTF(clientId);
            oos.flush();
            String cadena;
            Object objeto;
            while (!disconnected) {
                objeto = ois.readObject();
                if (objeto instanceof String) {
                    cadena = (String) objeto;
                    Command an = Commands.getInstance().getCommand(cadena);
                    boolean b;
                    if (an == null) {
                        b = false;
                    } else {
                        b = an.isGoodFormed();
                    }
                    if (!b) {
                        // Está mal escrito, responder.
                        CommandResponse response = new CommandResponse();
                        response.addError(EAW.ERR.MISSPELLED.getError(), EAW.ERR.MISSPELLED.getInfo(cadena));
                        synchronized (oos) {
                            oos.writeObject(response);
                            oos.flush();
                        }
                    } else {
                        Executor ex = an.getExecutor();

                        ex.client = this;
                        Response respuesta = ex.execute();
                        synchronized (oos) {
                            oos.writeObject(respuesta);
                            oos.flush();
                        }
                    }
                } else if (objeto instanceof ImageDifusion) {
                    ImageDifusion imgDifusion = (ImageDifusion) objeto;
                    ImageDifusionExecutor ide = new ImageDifusionExecutor(imgDifusion);
                    ide.client = this;
                    oos.writeObject(ide.execute());
                    oos.flush();
                } else if (objeto instanceof ObjectDiffusion) {
                    ObjectDiffusion objectDifusion = (ObjectDiffusion) objeto;
                    ObjectDiffusionExecutor ode = new ObjectDiffusionExecutor(objectDifusion);
                    ode.client = this;
                    oos.writeObject(ode.execute());
                    oos.flush();
                }
            }
        } catch (IOException ex) {
            DisconnectExecutor disc = new DisconnectExecutor();
            disc.client = this;
            disc.execute();
            System.out.println("Cliente desconectado de forma imprevista.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void disconnect() {
        this.disconnected = true;
    }

    @Override
    public boolean equals(Object o) {
        return this.clientId.equals(((ClientRunnable) o).clientId);
    }

    @Override
    public int hashCode() {
        return this.clientId.hashCode();
    }
    
    public int getSendPortUDP(){
        return sendPortUDP;
    }
    
    public void setSendPortUDP(int sendPortUDP){
        this.sendPortUDP = sendPortUDP;
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public void addGroup(Group g) {
        this.groups.add(g);
    }

    public boolean removeGroup(Group g) {
        return this.groups.remove(g);
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getIP() {
        return s.getInetAddress().toString();
    }

    public InetAddress getIPAddress() {
        return s.getInetAddress();
    }

    public void send(MessageResponse rs) {
        try {
            synchronized (oos) {
                oos.writeObject(rs);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(InformationResponse rs) {
        try {
            synchronized (oos) {
                oos.writeObject(rs);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(ImageDifusion img) {
        try {
            synchronized (oos) {
                oos.writeObject(img);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(ObjectDiffusion object) {
        try {
            synchronized (oos) {
                oos.writeObject(object);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setId(String id) {
        this.clientId = id;
    }

    @Override
    public String toString() {
        return clientId + "\t\t" + s.getInetAddress();
    }

    public ClientRunnableUDP getClientUDP() {
        return this.udp;
    }
}
