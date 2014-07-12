package terminal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class Language {

    private Properties lang;

    public Language() {
        try {
            Properties config = new Properties();
            config.load(new FileInputStream(new File("config.txt")));
            String language = config.getProperty("language");
            lang = new Properties();
            lang.load(new FileInputStream(new File("lang" + File.separator + language + ".txt")));
        } catch (IOException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getGroupName() {
        return lang.getProperty("group_name");
    }

    public String getMaxNum() {
        return lang.getProperty("max_num");
    }

    public String getGroupId() {
        return lang.getProperty("group_id");
    }

    public String getAdminId() {
        return lang.getProperty("admin_id");
    }

    public String getPassword() {
        return lang.getProperty("password");
    }

    public String getClient() {
        return lang.getProperty("client");
    }

    public String getIP() {
        return lang.getProperty("ip");
    }

    public String getTheClient() {
        return lang.getProperty("the_client");
    }

    public String getNotExists() {
        return lang.getProperty("not_exists");
    }

    public String getName() {
        return lang.getProperty("name");
    }

    public String getGroups() {
        return lang.getProperty("groups");
    }

    public String getCurrentClients() {
        return lang.getProperty("current_clients");
    }

    public String getCurrentGroups() {
        return lang.getProperty("current_groups");
    }

    public String getInvalidCommand() {
        return lang.getProperty("invalid_command");
    }
}
