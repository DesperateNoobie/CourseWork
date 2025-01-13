package com.example.myfuckingcoursework1;
import javafx.util.Pair;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:1234/chatdb";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Регистрация драйвера
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер MySQL не найден!");
            e.printStackTrace();
        }
    }

    // Метод для подключения к базе данных
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных!");
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserRegistrationDate(String username) {
        String query = "SELECT created_at FROM users WHERE username = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("created_at"); // Дата из базы данных
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Если не нашли, возвращаем null
    }

    // Метод для выполнения SELECT запросов
    public static ResultSet executeSelect(String query) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            return statement.executeQuery(query); // Выполняем запрос и возвращаем результат
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения SELECT запроса!");
            e.printStackTrace();
        }
        return null; // Возвращаем null, если произошла ошибка
    }

    // Метод для выполнения INSERT, UPDATE, DELETE запросов
    public static int executeUpdate(String query) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            return statement.executeUpdate(query); // Выполняем обновление данных
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения UPDATE/INSERT/DELETE запроса!");
            e.printStackTrace();
        }
        return 0; // Возвращаем 0, если произошла ошибка
    }
    public static Pair<Connection, ResultSet> executeSelectWithConnection(String query) {
        try {
            Connection connection = connect(); // Устанавливаем соединение
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                return new Pair<>(connection, resultSet); // Возвращаем пару Connection и ResultSet
            }
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения SELECT запроса!");
            e.printStackTrace();
        }
        return null;
    }
}
