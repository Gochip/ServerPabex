package syntactic;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Permite la creación de un comando fácilmente, con el siguiente formato:
 * NOMBRE_COMANDO opción_fija_1 opción_fija_2 ... opción_fija_n -o1 opc_var_1
 * -o2 opc_var_2 ... -on opc_var_n
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.1
 */
public class GenericCommand {
    
    private boolean isGoodFormed;
    
    public enum Type {
        
        STRING,
        DECIMAL,
        INTEGER,
        NATURAL,
        BOOLEAN
    }
    private String name;
    private int countFixed;
    private ArrayList<Object> valueOptionsFixed;
    private TreeMap<String, Pair<Type, Object>> optionsVariable;
    private final char INDICATOR;
    private ArrayList<Type> typesFixed;
    
    public GenericCommand(String nombre, int optionsFixed) {
        this(nombre, optionsFixed, '-');
    }
    
    public GenericCommand(String nombre, int countFixed, char INDICATOR) {
        this.name = nombre;
        this.countFixed = countFixed;
        this.INDICATOR = INDICATOR;
        this.valueOptionsFixed = new ArrayList<>();
        this.optionsVariable = new TreeMap<>();
        this.typesFixed = new ArrayList<>();
        for (int i = 0; i < countFixed; i++) {
            typesFixed.add(Type.STRING);
        }
    }
    
    public void setTypeOptionFixed(int ind, Type type) {
        typesFixed.set(ind, type);
    }

    /**
     * @param type es el tipo de variable
     * @param name es el nombre de la opción variable.
     */
    public void addOptionVariable(String name, Type type) {
        optionsVariable.put(name, new Pair(type, null));
    }
    
    public boolean analyze(String cadena) {
        if (countFixed == 0) {
            //Agregar '' 
            cadena = cadena.replaceFirst(" ", " '' ");
        }
        
        String[] words = cadena.split("'");

        // Limpieza
        int cont = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
            if (!words[i].equals("")) {
                cont++;
            }
        }
        
        if (cont != 0) {
            String[] wordsCopy = new String[cont];
            int aux = 0;
            for (int i = 0; i < words.length; i++) {
                if (!words[i].equals("")) {
                    wordsCopy[aux] = words[i];
                    aux++;
                }
            }
            words = wordsCopy;
        }
        
        if (words.length < countFixed + 1) {
            return false;
        }
        
        if (!name.equalsIgnoreCase(words[0])) {
            return false;
        }

        // Ignoro la primera...
        // Comienza el análisis para la parte fija del comando.
        for (int i = 1; i < countFixed + 1; i++) {
            Type t = typesFixed.get(i - 1);
            switch (t) {
                case STRING:
                    valueOptionsFixed.add(words[i]);
                    break;
                case DECIMAL:
                    try {
                        valueOptionsFixed.add(Float.parseFloat(words[i]));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                case INTEGER:
                    try {
                        valueOptionsFixed.add(Integer.parseInt(words[i]));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                case NATURAL:
                    try {
                        int number = Integer.parseInt(words[i]);
                        if (number < 0) {
                            return false;
                        }
                        valueOptionsFixed.add(number);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                case BOOLEAN:
                    if (words[i].equals("true") || words[i].equals("yes")) {
                        valueOptionsFixed.add(words[i]);
                    } else {
                        return false;
                    }
                    break;
            }
        }

        //Comienza el análisis para la parte variable del comando.
        // Recorrer las palabras que faltan.
        for (int i = countFixed + 1; i < words.length; i += 2) {
            if (i + 1 >= words.length) {
                return false;
            }
            String p = words[i];
            if (p.charAt(0) == INDICATOR) {
                
                String opt = p.substring(1);
                Pair<Type, Object> pair = optionsVariable.get(opt);
                if (pair == null) {
                    return false;
                }
                
                Type type = pair.getX();
                
                switch (type) {
                    case STRING:
                        pair.setY(words[i + 1]);
                        optionsVariable.put(opt, pair);
                        break;
                    case DECIMAL:
                        try {
                            pair.setY(Float.parseFloat(words[i + 1]));
                            optionsVariable.put(opt, pair);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        break;
                    case INTEGER:
                        try {
                            pair.setY(Integer.parseInt(words[i + 1]));
                            optionsVariable.put(opt, pair);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        break;
                    case NATURAL:
                        try {
                            int number = Integer.parseInt(words[i + 1]);
                            if (number < 0) {
                                return false;
                            }
                            pair.setY(number);
                            optionsVariable.put(opt, pair);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        break;
                    case BOOLEAN:
                        if (words[i + 1].equals("true") || words[i + 1].equals("false") || words[i + 1].equals("yes") || words[i + 1].equals("no")) {
                            pair.setY(words[i + 1]);
                            optionsVariable.put(opt, pair);
                        } else {
                            return false;
                        }
                        break;
                }
            } else {
                return false;
            }
        }
        isGoodFormed = true;
        return true;
    }
    
    public boolean isGoodFormed() {
        return isGoodFormed;
    }
    
    public Object getOptionFixed(int indice) {
        return valueOptionsFixed.get(indice);
    }

    /**
     *
     * @param nombre la opción variable (Sin el guión)
     * @return el valor asociado a la opción, si la opcion no ha sido
     * especificada devuelve null.
     */
    public Object getOptionVariable(String nombre) {
        return optionsVariable.get(nombre).getY();
    }

    //  ************   TESTTTTT**************************/
    public static void main(String[] args) {
        GenericCommand gen = new GenericCommand("SET_CONFIG", 0);
        gen.addOptionVariable("name", GenericCommand.Type.STRING);
        gen.addOptionVariable("v", Type.BOOLEAN);
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        while (!cadena.equals("exit")) {
            System.out.println(gen.analyze(cadena));
            System.out.println(gen.getOptionVariable("v"));
            cadena = sc.nextLine();
        }
    }
    
}
