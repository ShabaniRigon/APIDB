package serverrest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApiarioService {

    /**
     * Metodo per gestire la GET.
     * Si collega al database e recupera tutti gli apiari.
     */
    public static List<Apiario> getApiari() throws SQLException {
        List<Apiario> listaApiari = new ArrayList<>();
        String query = "SELECT api_id, api_nome, api_luogo FROM Apiario";

        // Il try-with-resources chiude automaticamente la connessione al termine
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Apiario a = new Apiario();
                a.setApi_id(rs.getInt("api_id"));
                a.setApi_nome(rs.getString("api_nome"));
                a.setApi_luogo(rs.getString("api_luogo"));
                
                listaApiari.add(a);
            }
        }
        return listaApiari;
    }

    /**
     * Metodo per gestire la POST.
     * Prende un oggetto Apiario e lo inserisce nel database.
     */
    public static boolean aggiungiApiario(Apiario nuovoApiario) throws SQLException {
        String query = "INSERT INTO Apiario (api_nome, api_luogo) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Sostituisce i punti interrogativi (?) con i valori dell'oggetto
            pstmt.setString(1, nuovoApiario.getApi_nome());
            pstmt.setString(2, nuovoApiario.getApi_luogo());
            
            // Esegue l'inserimento
            int righeModificate = pstmt.executeUpdate();
            
            // Se ha modificato almeno una riga, l'inserimento è andato a buon fine
            return righeModificate > 0;
        }
    }
}