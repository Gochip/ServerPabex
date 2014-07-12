package syntactic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * SINGLETON
 * 
 * Fábrica de comandos. Recibe una cadena y devuelve el comando asociado.
 * 
 * @author Parisi Germán & Bertola Federico
 * @version 1.1
 */
public class Commands {

    private Command cmd;
    private static Commands me;
    protected String c;
    private static ArrayList<Pair<String, String>> commands;

    private Commands() {
        commands = new ArrayList<>();
        init();
    }

    public static Commands getInstance() {
        if (me == null) {
            me = new Commands();
        }
        return me;
    }

    /**
     *
     * @return <code>true</code> si el comando esta bien escrito,
     * <code>null</code> en caso que esté mal escrito.
     */
    private boolean analyze() {
        assert commands != null;

        String word = this.c.split(" ")[0];
        for (Pair<String, String> pair : commands) {
            if (word.equalsIgnoreCase(pair.getX())) {
                try {
                    Class clase = Class.forName(pair.getY());
                    cmd = (Command) clase.newInstance();
                    return (cmd.analyze(this.c));
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
                    Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    /**
     *
     * @return el comando si esta bien formado, caso contrario retorna
     * <code>null</code>
     */
    public Command getCommand(String c) {
        this.c = c;
        if (!analyze()) {
            this.cmd = null;
        }
        return cmd;
    }

    private void init() {
        commands.add(new Pair<>("CREATE_GROUP", "syntactic.commands.CreateGroupCommand"));
        commands.add(new Pair<>("JOIN", "syntactic.commands.JoinCommand"));
        commands.add(new Pair<>("SHOW_GROUPS", "syntactic.commands.ShowGroupsCommand"));
        commands.add(new Pair<>("MESSAGE_CLIENT", "syntactic.commands.MessageClientCommand"));
        commands.add(new Pair<>("MESSAGE_GROUP", "syntactic.commands.MessageGroupCommand"));
        commands.add(new Pair<>("DISCONNECT", "syntactic.commands.DisconnectCommand"));
        commands.add(new Pair<>("SHOW_GROUP", "syntactic.commands.ShowGroupCommand"));
        commands.add(new Pair<>("ADD_ATTRIBUTE", "syntactic.commands.AddAttributeCommand"));
        commands.add(new Pair<>("GET_ATTRIBUTE", "syntactic.commands.GetAttributeCommand"));
        commands.add(new Pair<>("REMOVE_ATTRIBUTE", "syntactic.commands.RemoveAttributeCommand"));
        commands.add(new Pair<>("LEAVE_GROUP", "syntactic.commands.LeaveGroupCommand"));
    }
}
