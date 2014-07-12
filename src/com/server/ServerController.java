package com.server;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import com.stat.CounterStat;

/**
 * ES SINGLETON
 *
 * @author Parisi Germán &  Bertola Federico
 * @version 1.0
 */
public class ServerController implements Fachada{

    private CounterStat counter;
    private static ServerController me;
    private Map<String, ClientRunnable> clients = Collections.synchronizedMap(new TreeMap<String, ClientRunnable>());
    private Map<String, Group> groups = Collections.synchronizedMap(new TreeMap<String, Group>());
    private StringBuilder currentIdGroup;
     /** 
     * Lleva el ID del próximo cliente a conectarse.
     */
    private StringBuilder currentIdClient;
    
    private ServerController() {
        this.currentIdGroup = new StringBuilder("000");
        this.currentIdClient  = new StringBuilder("000");
        this.counter = CounterStat.getIntance();
    }

    public static ServerController getInstance() {
        if (me == null) {
            me = new ServerController();
        }
        return me;
    }

    /**
     *
     * @param cr Cliente
     * @return true
     */
    public void addClient(ClientRunnable cr) {
        assert cr != null;
        cr.setId(currentIdClient.toString());
        clients.put(currentIdClient.toString(), cr);
        
        Helper.nextId(currentIdClient);  
    }

    @Override
    public ClientRunnable getClient(String idClient) {
        assert idClient != null;
        return clients.get(idClient);
    }

    public ClientRunnable removeClient(String idClient) {
        assert idClient != null;
        return clients.remove(idClient);
    }

    /**
     * Al agregar un grupo, este método se encarga de establecer el Id al grupo.
     *
     * @param g Grupo a agregar
     */
    public synchronized void addGroup(Group g) {
        groups.put(currentIdGroup.toString(), g);
        g.setId(currentIdGroup.toString());
        Helper.nextId(currentIdGroup);
    }

    @Override
    public Group getGroup(String idGroup) {
        assert idGroup != null;
        return groups.get(idGroup);
    }

    public Group removeGroup(String idGroup) {
        return this.groups.remove(idGroup);
    }

    @Override
    public synchronized Collection<Group> getGroupsList() {
        return groups.values();
    }

    @Override
    public synchronized Collection<ClientRunnable> getClientList() {
        return clients.values();
    }

    public void incrementCountAcceptedClients() {
        this.counter.incrementCountAcceptedClients();
    }

    public void incrementCountIgnoredClients() {
        this.counter.incrementCountIgnoredClients();
    }

    public void incrementCountSentImages() {
        this.counter.incrementCountSentImages();
    }
    
    public void incrementCountSentObject(){
        this.counter.incrementCountSentObjects();
    }

    @Override
    public long getCountTotalClients() {
        return this.counter.getCountTotalClients();
    }

    @Override
    public long getCountAcceptedClients() {
        return this.counter.getCountAcceptedClients();
    }

    @Override
    public long getCountIgnoredClients() {
        return this.counter.getCountIgnoredClients();
    }

    @Override
    public long getCountSendImages() {
        return this.counter.getCountSentImages();
    }
}
