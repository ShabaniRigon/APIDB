package serverrest;

/**
 * MAIN
 * Punto di partenza dell'applicazione.
 */
public class App {
    public static void main(String[] args) {
        int porta = 8080;
        ServerRest.avvia(porta);
    }
}
