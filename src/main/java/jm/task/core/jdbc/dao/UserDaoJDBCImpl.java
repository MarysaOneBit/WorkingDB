package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = new Util().getConnection();

    private static final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
    private static final String DROP_USERS_TABLE_SQL = "DROP TABLE IF EXISTS Users";
    private static final String SAVE_USER_SQL = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM Users WHERE id = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT * FROM Users";
    private static final String CLEAN_USERS_TABLE_SQL = "DELETE FROM Users";

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(CREATE_USERS_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(DROP_USERS_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(SAVE_USER_SQL)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(GET_ALL_USERS_SQL)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(CLEAN_USERS_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}