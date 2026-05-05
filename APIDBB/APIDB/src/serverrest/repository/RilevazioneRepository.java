package serverrest.repository;

import serverrest.model.Rilevazione;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFACCIA REPOSITORY per Rilevazione.
 * Dice COSA possiamo fare, non COME vengono fatte le query.
 */
public interface RilevazioneRepository {
    void save(Rilevazione elemento) throws SQLException;
    List<Rilevazione> findAll() throws SQLException;
    Optional<Rilevazione> findById(Long id) throws SQLException;
    void update(Rilevazione elemento) throws SQLException;
    void deleteById(Long id) throws SQLException;
}
