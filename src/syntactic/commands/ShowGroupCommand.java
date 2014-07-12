package syntactic.commands;

import executors.Executor;
import executors.ShowGroupExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class ShowGroupCommand implements Command {
private GenericCommand gen;

    public ShowGroupCommand() {
        gen = new GenericCommand("SHOW_GROUP", 1);
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
        return new ShowGroupExecutor(this);
    }
    
    public String getIdGroup(){
        return (String)gen.getOptionFixed(0);
    }
}