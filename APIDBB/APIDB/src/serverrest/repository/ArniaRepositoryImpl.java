package serverrest.repository;

import serverrest.db.ConnectionManager;
import serverrest.model.Arnia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTAZIONE JDBC per Arnia.
 * Qui stanno le query SQL, come nel PDF del Repository Pattern.
 */
public class ArniaRepositoryImpl implements ArniaRepository {

    private static final String INSERT =
            "INSERT INTO Arnia (arn_api_id, arn_nome, arn_dataInst, arn_piena, arn_MacAddress, arn_attiva) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT arn_id, arn_api_id, arn_nome, arn_dataInst, arn_piena, arn_MacAddress, arn_attiva, arn_creato_at FROM Arnia ORDER BY arn_id";

    private static final String SELECT_BY_ID =
            "SELECT arn_id, arn_api_id, arn_nome, arn_dataInst, arn_piena, arn_MacAddress, arn_attiva, arn_creato_at FROM Arnia WHERE arn_id = ?";

    private static final String UPDATE =
            "UPDATE Arnia SET arn_api_id = ?, arn_nome = ?, arn_dataInst = ?, arn_piena = ?, arn_MacAddress = ?, arn_attiva = ? WHERE arn_id = ?";

    private static final String DELETE =
            "DELETE FROM Arnia WHERE arn_id = ?";

    @Override
    public void save(Arnia elemento) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setIntegerNullable(ps, 1, elemento.getArn_api_id());
            setStringNullable(ps, 2, elemento.getArn_nome());
            setStringNullable(ps, 3, elemento.getArn_dataInst());
            setBooleanNullable(ps, 4, elemento.isArn_piena());
            setStringNullable(ps, 5, elemento.getArn_MacAddress());
            setBooleanNullable(ps, 6, elemento.isArn_attiva());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    elemento.setArn_id(getIntegerGeneratedKey(keys));
                }
            }
        }
    }

    @Override
    public List<Arnia> findAll() throws SQLException {
        List<Arnia> lista = new ArrayList<>();

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
    public Optional<Arnia> findById(Integer id) throws SQLException {
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
    public void update(Arnia elemento) throws SQLException {
        if (elemento.getArn_id() == null) {
            throw new SQLException("Impossibile aggiornare: id mancante.");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            setIntegerNullable(ps, 1, elemento.getArn_api_id());
            setStringNullable(ps, 2, elemento.getArn_nome());
            setStringNullable(ps, 3, elemento.getArn_dataInst());
            setBooleanNullable(ps, 4, elemento.isArn_piena());
            setStringNullable(ps, 5, elemento.getArn_MacAddress());
            setBooleanNullable(ps, 6, elemento.isArn_attiva());
            ps.setInt(7, elemento.getArn_id());

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

    private Arnia creaOggettoDaRiga(ResultSet rs) throws SQLException {
        Arnia elemento = new Arnia();
        elemento.setArn_id(getIntegerNullable(rs, "arn_id"));
        elemento.setArn_api_id(getIntegerNullable(rs, "arn_api_id"));
        elemento.setArn_nome(getStringNullable(rs, "arn_nome"));
        elemento.setArn_dataInst(getStringNullable(rs, "arn_dataInst"));
        elemento.setArn_piena(getBooleanNullable(rs, "arn_piena"));
        elemento.setArn_MacAddress(getStringNullable(rs, "arn_MacAddress"));
        elemento.setArn_attiva(getBooleanNullable(rs, "arn_attiva"));
        elemento.setArn_creato_at(getStringNullable(rs, "arn_creato_at"));
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
