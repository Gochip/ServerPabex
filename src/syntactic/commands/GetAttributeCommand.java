package syntactic.commands;

import executors.Executor;
import executors.GetAttributeExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class GetAttributeCommand implements Command{
    GenericCommand gen;

    public GetAttributeCommand() {
    gen = new GenericCommand("GET_ATTRIBUTE",1);
    }
    
    
    @Override
    public boolean analyze(String cadena) {
        return this.gen.analyze(cadena);
    }

    @Override
    public boolean isGoodFormed() {
        return this.gen.isGoodFormed();
    }

    @Override
    public Executor getExecutor() {
        return new GetAttributeExecutor(this);
    }
    
    public String getKey(){
        return (String)gen.getOptionFixed(0);
    }
}
