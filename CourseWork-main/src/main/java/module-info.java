module com.example.AAAAA {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.desktop;
    requires  javafx.graphics;

    requires java.sql;
    requires mysql.connector.j;  // Для работы с базой данных

    opens com.example.AAAAA to javafx.fxml;
    exports com.example.AAAAA;
}