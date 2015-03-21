package syntactic.commands;

import executors.CreateGroupExecutor;
import executors.Executor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 * CREATE_GROUP 'nombre_grupo' [-p 'clave'] [-n 'numero_maxímo']: (resp, error,
 * error_info)
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class CreateGroupCommand implements Command {

    private GenericCommand gen;

    public CreateGroupCommand() {
        gen = new GenericCommand("CREATE_GROUP", 1);
        gen.addOptionVariable("p", GenericCommand.Type.STRING);
        gen.addOptionVariable("n", GenericCommand.Type.NATURAL);
        gen.addOptionVariable("f", GenericCommand.Type.STRING);
    }

    @Override
    public boolean analyze(String cadena) {
        return gen.analyze(cadena);
    }

    public String getGroupName() {
        return (String) gen.getOptionFixed(0);
    }

    public Integer getMaxNum() {
        Object in = gen.getOptionVariable("n");
        if (in == null) {
            return null;
        }
        return (Integer) in;
    }

    public String getPassword() {
        Object in = gen.getOptionVariable("p");
        if (in == null) {
            return null;
        }
        return (String) in;
    }

    public boolean isForceToDelete() {
        Object in = gen.getOptionVariable("f");
        if (in == null) {
            return false;
        }
        return ((String) in).equalsIgnoreCase("yes");
    }

    @Override
    public boolean isGoodFormed() {
        return gen.isGoodFormed();
    }

    @Override
    public Executor getExecutor() {
        return new CreateGroupExecutor(this);
    }
}
