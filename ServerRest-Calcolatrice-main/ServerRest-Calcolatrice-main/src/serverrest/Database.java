package serverrest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // URL per connettersi al db "apicoltura" su XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/apicoltura";
    private static final String USER = "root"; // utente di default di XAMPP
    private static final String PASS = "";     // password di default vuota

    public static Connection getConnection() throws SQLException {
        try {
            // FORZA IL CARICAMENTO DEL DRIVER MYSQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL non trovato! Assicurati di aver aggiunto il file .jar alle librerie.", e);
        }
        
        // Restituisce la connessione attiva
        return DriverManager.getConnection(URL, USER, PASS);
    }
}