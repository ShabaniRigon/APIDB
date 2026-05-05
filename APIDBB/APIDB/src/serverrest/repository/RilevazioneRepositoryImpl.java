package serverrest.repository;

import serverrest.db.ConnectionManager;
import serverrest.model.Rilevazione;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTAZIONE JDBC per Rilevazione.
 * Qui stanno le query SQL, come nel PDF del Repository Pattern.
 */
public class RilevazioneRepositoryImpl implements RilevazioneRepository {

    private static final String INSERT =
            "INSERT INTO Rilevazione (ril_sea_id, ril_dato, ril_dataOra, ril_codice_stato) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT ril_id, ril_sea_id, ril_dato, ril_dataOra, ril_codice_stato, ril_creata_at FROM Rilevazione ORDER BY ril_id";

    private static final String SELECT_BY_ID =
            "SELECT ril_id, ril_sea_id, ril_dato, ril_dataOra, ril_codice_stato, ril_creata_at FROM Rilevazione WHERE ril_id = ?";

    private static final String UPDATE =
            "UPDATE Rilevazione SET ril_sea_id = ?, ril_dato = ?, ril_dataOra = ?, ril_codice_stato = ? WHERE ril_id = ?";

    private static final String DELETE =
            "DELETE FROM Rilevazione WHERE ril_id = ?";

    @Override
    public void save(Rilevazione elemento) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setIntegerNullable(ps, 1, elemento.getRil_sea_id());
            setDoubleNullable(ps, 2, elemento.getRil_dato());
            setStringNullable(ps, 3, elemento.getRil_dataOra());
            setIntegerNullable(ps, 4, elemento.getRil_codice_stato());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    elemento.setRil_id(getLongGeneratedKey(keys));
                }
            }
        }
    }

    @Override
    public List<Rilevazione> findAll() throws SQLException {
        List<Rilevazione> lista = new ArrayList<>();

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
    public Optional<Rilevazione> findById(Long id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(creaOggettoDaRiga(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void update(Rilevazione elemento) throws SQLException {
        if (elemento.getRil_id() == null) {
            throw new SQLException("Impossibile aggiornare: id mancante.");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            setIntegerNullable(ps, 1, elemento.getRil_sea_id());
            setDoubleNullable(ps, 2, elemento.getRil_dato());
            setStringNullable(ps, 3, elemento.getRil_dataOra());
            setIntegerNullable(ps, 4, elemento.getRil_codice_stato());
            ps.setLong(5, elemento.getRil_id());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Rilevazione creaOggettoDaRiga(ResultSet rs) throws SQLException {
        Rilevazione elemento = new Rilevazione();
        elemento.setRil_id(getLongNullable(rs, "ril_id"));
        elemento.setRil_sea_id(getIntegerNullable(rs, "ril_sea_id"));
        elemento.setRil_dato(getDoubleNullable(rs, "ril_dato"));
        elemento.setRil_dataOra(getStringNullable(rs, "ril_dataOra"));
        elemento.setRil_codice_stato(getIntegerNullable(rs, "ril_codice_stato"));
        elemento.setRil_creata_at(getStringNullable(rs, "ril_creata_at"));
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
