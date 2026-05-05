package serverrest.repository;

import serverrest.model.Sensore;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFACCIA REPOSITORY per Sensore.
 * Dice COSA possiamo fare, non COME vengono fatte le query.
 */
public interface SensoreRepository {
    void save(Sensore elemento) throws SQLException;
    List<Sensore> findAll() throws SQLException;
    Optional<Sensore> findById(Integer id) throws SQLException;
    void update(Sensore elemento) throws SQLException;
    void deleteById(Integer id) throws SQLException;
}
