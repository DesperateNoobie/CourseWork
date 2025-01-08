package com.example.myfuckingcoursework1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
//import sun.net.ftp.FtpClient;

import java.sql.*;

public class RegisterControler {

    @FXML
    private TextField passwordregisterWindow1; // 1 окно для пароля - регистрация

    @FXML
    private TextField passwordregisterWindow2; // 2 окно для пароля - регистрация
    @FXML
    private Button AuthorizationButton;

    @FXML
    private TextField nicknameLogin;

    @FXML
    private  TextField nicknameRegisterWindow;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorMessage;

    @FXML
    private Button RegisterButton;

    @FXML
    void setAuthorizationButton() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorMessage.setText("Пожалуйста, заполните все поля!");
                return;
            }

            // Проверка на существование пользователя
            if (isUserExists(username)) {
                errorMessage.setText("Пользователь с таким именем уже существует!");
                return;
            }

            // Добавление пользователя в базу данных
            addUserToDatabase(username, password);

            // Переход на экран авторизации
            HelloApplication.changeStage("/com/example/myfuckingcoursework1/hello-view.fxml");

            // Отображение сообщения об успешной регистрации
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Уведомление");
            alert.setHeaderText(null);
            alert.setContentText("Вы успешно зарегистрированы!");
            alert.showAndWait();

        } catch (RuntimeException e) {
            System.out.println("ОШИБКА В РЕГИСТРАЦИИ!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Метод для проверки существования пользователя
    private boolean isUserExists(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Если пользователь найден, возвращаем true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    void onRegisterButtonClick() {
        String username = nicknameRegisterWindow.getText();  // Получаем введенное имя пользователя
        String password = passwordregisterWindow1.getText();  // Получаем введенный пароль
        String confirmPassword = passwordregisterWindow2.getText();  // Получаем введенное подтверждение пароля

        // Проверка, что поля не пустые
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Ошибка регистрации", "Все поля должны быть заполнены!", Alert.AlertType.ERROR);
            return;
        }

        // Проверка, что пароли совпадают
        if (!password.equals(confirmPassword)) {
            showAlert("Ошибка регистрации", "Все поля должны быть заполнены!", Alert.AlertType.ERROR);
            return;
        }

        // Вставка пользователя в базу данных
        addUserToDatabase(username, password);

        // Показать сообщение об успешной регистрации
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Регистрация");
        alert.setHeaderText(null);
        alert.setContentText("Регистрация прошла успешно!");
        alert.showAndWait();
    }
    // Метод для добавления нового пользователя в базу данных
    private void addUserToDatabase(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";


        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password); // Пароль должен быть захеширован на практике!
            statement.executeUpdate(); // Выполнить запрос

        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("Ошибка при регистрации!");
        }

    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);         // Заголовок окна
        alert.setHeaderText(null);     // Убираем подзаголовок
        alert.setContentText(content); // Текст сообщения
        alert.showAndWait();           // Показываем окно и ждём, пока пользователь нажмёт "ОК"
    }
}
