package serverrest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetHandler implements HttpHandler {
    
    // Istanza Gson
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        // Verifica metodo GET
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            inviaErrore(exchange, 405, "Metodo non consentito. Usa GET");
            return;
        }
        
        try {
            // 1. Chiama il Service per prendere tutti gli apiari dal database
            List<Apiario> listaApiari = ApiarioService.getApiari();
            
            // 2. Converte la lista Java in un array JSON
            String jsonRisposta = gson.toJson(listaApiari);
            
            // 3. Invia la risposta
            inviaRisposta(exchange, 200, jsonRisposta);
            
        } catch (SQLException e) {
            inviaErrore(exchange, 500, "Errore Database: " + e.getMessage());
        } catch (Exception e) {
            inviaErrore(exchange, 500, "Errore generico: " + e.getMessage());
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