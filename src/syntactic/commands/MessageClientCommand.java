package syntactic.commands;

import executors.Executor;
import executors.MessageClientExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 * MESSAGE_CLIENT 'id_client' 'texto': (resp, error, error_info)
 * 
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class MessageClientCommand implements Command{

    private GenericCommand gen;
    public MessageClientCommand(){
        gen = new GenericCommand("MESSAGE_CLIENT", 2);
    }
    @Override
    public boolean analyze(String cadena) {
        return gen.analyze(cadena);
    }

    @Override
    public boolean isGoodFormed() {
        return gen.isGoodFormed();
    }

    public String getIdClient(){
        return (String) gen.getOptionFixed(0);
    }
    
    public String getText(){
        return (String) gen.getOptionFixed(1);
    }
    
    @Override
    public Executor getExecutor() {
        return new MessageClientExecutor(this);
    }
}
