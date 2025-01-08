package com.example.myfuckingcoursework1;

import javafx.scene.Parent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloApplication extends Application {

    private static Stage primarystage;

    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.primarystage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registerwindow.fxml"));
        stage.setResizable(false); // Отключить изменение размеров

        Scene scene = new Scene(fxmlLoader.load(), 472, 303);
        stage.setScene(scene);
        stage.show();

        // Пример подключения к базе данных
        String query = "SELECT * FROM users"; // Запрос для получения всех пользователей
        ResultSet resultSet = DatabaseManager.executeSelect(query);

        // Обрабатываем результат запроса
        try {
            while (resultSet != null && resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Username: " + resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeStage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent root = loader.load();
            primarystage.setScene(new Scene(root));

        } catch (IOException e) {
            System.out.println("  ОШИБКА В МЕЙНЕ!!!!!!");
            e.printStackTrace(); // Для отладки
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
