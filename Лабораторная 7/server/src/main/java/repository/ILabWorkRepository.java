package repository;

import model.LabWork;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * Интерфейс ILabWorkRepository определяет методы для работы с коллекцией LabWork.
 */
public interface ILabWorkRepository {
    Stack<LabWork> getAll() throws SQLException;
    void add(LabWork labWork, Integer ownerId) throws SQLException;
    void update(long id, LabWork labWork, Integer ownerId) throws SQLException;
    boolean removeById(long id, Integer ownerId) throws SQLException;
    void clear(Integer ownerId) throws SQLException;
    Optional<LabWork> getById(long id) throws SQLException;
    List<LabWork> getLabWorksByOwner(Integer ownerId) throws SQLException;
    void loadCollectionFromDatabase() throws SQLException;
}