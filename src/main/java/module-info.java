module com.github.dangelcrack {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.dangelcrack to javafx.fxml;
    exports com.github.dangelcrack;
}
