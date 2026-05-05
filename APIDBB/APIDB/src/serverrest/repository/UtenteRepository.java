package serverrest.repository;

import serverrest.model.Utente;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFACCIA REPOSITORY per Utente.
 * Dice COSA possiamo fare, non COME vengono fatte le query.
 */
public interface UtenteRepository {
    void save(Utente elemento) throws SQLException;
    List<Utente> findAll() throws SQLException;
    Optional<Utente> findById(Integer id) throws SQLException;
    void update(Utente elemento) throws SQLException;
    void deleteById(Integer id) throws SQLException;
}
