package serverrest.repository;

import serverrest.db.ConnectionManager;
import serverrest.model.Utente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTAZIONE JDBC per Utente.
 * Qui stanno le query SQL, come nel PDF del Repository Pattern.
 */
public class UtenteRepositoryImpl implements UtenteRepository {

    private static final String INSERT =
            "INSERT INTO Utente (ute_username, ute_password, ute_admin, ute_token, ute_scadenzaToken) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT ute_id, ute_username, ute_password, ute_admin, ute_token, ute_scadenzaToken, ute_creato_at FROM Utente ORDER BY ute_id";

    private static final String SELECT_BY_ID =
            "SELECT ute_id, ute_username, ute_password, ute_admin, ute_token, ute_scadenzaToken, ute_creato_at FROM Utente WHERE ute_id = ?";

    private static final String UPDATE =
            "UPDATE Utente SET ute_username = ?, ute_password = ?, ute_admin = ?, ute_token = ?, ute_scadenzaToken = ? WHERE ute_id = ?";

    private static final String DELETE =
            "DELETE FROM Utente WHERE ute_id = ?";

    @Override
    public void save(Utente elemento) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setStringNullable(ps, 1, elemento.getUte_username());
            setStringNullable(ps, 2, elemento.getUte_password());
            setBooleanNullable(ps, 3, elemento.isUte_admin());
            setStringNullable(ps, 4, elemento.getUte_token());
            setStringNullable(ps, 5, elemento.getUte_scadenzaToken());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    elemento.setUte_id(getIntegerGeneratedKey(keys));
                }
            }
        }
    }

    @Override
    public List<Utente> findAll() throws SQLException {
        List<Utente> lista = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(creaOggettoDaRiga(rs));
            }
        }

        return lista;
    }

    @Override
    public Optional<Utente> findById(Integer id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(creaOggettoDaRiga(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void update(Utente elemento) throws SQLException {
        if (elemento.getUte_id() == null) {
            throw new SQLException("Impossibile aggiornare: id mancante.");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            setStringNullable(ps, 1, elemento.getUte_username());
            setStringNullable(ps, 2, elemento.getUte_password());
            setBooleanNullable(ps, 3, elemento.isUte_admin());
            setStringNullable(ps, 4, elemento.getUte_token());
            setStringNullable(ps, 5, elemento.getUte_scadenzaToken());
            ps.setInt(6, elemento.getUte_id());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Utente creaOggettoDaRiga(ResultSet rs) throws SQLException {
        Utente elemento = new Utente();
        elemento.setUte_id(getIntegerNullable(rs, "ute_id"));
        elemento.setUte_username(getStringNullable(rs, "ute_username"));
        elemento.setUte_password(getStringNullable(rs, "ute_password"));
        elemento.setUte_admin(getBooleanNullable(rs, "ute_admin"));
        elemento.setUte_token(getStringNullable(rs, "ute_token"));
        elemento.setUte_scadenzaToken(getStringNullable(rs, "ute_scadenzaToken"));
        elemento.setUte_creato_at(getStringNullable(rs, "ute_creato_at"));
        return elemento;
    }

    private Integer getIntegerGeneratedKey(ResultSet keys) throws SQLException {
        return keys.getInt(1);
    }

    private Long getLongGeneratedKey(ResultSet keys) throws SQLException {
        return keys.getLong(1);
    }

    private void setStringNullable(PreparedStatement ps, int pos, String value) throws SQLException {
        if (value == null) ps.setNull(pos, Types.VARCHAR);
        else ps.setString(pos, value);
    }

    private void setIntegerNullable(PreparedStatement ps, int pos, Integer value) throws SQLException {
        if (value == null) ps.setNull(pos, Types.INTEGER);
        else ps.setInt(pos, value);
    }

    private void setLongNullable(PreparedStatement ps, int pos, Long value) throws SQLException {
        if (value == null) ps.setNull(pos, Types.BIGINT);
        else ps.setLong(pos, value);
    }

    private void setDoubleNullable(PreparedStatement ps, int pos, Double value) throws SQLException {
        if (value == null) ps.setNull(pos, Types.DOUBLE);
        else ps.setDouble(pos, value);
    }

    private void setBooleanNullable(PreparedStatement ps, int pos, Boolean value) throws SQLException {
        if (value == null) ps.setNull(pos, Types.BOOLEAN);
        else ps.setBoolean(pos, value);
    }

    private String getStringNullable(ResultSet rs, String col) throws SQLException {
        return rs.getString(col);
    }

    private Integer getIntegerNullable(ResultSet rs, String col) throws SQLException {
        int value = rs.getInt(col);
        return rs.wasNull() ? null : value;
    }

    private Long getLongNullable(ResultSet rs, String col) throws SQLException {
        long value = rs.getLong(col);
        return rs.wasNull() ? null : value;
    }

    private Double getDoubleNullable(ResultSet rs, String col) throws SQLException {
        double value = rs.getDouble(col);
        return rs.wasNull() ? null : value;
    }

    private Boolean getBooleanNullable(ResultSet rs, String col) throws SQLException {
        boolean value = rs.getBoolean(col);
        return rs.wasNull() ? null : value;
    }

}
