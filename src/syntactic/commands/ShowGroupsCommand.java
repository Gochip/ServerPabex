package syntactic.commands;

import executors.Executor;
import executors.ShowGroupsExecutor;
import syntactic.Command;
import syntactic.GenericCommand;

/**
 * Comando para mostrar todos los grupos en el servidor.
 * 
 * SHOW_GROUPS : (id_groups, xxx_group_name, xxx_id_admin, xxx_max_num)
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class ShowGroupsCommand implements Command {

    private GenericCommand gen;

    public ShowGroupsCommand() {
        gen = new GenericCommand("SHOW_GROUPS", 0);
    }

    @Override
    public boolean analyze(String cadena) {
        return gen.analyze(cadena);
    }
    
    @Override
    public Executor getExecutor() {
        return new ShowGroupsExecutor();
    }
    
    @Override
    public boolean isGoodFormed() {
        return gen.isGoodFormed();
    }
}
