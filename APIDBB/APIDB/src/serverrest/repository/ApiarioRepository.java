package serverrest.repository;

import serverrest.model.Apiario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * INTERFACCIA REPOSITORY per Apiario.
 * Dice COSA possiamo fare, non COME vengono fatte le query.
 */
public interface ApiarioRepository {
    void save(Apiario elemento) throws SQLException;
    List<Apiario> findAll() throws SQLException;
    Optional<Apiario> findById(Integer id) throws SQLException;
    void update(Apiario elemento) throws SQLException;
    void deleteById(Integer id) throws SQLException;
}
