package serverrest;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import serverrest.model.*;
import serverrest.repository.*;

/**
 * Unico file per gli endpoint REST.
 *
 * Qui NON ci sono query SQL: il server riceve le richieste HTTP
 * e chiama i Repository, come indicato nel PDF.
 */
public class ServerRest {

    private static final Gson gson = new Gson();

    private static final UtenteRepository utenti = new UtenteRepositoryImpl();
    private static final ApiarioRepository apiari = new ApiarioRepositoryImpl();
    private static final ArniaRepository arnie = new ArniaRepositoryImpl();
    private static final SensoreRepository sensori = new SensoreRepositoryImpl();
    private static final TipoRilevazioneRepository tipi = new TipoRilevazioneRepositoryImpl();
    private static final SensoreArniaRepository sensoriArnia = new SensoreArniaRepositoryImpl();
    private static final RilevazioneRepository rilevazioni = new RilevazioneRepositoryImpl();

    public static void avvia(int porta) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);

            // Endpoint principali: uno per ogni tabella completa del database.
            server.createContext("/api/utenti", ex -> gestisciCrud(
                    ex, Utente.class, Integer::parseInt, Utente::setUte_id,
                    utenti::findAll, utenti::findById, utenti::save, utenti::update, utenti::deleteById));

            server.createContext("/api/apiario", ex -> gestisciCrud(
                    ex, Apiario.class, Integer::parseInt, Apiario::setApi_id,
                    apiari::findAll, apiari::findById, apiari::save, apiari::update, apiari::deleteById));

            server.createContext("/api/arnie", ex -> gestisciCrud(
                    ex, Arnia.class, Integer::parseInt, Arnia::setArn_id,
                    arnie::findAll, arnie::findById, arnie::save, arnie::update, arnie::deleteById));

            server.createContext("/api/sensori", ex -> gestisciCrud(
                    ex, Sensore.class, Integer::parseInt, Sensore::setSen_id,
                    sensori::findAll, sensori::findById, sensori::save, sensori::update, sensori::deleteById));

            server.createContext("/api/tipi-rilevazione", ex -> gestisciCrud(
                    ex, TipoRilevazione.class, Integer::parseInt, TipoRilevazione::setTip_id,
                    tipi::findAll, tipi::findById, tipi::save, tipi::update, tipi::deleteById));

            server.createContext("/api/sensori-arnia", ex -> gestisciCrud(
                    ex, SensoreArnia.class, Integer::parseInt, SensoreArnia::setSea_id,
                    sensoriArnia::findAll, sensoriArnia::findById, sensoriArnia::save, sensoriArnia::update, sensoriArnia::deleteById));

            server.createContext("/api/rilevazioni", ex -> gestisciCrud(
                    ex, Rilevazione.class, Long::parseLong, Rilevazione::setRil_id,
                    rilevazioni::findAll, rilevazioni::findById, rilevazioni::save, rilevazioni::update, rilevazioni::deleteById));

            server.setExecutor(null);
            server.start();
            stampaInfo(porta);

        } catch (IOException e) {
            System.err.println("Errore avvio server: " + e.getMessage());
        }
    }

    /**
     * Metodo comune per GET, POST, PUT e DELETE.
     * Così evitiamo una classe Handler diversa per ogni tabella.
     */
    private static <T, ID> void gestisciCrud(
            HttpExchange ex,
            Class<T> classe,
            Function<String, ID> leggiId,
            BiConsumer<T, ID> impostaId,
            TrovaTutti<T> trovaTutti,
            TrovaPerId<T, ID> trovaPerId,
            Salva<T> salva,
            Aggiorna<T> aggiorna,
            Elimina<ID> elimina) throws IOException {

        aggiungiCors(ex);

        try {
            String metodo = ex.getRequestMethod();

            if (metodo.equalsIgnoreCase("OPTIONS")) {
                invia(ex, 204, "");
                return;
            }

            switch (metodo) {
                case "GET" -> {
                    ID id = idDallaQuery(ex, leggiId);
                    if (id == null) {
                        List<T> lista = trovaTutti.esegui();
                        inviaJson(ex, 200, lista);
                    } else {
                        Optional<T> elemento = trovaPerId.esegui(id);
                        if (elemento.isPresent()) inviaJson(ex, 200, elemento.get());
                        else inviaErrore(ex, 404, "Record non trovato");
                    }
                }
                case "POST" -> {
                    T elemento = leggiJson(ex, classe);
                    salva.esegui(elemento);
                    inviaJson(ex, 201, elemento);
                }
                case "PUT" -> {
                    ID id = idDallaQuery(ex, leggiId);
                    if (id == null) {
                        inviaErrore(ex, 400, "Per modificare serve ?id=...");
                        return;
                    }
                    T elemento = leggiJson(ex, classe);
                    impostaId.accept(elemento, id);
                    aggiorna.esegui(elemento);
                    inviaJson(ex, 200, elemento);
                }
                case "DELETE" -> {
                    ID id = idDallaQuery(ex, leggiId);
                    if (id == null) {
                        inviaErrore(ex, 400, "Per eliminare serve ?id=...");
                        return;
                    }
                    elimina.esegui(id);
                    inviaJson(ex, 200, messaggio("Record eliminato"));
                }
                default -> inviaErrore(ex, 405, "Metodo non supportato");
            }

        } catch (NumberFormatException e) {
            inviaErrore(ex, 400, "Id non valido");
        } catch (Exception e) {
            inviaErrore(ex, 500, e.getMessage());
        }
    }

    private static <ID> ID idDallaQuery(HttpExchange ex, Function<String, ID> converti) {
        String query = ex.getRequestURI().getQuery();
        if (query == null || query.isBlank()) return null;

        for (String pezzo : query.split("&")) {
            String[] parti = pezzo.split("=", 2);
            if (parti.length == 2 && parti[0].equals("id")) {
                return converti.apply(parti[1]);
            }
        }
        return null;
    }

    private static <T> T leggiJson(HttpExchange ex, Class<T> classe) throws IOException {
        try (InputStream is = ex.getRequestBody()) {
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return gson.fromJson(body, classe);
        }
    }

    private static void inviaJson(HttpExchange ex, int stato, Object oggetto) throws IOException {
        invia(ex, stato, gson.toJson(oggetto));
    }

    private static void inviaErrore(HttpExchange ex, int stato, String testo) throws IOException {
        inviaJson(ex, stato, messaggio(testo));
    }

    private static Map<String, String> messaggio(String testo) {
        Map<String, String> mappa = new HashMap<>();
        mappa.put("messaggio", testo);
        return mappa;
    }

    private static void invia(HttpExchange ex, int stato, String risposta) throws IOException {
        byte[] bytes = risposta.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        ex.sendResponseHeaders(stato, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void aggiungiCors(HttpExchange ex) {
        ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ex.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void stampaInfo(int porta) {
        System.out.println("Server APIDB avviato: http://localhost:" + porta);
        System.out.println();
        System.out.println("Endpoint disponibili:");
        System.out.println("GET/POST/PUT/DELETE /api/utenti");
        System.out.println("GET/POST/PUT/DELETE /api/apiario");
        System.out.println("GET/POST/PUT/DELETE /api/arnie");
        System.out.println("GET/POST/PUT/DELETE /api/sensori");
        System.out.println("GET/POST/PUT/DELETE /api/tipi-rilevazione");
        System.out.println("GET/POST/PUT/DELETE /api/sensori-arnia");
        System.out.println("GET/POST/PUT/DELETE /api/rilevazioni");
        System.out.println();
        System.out.println("Esempi:");
        System.out.println("GET    /api/apiario");
        System.out.println("GET    /api/apiario?id=1");
        System.out.println("POST   /api/apiario");
        System.out.println("PUT    /api/apiario?id=1");
        System.out.println("DELETE /api/apiario?id=1");
    }

    @FunctionalInterface private interface TrovaTutti<T> { List<T> esegui() throws Exception; }
    @FunctionalInterface private interface TrovaPerId<T, ID> { Optional<T> esegui(ID id) throws Exception; }
    @FunctionalInterface private interface Salva<T> { void esegui(T elemento) throws Exception; }
    @FunctionalInterface private interface Aggiorna<T> { void esegui(T elemento) throws Exception; }
    @FunctionalInterface private interface Elimina<ID> { void esegui(ID id) throws Exception; }
}
