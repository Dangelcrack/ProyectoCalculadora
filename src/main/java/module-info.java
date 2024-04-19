module com.github.dangelcrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    opens com.github.dangelcrack to javafx.fxml;
    exports com.github.dangelcrack;
    exports com.github.dangelcrack.view;
    opens com.github.dangelcrack.view to javafx.fxml;
}
