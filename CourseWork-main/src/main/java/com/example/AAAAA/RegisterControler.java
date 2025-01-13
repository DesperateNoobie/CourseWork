package com.example.AAAAA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//import sun.net.ftp.FtpClient;

import java.io.IOException;
import java.sql.*;

public class RegisterControler {

    private HelloApplication helloApplication; // Ссылка на основной класс приложения
    private DatabaseManager databaseManager;

    public void setHelloApplication(HelloApplication helloApplication) {
        this.helloApplication = helloApplication;
    }
    @FXML
    public void switchToChatWindow(String username, String registrationDate, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatWindow.fxml"));
            Parent root = loader.load();

            // Получаем контроллер и передаем данные
            ChatControler chatController = loader.getController();
            chatController.loadUserData(username);

            // Устанавливаем сцену на переданный Stage
            stage.setScene(new Scene(root));  // Убедитесь, что используете правильный Stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




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
    private TextField userAthorimation;
    @FXML
    private TextField passwordAthoriz;

    @FXML
   static Label nicknamechat;

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
            HelloApplication.changeStage("/ChatWindow.fxml", controller -> {
                if (controller instanceof ChatControler) {
                    ((ChatControler) controller).loadUserData(username);
                }
            });


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
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Если пользователь найден, возвращаем true
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    void AuthorizationButton()
    {
        String username = this.userAthorimation.getText();
        String password = this.passwordAthoriz.getText();
        String registrationDate = DatabaseManager.getUserRegistrationDate(username);  // Получаем дату регистрации

        if (username.isEmpty() || password.isEmpty())
        {
            showAlert("Ошибка входа", "Неправильнвый логин или пароль", Alert.AlertType.ERROR);
            return;
        }

        if(!isUserExists(username))
        {
            showAlert("Ошибка","Пользователь не найден!",Alert.AlertType.ERROR);
            return;
        }

        if (!isPasswordCorrect(username, password)) {
            showAlert("Ошибка авторизации", "Неверный пароль!", Alert.AlertType.ERROR);
            return;
        }


        showAlert("Получилось", "Вы авторизованны",Alert.AlertType.INFORMATION);

        System.out.println("Ща загружу новую сцену");
        //  ChatControler chatControlr = loader.
        HelloApplication.changeStage("/ChatWindow.fxml", controller -> {
            if (controller instanceof ChatControler)
            {
                ChatControler chatController = (ChatControler) controller;
                ((ChatControler) controller).loadUserData(username);

                // Если хочешь обновить UI после загрузки сцены, сделай это с помощью runLater
                javafx.application.Platform.runLater(() -> {
                    chatController.initialize(username); // Настройка данных в интерфейсе
                });
            }
        });


        // Получаем контроллер чата и передаем данные
        System.out.println("Data " + registrationDate + " Name  " + username);
       ChatControler chatController = HelloApplication.getController("/ChatWindow.fxml");
       chatController.loadUserData(username);  // Передаем данные в контроллер

    }


    @FXML
    void onRegisterButtonClick() {
        String username = this.nicknameRegisterWindow.getText();  // Получаем введенное имя пользователя
        String password = this.passwordregisterWindow1.getText();  // Получаем введенный пароль
        String confirmPassword = this.passwordregisterWindow2.getText();  // Получаем введенное подтверждение пароля

        // Проверка, что поля не пустые
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Ошибка регистрации1", "Все поля должны быть заполнены!", Alert.AlertType.ERROR);
            return;
        }

        // Проверка, что пароли совпадают
        if (!password.equals(confirmPassword)) {
            showAlert("Ошибка регистрации2", "Все поля должны быть заполнены!", Alert.AlertType.ERROR);
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
        String query = "INSERT INTO users (username, password, created_at) VALUES (?, ?, NOW())"; // Добавили поле created_at

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Заполняем параметры
            statement.setString(1, username);
            statement.setString(2, password); // Пароль должен быть захеширован в реальном проекте!

            // Выполняем запрос
            statement.executeUpdate();

            // Выводим сообщение об успехе (можно заменить на вызов showAlert)
            System.out.println("Пользователь добавлен в базу данных!");

        } catch (SQLException e) {
            e.printStackTrace();
            // Если хотите, можете использовать сообщение об ошибке через Alert:
            showAlert("Ошибка", "Ошибка при добавлении пользователя в базу данных!", Alert.AlertType.ERROR);
        }
    }

    private boolean isPasswordCorrect(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    return password.equals(storedPassword);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);         // Заголовок окна
        alert.setHeaderText(null);     // Убираем подзаголовок
        alert.setContentText(content); // Текст сообщения
        alert.showAndWait();           // Показываем окно и ждём, пока пользователь нажмёт "ОК"
    }
}
