package repository;

import database.DatabaseManager;
import model.Coordinates;
import model.Discipline;
import model.Difficulty;
import model.LabWork;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LabWorkRepository implements ILabWorkRepository {
    private final Stack<LabWork> stack;
    private final DatabaseManager databaseManager;

    public LabWorkRepository(DatabaseManager databaseManager) throws SQLException {
        this.databaseManager = databaseManager;
        this.stack = new Stack<>();
        this.loadCollectionFromDatabase();
    }

    @Override
    public Stack<LabWork> getAll() {
        try {
            return (Stack<LabWork>) stack.clone();
        } finally {
        }
    }

    @Override
    public Optional<LabWork> getById(long id) {
        try {
            return stack.stream().filter(lw -> lw.getId() == id).findFirst();
        } finally {
        }
    }

    @Override
    public List<LabWork> getLabWorksByOwner(Integer ownerId) {
        try {
            return stack.stream().filter(lw -> lw.getOwnerId() != null && lw.getOwnerId().equals(ownerId)).toList();
        } finally {
        }
    }

    @Override
    public void add(LabWork labWork, Integer ownerId) throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO labworks (name, coordinates_x, coordinates_y, minimal_point, tuned_in_works, difficulty, discipline_name, discipline_labs_count, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id",
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, labWork.getName());
            statement.setInt(2, (int) labWork.getCoordinates().getX());
            statement.setDouble(3, labWork.getCoordinates().getY());
            statement.setFloat(4, labWork.getMinimalPoint());
            if (labWork.getTunedInWorks() != null) {
                statement.setLong(5, labWork.getTunedInWorks());
            } else {
                statement.setNull(5, Types.BIGINT);
            }
            statement.setString(6, labWork.getDifficulty().name());
            if (labWork.getDiscipline() != null) {
                statement.setString(7, labWork.getDiscipline().getName());
                statement.setInt(8, labWork.getDiscipline().getLabsCount());
            } else {
                statement.setNull(7, Types.VARCHAR);
                statement.setNull(8, Types.INTEGER);
            }
            statement.setInt(9, ownerId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating labwork failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    labWork.setId(generatedKeys.getLong(1));
                    labWork.setOwnerId(ownerId);
                    synchronized (stack) {
                        stack.push(labWork);
                    }
                } else {
                    throw new SQLException("Creating labwork failed, no ID obtained.");
                }
            }
        } finally {
        }
    }

    @Override
    public void update(long id, LabWork labWork, Integer ownerId) throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE labworks SET name = ?, coordinates_x = ?, coordinates_y = ?, minimal_point = ?, tuned_in_works = ?, difficulty = ?, discipline_name = ?, discipline_labs_count = ? WHERE id = ? AND owner_id = ?")) {

            statement.setString(1, labWork.getName());
            statement.setInt(2, (int) labWork.getCoordinates().getX());
            statement.setDouble(3, labWork.getCoordinates().getY());
            statement.setFloat(4, labWork.getMinimalPoint());
            if (labWork.getTunedInWorks() != null) {
                statement.setLong(5, labWork.getTunedInWorks());
            } else {
                statement.setNull(5, Types.BIGINT);
            }
            statement.setString(6, labWork.getDifficulty().name());
            if (labWork.getDiscipline() != null) {
                statement.setString(7, labWork.getDiscipline().getName());
                statement.setInt(8, labWork.getDiscipline().getLabsCount());
            } else {
                statement.setNull(7, Types.VARCHAR);
                statement.setNull(8, Types.INTEGER);
            }
            statement.setLong(9, id);
            statement.setInt(10, ownerId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating labwork failed or labwork not found/owned by user.");
            }

            // Update in-memory stack
            synchronized (stack) {
                stack.removeIf(lw -> lw.getId() == id);
                labWork.setId(id);
                labWork.setOwnerId(ownerId);
                stack.push(labWork);
            }
        } finally {
        }
    }

    @Override
    public boolean removeById(long id, Integer ownerId) throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM labworks WHERE id = ? AND owner_id = ?")) {
            statement.setLong(1, id);
            statement.setInt(2, ownerId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                synchronized (stack) {
                    stack.removeIf(lw -> lw.getId() == id);
                }
                return true;
            }
            return false;
        } finally {
        }
    }

    @Override
    public void clear(Integer ownerId) throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM labworks WHERE owner_id = ?")) {
            statement.setInt(1, ownerId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                synchronized (stack) {
                    stack.removeIf(lw -> lw.getOwnerId() != null && lw.getOwnerId().equals(ownerId));
                }
            }
        } finally {
        }
    }

    @Override
    public void loadCollectionFromDatabase() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM labworks")) {

            synchronized (stack) {
                stack.clear();
            }
            while (resultSet.next()) {
                LabWork labWork = new LabWork();
                labWork.setId(resultSet.getLong("id"));
                labWork.setName(resultSet.getString("name"));
                labWork.setCreationDate(new Date(resultSet.getTimestamp("creation_date").getTime()));
                labWork.setMinimalPoint(resultSet.getFloat("minimal_point"));
                labWork.setTunedInWorks(resultSet.getObject("tuned_in_works", Long.class));
                labWork.setDifficulty(Difficulty.valueOf(resultSet.getString("difficulty")));

                Coordinates coordinates = new Coordinates(resultSet.getLong("coordinates_x"), (int) resultSet.getDouble("coordinates_y"));
                labWork.setCoordinates(coordinates);

                String disciplineName = resultSet.getString("discipline_name");
                if (disciplineName != null) {
                    Discipline discipline = new Discipline(disciplineName, 0, 0); // Using 0 for hours as they are not in DB
                    labWork.setDiscipline(discipline);
                }
                labWork.setOwnerId(resultSet.getObject("owner_id", Integer.class));
                synchronized (stack) {
                    stack.push(labWork);
                }
            }
        } finally {
        }
    }
}