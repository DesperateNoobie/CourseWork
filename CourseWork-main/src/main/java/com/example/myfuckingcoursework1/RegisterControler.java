package com.example.myfuckingcoursework1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterControler
{
@FXML
    private Button AuthorizationButton;



@FXML
    void setAuthorizationButton()
{
try {

    HelloApplication.changeStage("/com/example/myfuckingcoursework1/hello-view.fxml");

    Alert alert = new Alert(Alert.AlertType.INFORMATION); // Тип окна — информационное
    alert.setTitle("Уведомление");                  // Заголовок окна
    alert.setHeaderText(null);                      // Убираем заголовок
    alert.setContentText("Вы авторизованы!");       // Текст сообщения

    alert.showAndWait(); // Показываем окно и ждём, пока пользователь нажмёт "ОК"
}
catch (RuntimeException e)
{
    System.out.println("ОШИБКА В РЕГИСТРЕ!!!!");
    System.out.println("ОШИБКА В РЕГИСТРЕ!!!!");
    System.out.println("ОШИБКА В РЕГИСТРЕ!!!!");
    System.out.println("ОШИБКА В РЕГИСТРЕ!!!!");
    e.printStackTrace(); // Для отладки
    throw new RuntimeException(e);
}
}



}
