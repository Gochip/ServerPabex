package syntactic.commands;

import executors.Executor;
import executors.RemoveAttributeExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class RemoveAttributeCommand implements Command{
private GenericCommand gen;

    public RemoveAttributeCommand() {
        gen = new GenericCommand("REMOVE_ATTRIBUTE",1);
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
        return new RemoveAttributeExecutor(this);
    }

    public String getKey() {
        return (String) this.gen.getOptionFixed(0);
    }

}
