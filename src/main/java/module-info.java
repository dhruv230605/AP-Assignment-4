module com.example.firstjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.firstjavafx to javafx.fxml;
    exports com.example.firstjavafx;
}