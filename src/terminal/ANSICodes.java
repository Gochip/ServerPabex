package terminal;

/**
 *
 * @author Bertola Federico
 * @version 1.0
 */
class ANSICodes {

    static final char ESC = 27;
    static final String OFF = ESC + "[0m";
    /**
     * Color de Texto
     */
    static final String BLACK = ESC + "[30m";
    static final String RED = ESC + "[31m";
    static final String GREEN = ESC + "[32m";
    static final String YELLOW = ESC + "[33m";
    static final String BLUE = ESC + "[34m";
    static final String MAGENTA = ESC + "[35m";
    static final String CYAN = ESC + "[36m";
    static final String WHITE = ESC + "[37m";
    /**
     * Colores de fondo
     */
    static final String BLACK_BG = ESC + "[40m";
    static final String RED_BG = ESC + "[41m";
    static final String GREEN_BG = ESC + "[42m";
    static final String YELLOW_BG = ESC + "[43m";
    static final String BLUE_BG = ESC + "[44m";
    static final String MAGENTA_BG = ESC + "[45m";
    static final String CYAN_BG = ESC + "[46m";
    static final String WHITE_BG = ESC + "[47m";
    
    /**
     * Formato de Texto
     */
    static final String BOLD_TXT = ESC + "[1m";
    static final String UNDERSCOREBOLD_TXT = ESC + "[4m";
    static final String BLINKBOLD_TXT = ESC + "[5m";
    static final String REVERSEBOLD_TXT = ESC + "[7m";
    static final String CONCEALEDBOLD_TXT = ESC + "[8m";

    public static void main(String args[]) {
        System.out.println(BLACK + "TEXTO NEGRO");
        System.out.println(RED + "TEXTO ROJO");
        System.out.println(GREEN + "TEXTO VERDE");
        System.out.println(YELLOW + "TEXTO AMARILLO");
        System.out.println(BLUE + "TEXTO AZUL");
        System.out.println(MAGENTA + "TEXTO VIOLETA");
        System.out.println(CYAN + "TEXTO TURQUESA");
        System.out.println(WHITE + "TEXTO BLANCO??" + OFF);

        System.out.println(BLACK_BG + "FONDO NEGRO");
        System.out.println(RED_BG + "FONDO ROJO");
        System.out.println(GREEN_BG + "FONDO VERDE");
        System.out.println(YELLOW_BG + "FONDO AMARILLO");
        System.out.println(BLUE_BG + "FONDO AZUL");
        System.out.println(MAGENTA_BG + "FONDO VIOLETA");
        System.out.println(CYAN_BG + "FONDO TURQUESA");
        System.out.println(WHITE_BG + "FONDO BLANCO??"+OFF);
        
        System.out.println(BOLD_TXT+"BOLD");
        System.out.println(UNDERSCOREBOLD_TXT+"UNDERSCOREBOLD_TXT");
        System.out.println(BLINKBOLD_TXT+"BLINKBOLD_TXT");
        System.out.println(REVERSEBOLD_TXT+"REVERSEBOLD_TXT");
        System.out.println(CONCEALEDBOLD_TXT+"CONCEALEDBOLD_TXT");
    }
}
