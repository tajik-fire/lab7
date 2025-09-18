package service;

import model.User;
import repository.IUserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final IUserRepository userRepository;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String username, String password) throws SQLException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedPassword = hashPassword(password);
            if (hashedPassword.equals(user.getPasswordHash())) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

    public User register(String username, String password) throws SQLException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new SQLException("User with this username already exists.");
        }
        String hashedPassword = hashPassword(password);
        return userRepository.createUser(username, hashedPassword);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-384");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-384 algorithm not found", e);
        }
    }
}
