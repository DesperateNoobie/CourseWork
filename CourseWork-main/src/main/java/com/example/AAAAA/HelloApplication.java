package com.example.AAAAA;

import javafx.scene.Parent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair; // Импортируем Pair

import java.io.IOException;
import java.sql.*;
import java.util.function.Consumer;

public class HelloApplication extends Application {

    public static Stage primarystage;

    public static <T> T getController(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent root = loader.load();  // Загружаем FXML файл
            return loader.getController();  // Получаем контроллер этого файла
        } catch (IOException e) {
            System.out.println("Ниче не передается");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        primarystage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registerwindow.fxml"));
        stage.setResizable(false); // Отключить изменение размеров окна

        Scene scene = new Scene(fxmlLoader.load(), 472, 303);
        stage.setScene(scene);
        stage.show();

        // Пример работы с базой данных с использованием Pair
        String query = "SELECT * FROM users"; // Запрос для получения всех пользователей
        Pair<Connection, ResultSet> resultPair = DatabaseManager.executeSelectWithConnection(query);

        // Обрабатываем результат запроса
        if (resultPair != null) {
            try {
                ResultSet resultSet = resultPair.getValue();
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Username: " + resultSet.getString("username"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Закрываем ресурсы
                try {
                    resultPair.getValue().close(); // Закрываем ResultSet
                    resultPair.getKey().close();  // Закрываем Connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Ошибка: результат запроса пустой!");
        }
    }

    public static void changeStage(String fxmlFile, Consumer<Object> controllerCallback) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent root = loader.load();
            primarystage.setScene(new Scene(root));

            System.out.println("Я работаю 1");
            // Вызов callback для передачи данных в контроллер
            Object controller = loader.getController();
            System.out.println("Я работаю 2");

            controllerCallback.accept(controller); // Вызываем переданный callback
            System.out.println("Я работаю 3");
            if (controllerCallback != null) {
                controllerCallback.accept(controller);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при смене сцены!");
            e.printStackTrace(); // Для отладки
            throw new RuntimeException(e);
        }
    }




    /*public static void changeStage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent root = loader.load();
            primarystage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Ошибка при смене сцены!");
            e.printStackTrace(); // Для отладки
            throw new RuntimeException(e);
        }
    }
*/
    public static void main(String[] args) {
        launch();
    }
}
