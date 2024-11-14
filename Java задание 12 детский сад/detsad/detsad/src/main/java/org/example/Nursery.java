package org.example;

import java.sql.*;

class Nursery {
    private final Connection connection;

    public Nursery() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void addGroup(Group group) {
        String sql = "INSERT INTO groups (name, number) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, group.getName());
            statement.setInt(2, group.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeGroup(Group group) {
        String sql = "DELETE FROM groups WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, group.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printGroups() {
        String sql = "SELECT * FROM groups";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            boolean hasGroups = false;
            while (resultSet.next()) {
                hasGroups = true;
                Group group = new Group(resultSet.getString("name"), resultSet.getInt("number"), connection);
                System.out.println(group);
            }
            if (!hasGroups) {
                System.out.println("Нет доступных групп.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Group findGroupByNumberAndName(int number, String name) {
        String sql = "SELECT * FROM groups WHERE number = ? AND name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, number);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Group(resultSet.getString("name"), resultSet.getInt("number"), connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}