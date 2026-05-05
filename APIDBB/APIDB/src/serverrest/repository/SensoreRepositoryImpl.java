package serverrest.repository;

import serverrest.db.ConnectionManager;
import serverrest.model.Sensore;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTAZIONE JDBC per Sensore.
 * Qui stanno le query SQL, come nel PDF del Repository Pattern.
 */
public class SensoreRepositoryImpl implements SensoreRepository {

    private static final String INSERT =
            "INSERT INTO Sensore (sen_modello, sen_codice, sen_produttore) VALUES (?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT sen_id, sen_modello, sen_codice, sen_produttore FROM Sensore ORDER BY sen_id";

    private static final String SELECT_BY_ID =
            "SELECT sen_id, sen_modello, sen_codice, sen_produttore FROM Sensore WHERE sen_id = ?";

    private static final String UPDATE =
            "UPDATE Sensore SET sen_modello = ?, sen_codice = ?, sen_produttore = ? WHERE sen_id = ?";

    private static final String DELETE =
            "DELETE FROM Sensore WHERE sen_id = ?";

    @Override
    public void save(Sensore elemento) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setStringNullable(ps, 1, elemento.getSen_modello());
            setStringNullable(ps, 2, elemento.getSen_codice());
            setStringNullable(ps, 3, elemento.getSen_produttore());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    elemento.setSen_id(getIntegerGeneratedKey(keys));
                }
            }
        }
    }

    @Override
    public List<Sensore> findAll() throws SQLException {
        List<Sensore> lista = new ArrayList<>();

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
    public Optional<Sensore> findById(Integer id) throws SQLException {
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
    public void update(Sensore elemento) throws SQLException {
        if (elemento.getSen_id() == null) {
            throw new SQLException("Impossibile aggiornare: id mancante.");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            setStringNullable(ps, 1, elemento.getSen_modello());
            setStringNullable(ps, 2, elemento.getSen_codice());
            setStringNullable(ps, 3, elemento.getSen_produttore());
            ps.setInt(4, elemento.getSen_id());

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

    private Sensore creaOggettoDaRiga(ResultSet rs) throws SQLException {
        Sensore elemento = new Sensore();
        elemento.setSen_id(getIntegerNullable(rs, "sen_id"));
        elemento.setSen_modello(getStringNullable(rs, "sen_modello"));
        elemento.setSen_codice(getStringNullable(rs, "sen_codice"));
        elemento.setSen_produttore(getStringNullable(rs, "sen_produttore"));
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
