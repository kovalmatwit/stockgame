module com.example.stockgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stockgame to javafx.fxml;
    exports com.example.stockgame;
}