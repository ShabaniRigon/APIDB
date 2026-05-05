package serverrest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * CONNECTION MANAGER
 * Tiene in un solo punto URL, utente e password del database.
 * I repository chiedono qui una connessione quando devono fare query SQL.
 */
public final class ConnectionManager {

    // Dati standard di XAMPP in locale.
    private static final String URL =
            "jdbc:mysql://localhost:3306/apicoltura?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Costruttore privato: questa classe non deve essere istanziata.
    private ConnectionManager() {
    }
}
