package syntactic.commands;

import executors.Executor;
import executors.JoinExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 * Esta clase analiza y determina si una cadena de comando JOIN está bien
 * formada.
 * 
 * JOIN 'id_group' [-p 'clave']: (resp, error, error_info, warning, warning_info)
 * 
 * 
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class JoinCommand implements Command {

    private GenericCommand gen;

    public JoinCommand() {
        gen = new GenericCommand("JOIN", 1);
        gen.addOptionVariable("p", GenericCommand.Type.STRING);
    }

    @Override
    public boolean analyze(String cadena) {
        return gen.analyze(cadena);
    }
    
    public String getIdGroup(){
        return (String)gen.getOptionFixed(0);
    }
    
    public String getPassword(){
        Object pass = gen.getOptionVariable("p");
        if(pass == null){
            return null;
        }
        return (String) pass;
    }
    
    @Override
    public boolean isGoodFormed(){
        return gen.isGoodFormed();
    }

    @Override
    public Executor getExecutor() {
        return new JoinExecutor(this);
    }
}
