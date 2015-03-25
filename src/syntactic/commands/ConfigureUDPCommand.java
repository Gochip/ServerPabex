package syntactic.commands;

import executors.AddAttributeExecutor;
import executors.ConfigureUDPExecutor;
import executors.Executor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class ConfigureUDPCommand implements Command{

    GenericCommand gen;

    public ConfigureUDPCommand() {
        gen = new GenericCommand("CONFIGURE_UDP", 1);
        gen.setTypeOptionFixed(0, GenericCommand.Type.NATURAL);
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
        return new ConfigureUDPExecutor(this);
    }
    
    public int getPort() {
        Object in = gen.getOptionFixed(0);
        if (in == null) {
            return -1;
        }
        return (Integer) in;
    }
}
