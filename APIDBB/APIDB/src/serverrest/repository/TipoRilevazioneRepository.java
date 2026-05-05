package serverrest.repository;

import serverrest.model.TipoRilevazione;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFACCIA REPOSITORY per TipoRilevazione.
 * Dice COSA possiamo fare, non COME vengono fatte le query.
 */
public interface TipoRilevazioneRepository {
    void save(TipoRilevazione elemento) throws SQLException;
    List<TipoRilevazione> findAll() throws SQLException;
    Optional<TipoRilevazione> findById(Integer id) throws SQLException;
    void update(TipoRilevazione elemento) throws SQLException;
    void deleteById(Integer id) throws SQLException;
}
