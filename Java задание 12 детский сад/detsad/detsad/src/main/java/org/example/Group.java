package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Group {
    private final String name;
    private final int number;
    private final Connection connection;

    public Group(String name, int number, Connection connection) {
        this.name = name;
        this.number = number;
        this.connection = connection;
    }

    public void addKid(Kid kid) {
        int groupId = getGroupId();
        if (groupId != -1) {
            String sql = "INSERT INTO kids (full_name, gender, age, group_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, kid.getFullName());
                statement.setString(2, kid.getGender());
                statement.setInt(3, kid.getAge());
                statement.setInt(4, groupId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Группа не найдена в базе данных.");
        }
    }

    public void removeKid(Kid kid) {
        int groupId = getGroupId();
        if (groupId != -1) {
            String sql = "DELETE FROM kids WHERE full_name = ? AND group_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, kid.getFullName());
                statement.setInt(2, groupId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Группа не найдена в базе данных.");
        }
    }

    public List<Kid> getKids() {
        List<Kid> kids = new ArrayList<>();
        int groupId = getGroupId();
        if (groupId != -1) {
            String sql = "SELECT * FROM kids WHERE group_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, groupId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    kids.add(new Kid(
                            resultSet.getString("full_name"),
                            resultSet.getString("gender"),
                            resultSet.getInt("age")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Группа не найдена в базе данных.");
        }
        return kids;
    }

    private int getGroupId() {
        String sql = "SELECT id FROM groups WHERE number = ? AND name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, number);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Группа: " + name + " номер: " + number + ", дети: " + getKids();
    }
}