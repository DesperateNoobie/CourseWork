package com.example.AAAAA;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChatControler
{
    @FXML
    public void initialize(String username)
    {
        Chatik.setEditable(false);

        System.out.println("Я меняю имя");
        if (nicknamechat != null) {
            nicknamechat.setText("Пользователь: " + username);
        }
        System.out.println("Поменял имя =)");
    }



    @FXML
    private Button SEND; //Отправка сообщений в чат
    @FXML
    private TextArea Chatik; //Поле для сообщений

    @FXML
    private ListView SpisokFriend; //список контактов в чате
    @FXML
    private Button addFriend; //Добавление контактов


    @FXML
    private Label nicknamechat; //ник пользователя
    @FXML
    private Label DateREgistr; //дата регистрации аккаунта

    @FXML
    private TextField AAAAAA;


    @FXML
    public void SendMessageButton() {
        String message = AAAAAA.getText().trim(); // Получаем сообщение из поля ввода
        System.out.println("Сообщение получено: [" + message + "]"); // Для диагностики

        // Проверка на пустое сообщение
        if (!message.isEmpty()) {
            System.out.println("Сообщение не пустое, продолжаем обработку");

            // Добавляем сообщение в TextArea (вывод)
            Chatik.appendText("\nВы: " + message); // Добавляем сообщение в чат

            // Очищаем поле ввода
            AAAAAA.clear();
            System.out.println("Поле ввода очищено");

            // Принудительно обновляем TextArea
            Chatik.requestFocus();
        } else {
            System.out.println("Сообщение пустое, отправка отменена");
        }
    }


    void loadUserData(String username) {

        System.out.println("loaduserData работает!!!!");

        System.out.println("Загружаю данные ");
        String registrationDate = DatabaseManager.getUserRegistrationDate(username);  // Получаем дату регистрации
        nicknamechat.setWrapText(true);//перенос текста

        nicknamechat.setText("Ник: \n" + "\n"+ username);  // Устанавливаем имя пользователя
        nicknamechat.setWrapText(true);

        DateREgistr.setText("Дата регистрации: " +"\n"+ registrationDate);  // Устанавливаем дату регистрации
        System.out.println("Загрузил данные ");
    }
/*
    public void setUserData(String username, String registrationDate)
    {
        nicknamechat.setText("Ник: " + username);
        DateREgistr.setText("Дата регистрации: " + registrationDate);

        System.out.println("Username: " + username);
        System.out.println("Registration Date: " + registrationDate);
    }
*/


    public void ExitButtonChat(ActionEvent actionEvent)
    {

        Platform.exit();

        // Закрываем все другие процессы
        System.exit(0); // Завершаем работу JVM
    }
}
