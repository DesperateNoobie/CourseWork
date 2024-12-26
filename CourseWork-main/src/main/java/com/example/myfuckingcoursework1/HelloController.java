package com.example.myfuckingcoursework1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button SEND;

    @FXML
    private Button addFriend;

    private TextArea chatPanel;
    private TextField MessageWindow;
    @FXML
    public void onHelloButtonClick() {
        System.out.println("Вроде работает!");
    }

    @FXML
    public void handleMouseEnter(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: #42a5f5;");
    }

    @FXML
    public void handleMouseExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
    }

    @FXML
    public void initialize() {
        SEND.setOnMousePressed(event -> {
            SEND.setStyle("-fx-background-color: white;");
        });

        SEND.setOnMouseReleased(event -> {
            SEND.setStyle("-fx-background-color: white;");
        });

        //chatPanel.
    }

}

