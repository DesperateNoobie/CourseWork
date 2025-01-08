package com.example.myfuckingcoursework1;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
    public static Connection connect()
    {
        Connection con = null;
        try
        {
            //return DriverManager.getConnection(URL, USER, PASSWORD);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(con); // Выводим объект соединения
        }
        catch (Exception e)
        {
            System.out.println("Ошибка подключения к базе данных!");
            e.printStackTrace();
            return null;
        }
        return con; // Возвращаем объект соединения
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
