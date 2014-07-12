package syntactic;

import executors.Executor;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public interface Command {

    /**
     * @param cadena es la cadena a analizar.
     * @return True si la cadena corresponde a un
     */
    public abstract boolean analyze(String cadena);

    public abstract boolean isGoodFormed();

    public abstract Executor getExecutor();

}
