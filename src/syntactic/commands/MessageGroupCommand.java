package syntactic.commands;

import executors.Executor;
import executors.MessageGroupExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 * MESSAGE_GROUP 'id_group' 'mensaje' <-e 'excluido1, excluido2, ..., excluidoN'>: (error, error_info, warning, warning_info, count)
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class MessageGroupCommand implements Command {

    private GenericCommand gen;

    public MessageGroupCommand() {
        gen = new GenericCommand("MESSAGE_GROUP", 2);
        gen.addOptionVariable("e", GenericCommand.Type.STRING);
    }

    @Override
    public boolean analyze(String cadena) {
        return gen.analyze(cadena);
    }

    @Override
    public boolean isGoodFormed() {
        return gen.isGoodFormed();
    }

    public String getIdGroup() {
        return (String) gen.getOptionFixed(0);
    }

    public String getText() {
        return (String) gen.getOptionFixed(1);
    }
    
    public String getExcludedClients(){
        Object obj = gen.getOptionVariable("e");
        if (obj != null){
            return (String)obj;
        }else{
            return null;
        }
    }

    @Override
    public Executor getExecutor() {
        return new MessageGroupExecutor(this);
    }
}
