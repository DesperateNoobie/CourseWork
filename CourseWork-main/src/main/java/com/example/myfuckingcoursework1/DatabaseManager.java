package com.example.myfuckingcoursework1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/имя_базы_данных";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    // Метод для подключения к базе данных
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Ошибка подключения к базе данных!");
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet executeSelect(String query) {
        ResultSet resultSet = null;
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            resultSet = statement.executeQuery(query); // Выполняем запрос и получаем результат
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
        return resultSet; // Возвращаем ResultSet для дальнейшей обработки
    }
}
