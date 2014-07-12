package syntactic.commands;

import executors.Executor;
import executors.LeaveGroupExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class LeaveGroupCommand implements Command {

    private GenericCommand gen;

    public LeaveGroupCommand() {
        gen = new GenericCommand("LEAVE_GROUP", 1);
    }

    public String getIdGroup() {
        return (String) gen.getOptionFixed(0);
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
        return new LeaveGroupExecutor(this);
    }
}
