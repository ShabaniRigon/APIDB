package serverrest.repository;

import serverrest.db.ConnectionManager;
import serverrest.model.SensoreArnia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTAZIONE JDBC per SensoreArnia.
 * Qui stanno le query SQL, come nel PDF del Repository Pattern.
 */
public class SensoreArniaRepositoryImpl implements SensoreArniaRepository {

    private static final String INSERT =
            "INSERT INTO SensoreArnia (sea_arn_id, sea_tip_id, sea_stato, sea_attivo, sea_on, sea_min, sea_max, sea_intervallo_ms, sea_delta, sea_cal_factor, sea_tare_offset, sea_note) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT sea_id, sea_arn_id, sea_tip_id, sea_stato, sea_attivo, sea_on, sea_min, sea_max, sea_intervallo_ms, sea_delta, sea_cal_factor, sea_tare_offset, sea_note, sea_aggiornato_at FROM SensoreArnia ORDER BY sea_id";

    private static final String SELECT_BY_ID =
            "SELECT sea_id, sea_arn_id, sea_tip_id, sea_stato, sea_attivo, sea_on, sea_min, sea_max, sea_intervallo_ms, sea_delta, sea_cal_factor, sea_tare_offset, sea_note, sea_aggiornato_at FROM SensoreArnia WHERE sea_id = ?";

    private static final String UPDATE =
            "UPDATE SensoreArnia SET sea_arn_id = ?, sea_tip_id = ?, sea_stato = ?, sea_attivo = ?, sea_on = ?, sea_min = ?, sea_max = ?, sea_intervallo_ms = ?, sea_delta = ?, sea_cal_factor = ?, sea_tare_offset = ?, sea_note = ? WHERE sea_id = ?";

    private static final String DELETE =
            "DELETE FROM SensoreArnia WHERE sea_id = ?";

    @Override
    public void save(SensoreArnia elemento) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setIntegerNullable(ps, 1, elemento.getSea_arn_id());
            setIntegerNullable(ps, 2, elemento.getSea_tip_id());
            setBooleanNullable(ps, 3, elemento.isSea_stato());
            setBooleanNullable(ps, 4, elemento.isSea_attivo());
            setBooleanNullable(ps, 5, elemento.isSea_on());
            setDoubleNullable(ps, 6, elemento.getSea_min());
            setDoubleNullable(ps, 7, elemento.getSea_max());
            setIntegerNullable(ps, 8, elemento.getSea_intervallo_ms());
            setDoubleNullable(ps, 9, elemento.getSea_delta());
            setDoubleNullable(ps, 10, elemento.getSea_cal_factor());
            setLongNullable(ps, 11, elemento.getSea_tare_offset());
            setStringNullable(ps, 12, elemento.getSea_note());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    elemento.setSea_id(getIntegerGeneratedKey(keys));
                }
            }
        }
    }

    @Override
    public List<SensoreArnia> findAll() throws SQLException {
        List<SensoreArnia> lista = new ArrayList<>();

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
    public Optional<SensoreArnia> findById(Integer id) throws SQLException {
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
    public void update(SensoreArnia elemento) throws SQLException {
        if (elemento.getSea_id() == null) {
            throw new SQLException("Impossibile aggiornare: id mancante.");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            setIntegerNullable(ps, 1, elemento.getSea_arn_id());
            setIntegerNullable(ps, 2, elemento.getSea_tip_id());
            setBooleanNullable(ps, 3, elemento.isSea_stato());
            setBooleanNullable(ps, 4, elemento.isSea_attivo());
            setBooleanNullable(ps, 5, elemento.isSea_on());
            setDoubleNullable(ps, 6, elemento.getSea_min());
            setDoubleNullable(ps, 7, elemento.getSea_max());
            setIntegerNullable(ps, 8, elemento.getSea_intervallo_ms());
            setDoubleNullable(ps, 9, elemento.getSea_delta());
            setDoubleNullable(ps, 10, elemento.getSea_cal_factor());
            setLongNullable(ps, 11, elemento.getSea_tare_offset());
            setStringNullable(ps, 12, elemento.getSea_note());
            ps.setInt(13, elemento.getSea_id());

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

    private SensoreArnia creaOggettoDaRiga(ResultSet rs) throws SQLException {
        SensoreArnia elemento = new SensoreArnia();
        elemento.setSea_id(getIntegerNullable(rs, "sea_id"));
        elemento.setSea_arn_id(getIntegerNullable(rs, "sea_arn_id"));
        elemento.setSea_tip_id(getIntegerNullable(rs, "sea_tip_id"));
        elemento.setSea_stato(getBooleanNullable(rs, "sea_stato"));
        elemento.setSea_attivo(getBooleanNullable(rs, "sea_attivo"));
        elemento.setSea_on(getBooleanNullable(rs, "sea_on"));
        elemento.setSea_min(getDoubleNullable(rs, "sea_min"));
        elemento.setSea_max(getDoubleNullable(rs, "sea_max"));
        elemento.setSea_intervallo_ms(getIntegerNullable(rs, "sea_intervallo_ms"));
        elemento.setSea_delta(getDoubleNullable(rs, "sea_delta"));
        elemento.setSea_cal_factor(getDoubleNullable(rs, "sea_cal_factor"));
        elemento.setSea_tare_offset(getLongNullable(rs, "sea_tare_offset"));
        elemento.setSea_note(getStringNullable(rs, "sea_note"));
        elemento.setSea_aggiornato_at(getStringNullable(rs, "sea_aggiornato_at"));
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
