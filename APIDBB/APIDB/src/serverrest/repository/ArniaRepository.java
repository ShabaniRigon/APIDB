package serverrest.repository;

import serverrest.model.Arnia;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFACCIA REPOSITORY per Arnia.
 * Dice COSA possiamo fare, non COME vengono fatte le query.
 */
public interface ArniaRepository {
    void save(Arnia elemento) throws SQLException;
    List<Arnia> findAll() throws SQLException;
    Optional<Arnia> findById(Integer id) throws SQLException;
    void update(Arnia elemento) throws SQLException;
    void deleteById(Integer id) throws SQLException;
}
