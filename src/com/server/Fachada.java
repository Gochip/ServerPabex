package com.server;

import java.util.Collection;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public interface Fachada {

    public Collection<Group> getGroupsList();

    public Group getGroup(String string);

    public Collection<ClientRunnable> getClientList();

    public ClientRunnable getClient(String string);

    public long getCountSendImages();
    
    public long getCountIgnoredClients();
    
    public long getCountAcceptedClients();
    
    public long getCountTotalClients();    
}
