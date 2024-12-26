module com.example.myfuckingcoursework1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.desktop;
    requires  javafx.graphics;
    requires java.sql;

    opens com.example.myfuckingcoursework1 to javafx.fxml;
    exports com.example.myfuckingcoursework1;
}