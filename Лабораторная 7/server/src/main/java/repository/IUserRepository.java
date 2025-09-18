package repository;

import model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface IUserRepository {
    Optional<User> findByUsername(String username) throws SQLException;
    User createUser(String username, String hashedPassword) throws SQLException;
}
