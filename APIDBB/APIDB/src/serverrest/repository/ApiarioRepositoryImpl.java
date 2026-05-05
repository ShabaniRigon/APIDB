package serverrest.repository;

import serverrest.db.ConnectionManager;
import serverrest.model.Apiario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTAZIONE JDBC per Apiario.
 * Qui stanno le query SQL, come nel PDF del Repository Pattern.
 */
public class ApiarioRepositoryImpl implements ApiarioRepository {

    private static final String INSERT =
            "INSERT INTO Apiario (api_nome, api_luogo, api_lon, api_lat) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT api_id, api_nome, api_luogo, api_lon, api_lat, api_creato_at FROM Apiario ORDER BY api_id";

    private static final String SELECT_BY_ID =
            "SELECT api_id, api_nome, api_luogo, api_lon, api_lat, api_creato_at FROM Apiario WHERE api_id = ?";

    private static final String UPDATE =
            "UPDATE Apiario SET api_nome = ?, api_luogo = ?, api_lon = ?, api_lat = ? WHERE api_id = ?";

    private static final String DELETE =
            "DELETE FROM Apiario WHERE api_id = ?";

    @Override
    public void save(Apiario elemento) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setStringNullable(ps, 1, elemento.getApi_nome());
            setStringNullable(ps, 2, elemento.getApi_luogo());
            setDoubleNullable(ps, 3, elemento.getApi_lon());
            setDoubleNullable(ps, 4, elemento.getApi_lat());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    elemento.setApi_id(getIntegerGeneratedKey(keys));
                }
            }
        }
    }

    @Override
    public List<Apiario> findAll() throws SQLException {
        List<Apiario> lista = new ArrayList<>();

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
    public Optional<Apiario> findById(Integer id) throws SQLException {
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
    public void update(Apiario elemento) throws SQLException {
        if (elemento.getApi_id() == null) {
            throw new SQLException("Impossibile aggiornare: id mancante.");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            setStringNullable(ps, 1, elemento.getApi_nome());
            setStringNullable(ps, 2, elemento.getApi_luogo());
            setDoubleNullable(ps, 3, elemento.getApi_lon());
            setDoubleNullable(ps, 4, elemento.getApi_lat());
            ps.setInt(5, elemento.getApi_id());

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

    private Apiario creaOggettoDaRiga(ResultSet rs) throws SQLException {
        Apiario elemento = new Apiario();
        elemento.setApi_id(getIntegerNullable(rs, "api_id"));
        elemento.setApi_nome(getStringNullable(rs, "api_nome"));
        elemento.setApi_luogo(getStringNullable(rs, "api_luogo"));
        elemento.setApi_lon(getDoubleNullable(rs, "api_lon"));
        elemento.setApi_lat(getDoubleNullable(rs, "api_lat"));
        elemento.setApi_creato_at(getStringNullable(rs, "api_creato_at"));
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
