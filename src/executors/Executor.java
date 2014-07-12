package executors;

import com.server.ClientRunnable;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public abstract class Executor {
    public ClientRunnable client;
    public abstract Response execute();
}
