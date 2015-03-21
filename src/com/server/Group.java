package com.server;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que representa un grupo en el servidor.
 *
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.1
 */
public class Group {

    private static final int DEFAULT_MAX_NUM = 8;
    private final HashMap<ClientRunnable, Attributes> clients;
    private final String groupName;
    private Password password;
    private int maxNum;
    private ClientRunnable clientAdmin;
    /**
     * Se usa para determinar si al desconectarse el admin, se debe borrar el
     * grupo o no.
     */
    private boolean forceToDelete;
    /**
     * Si este atributo es null indica que todavia este grupo no ha sido
     * agregado al <code>ServerController</code>
     */
    private final Attributes attributes;
    private String idGroup;

    public Group(String groupName, ClientRunnable clientAdmin) {
        this.clients = new HashMap<>();
        this.clients.put(clientAdmin, new Attributes());
        this.groupName = groupName;
        this.clientAdmin = clientAdmin;
        this.maxNum = DEFAULT_MAX_NUM;
        this.idGroup = null;
        this.attributes = new Attributes();
        this.forceToDelete = false;
    }

    public void addClient(ClientRunnable cr) {
        this.clients.put(cr, new Attributes());
    }

    public boolean removeClient(ClientRunnable cr) {
        return this.clients.remove(cr) != null;
    }

    /**
     *
     * @param cr es el cliente
     * @return true si el cliente ya está conectado al grupo. False en caso
     * contrario.
     */
    public boolean existsClient(ClientRunnable cr) {
        return this.clients.containsKey(cr);
    }

    public boolean isValidPassword(String pass) {
        return this.password.isValid(pass);
    }

    @Override
    public String toString() {
        return this.groupName + "\t" + this.idGroup + "\t\t" + clientAdmin.getId() + "\t\t\t" + this.maxNum + "\t\t" + this.password;
    }
    /*-************GETTERS**********************-*/

    /**
     *
     * @return Devuelve el Id del Grupo, si es <code>null</code> es porque el
     * grupo todavía no es funcional.
     */
    public String getId() {
        return idGroup;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public Attributes getClientAttributes(ClientRunnable cr) {
        return this.clients.get(cr);
    }

    public String getGroupName() {
        return groupName;
    }

    public Password getPassword() {
        return password;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public ClientRunnable getClientAdmin() {
        return clientAdmin;
    }

    public ArrayList<ClientRunnable> getClients() {
        return new ArrayList(this.clients.keySet());
    }

    public static int getDEFAULT_MAX_NUM() {
        return DEFAULT_MAX_NUM;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public boolean isForceToDelete() {
        return this.forceToDelete;
    }

    /*-************SETTERS***********************-*/
    public void setId(String idGroup) {
        this.idGroup = idGroup;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public void setForceToDelete(boolean forceToDelete) {
        this.forceToDelete = forceToDelete;
    }

    public void setNextClientAdmin() {
        this.clientAdmin = getClients().get(0);
    }

    /**
     *
     * @param pass Es la clave del grupo, si pass es null entonces el grupo no
     * tiene clave.
     */
    public void setPassword(String pass) {
        if (pass != null) {
            this.password = new Password(pass);
        }
    }

    /**
     *
     * @return true si el grupo está lleno, es decir, no puede ingresar ningún
     * cliente más. Devuelve false caso contrario.
     */
    public boolean isFull() {
        return maxNum == this.clients.size();
    }

    /**
     *
     * @return true si el grupo necesita clave para conectarse.
     */
    public boolean isPrivate() {
        return password != null;
    }
}
