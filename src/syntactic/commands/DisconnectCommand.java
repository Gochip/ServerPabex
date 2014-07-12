package syntactic.commands;

import executors.DisconnectExecutor;
import executors.Executor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class DisconnectCommand implements Command{

    private GenericCommand gen;
    
    public DisconnectCommand(){
        gen = new GenericCommand("DISCONNECT", 0);
    }

    @Override
    public boolean analyze(String cadena) {
        return gen.analyze(cadena);
    }

    @Override
    public boolean isGoodFormed() {
        return gen.isGoodFormed();
    }

    @Override
    public Executor getExecutor() {
        return new DisconnectExecutor();
    }

}
