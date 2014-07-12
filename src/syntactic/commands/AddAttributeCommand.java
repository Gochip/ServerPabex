package syntactic.commands;

import executors.AddAttributeExecutor;
import executors.Executor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.1
 */
public class AddAttributeCommand implements Command {

    GenericCommand gen;

    public AddAttributeCommand() {
        gen = new GenericCommand("ADD_ATTRIBUTE", 2);
        gen.addOptionVariable("v", GenericCommand.Type.BOOLEAN);
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
        return new AddAttributeExecutor(this);
    }

    public String getKey() {
        return (String) gen.getOptionFixed(0);
    }

    public String getValue() {
        return (String) gen.getOptionFixed(1);
    }

    public boolean isVerbose() {
        Object v = gen.getOptionVariable("v");
        if (v != null) {
            String verbose = (String) v;
            return verbose.equals("true") || verbose.equals("yes");
        }
        return true;
    }
}
