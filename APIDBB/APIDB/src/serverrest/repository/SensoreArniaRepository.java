package serverrest.repository;

import serverrest.model.SensoreArnia;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFACCIA REPOSITORY per SensoreArnia.
 * Dice COSA possiamo fare, non COME vengono fatte le query.
 */
public interface SensoreArniaRepository {
    void save(SensoreArnia elemento) throws SQLException;
    List<SensoreArnia> findAll() throws SQLException;
    Optional<SensoreArnia> findById(Integer id) throws SQLException;
    void update(SensoreArnia elemento) throws SQLException;
    void deleteById(Integer id) throws SQLException;
}
