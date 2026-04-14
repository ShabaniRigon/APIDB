package serverrest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PostHandler implements HttpHandler {
    
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            inviaErrore(exchange, 405, "Metodo non consentito. Usa POST");
            return;
        }
        
        try {
            // 1. Legge il body (JSON)
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
            
            // 2. GSON mappa il JSON direttamente nel nostro oggetto Apiario!
            Apiario nuovoApiario = gson.fromJson(reader, Apiario.class);
            reader.close();
            
            // Validazione base
            if (nuovoApiario == null || nuovoApiario.getApi_nome() == null) {
                inviaErrore(exchange, 400, "Dati mancanti o JSON non valido");
                return;
            }
            
            // 3. Salva nel DB tramite il Service
            boolean successo = ApiarioService.aggiungiApiario(nuovoApiario);
            
            if (successo) {
                Map<String, String> risposta = new HashMap<>();
                risposta.put("messaggio", "Apiario '" + nuovoApiario.getApi_nome() + "' creato con successo!");
                inviaRisposta(exchange, 201, gson.toJson(risposta)); // 201 = Created
            } else {
                inviaErrore(exchange, 500, "Errore durante l'inserimento nel DB.");
            }
            
        } catch (JsonSyntaxException e) {
            inviaErrore(exchange, 400, "JSON formattato male: " + e.getMessage());
        } catch (SQLException e) {
            inviaErrore(exchange, 500, "Errore Database: " + e.getMessage());
        } catch (Exception e) {
            inviaErrore(exchange, 500, "Errore interno: " + e.getMessage());
        }
    }
    
    // --- METODI DI UTILITA' (uguali a prima) ---
    private void inviaRisposta(HttpExchange exchange, int codice, String jsonRisposta) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        byte[] bytes = jsonRisposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codice, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
    
    private void inviaErrore(HttpExchange exchange, int codice, String messaggio) throws IOException {
        Map<String, Object> errore = new HashMap<>();
        errore.put("errore", messaggio);
        errore.put("status", codice);
        String jsonErrore = gson.toJson(errore);
        inviaRisposta(exchange, codice, jsonErrore);
    }
}