package serverrest.repository;

import serverrest.db.ConnectionManager;
import serverrest.model.TipoRilevazione;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTAZIONE JDBC per TipoRilevazione.
 * Qui stanno le query SQL, come nel PDF del Repository Pattern.
 */
public class TipoRilevazioneRepositoryImpl implements TipoRilevazioneRepository {

    private static final String INSERT =
            "INSERT INTO TipoRilevazione (tip_tipologia, tip_codice, tip_sen_id, tip_unita, tip_futuro) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT tip_id, tip_tipologia, tip_codice, tip_sen_id, tip_unita, tip_futuro FROM TipoRilevazione ORDER BY tip_id";

    private static final String SELECT_BY_ID =
            "SELECT tip_id, tip_tipologia, tip_codice, tip_sen_id, tip_unita, tip_futuro FROM TipoRilevazione WHERE tip_id = ?";

    private static final String UPDATE =
            "UPDATE TipoRilevazione SET tip_tipologia = ?, tip_codice = ?, tip_sen_id = ?, tip_unita = ?, tip_futuro = ? WHERE tip_id = ?";

    private static final String DELETE =
            "DELETE FROM TipoRilevazione WHERE tip_id = ?";

    @Override
    public void save(TipoRilevazione elemento) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setStringNullable(ps, 1, elemento.getTip_tipologia());
            setStringNullable(ps, 2, elemento.getTip_codice());
            setIntegerNullable(ps, 3, elemento.getTip_sen_id());
            setStringNullable(ps, 4, elemento.getTip_unita());
            setBooleanNullable(ps, 5, elemento.isTip_futuro());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    elemento.setTip_id(getIntegerGeneratedKey(keys));
                }
            }
        }
    }

    @Override
    public List<TipoRilevazione> findAll() throws SQLException {
        List<TipoRilevazione> lista = new ArrayList<>();

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
    public Optional<TipoRilevazione> findById(Integer id) throws SQLException {
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
    public void update(TipoRilevazione elemento) throws SQLException {
        if (elemento.getTip_id() == null) {
            throw new SQLException("Impossibile aggiornare: id mancante.");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            setStringNullable(ps, 1, elemento.getTip_tipologia());
            setStringNullable(ps, 2, elemento.getTip_codice());
            setIntegerNullable(ps, 3, elemento.getTip_sen_id());
            setStringNullable(ps, 4, elemento.getTip_unita());
            setBooleanNullable(ps, 5, elemento.isTip_futuro());
            ps.setInt(6, elemento.getTip_id());

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

    private TipoRilevazione creaOggettoDaRiga(ResultSet rs) throws SQLException {
        TipoRilevazione elemento = new TipoRilevazione();
        elemento.setTip_id(getIntegerNullable(rs, "tip_id"));
        elemento.setTip_tipologia(getStringNullable(rs, "tip_tipologia"));
        elemento.setTip_codice(getStringNullable(rs, "tip_codice"));
        elemento.setTip_sen_id(getIntegerNullable(rs, "tip_sen_id"));
        elemento.setTip_unita(getStringNullable(rs, "tip_unita"));
        elemento.setTip_futuro(getBooleanNullable(rs, "tip_futuro"));
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
